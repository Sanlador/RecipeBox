package CS561.recipebox.Diet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import CS561.recipebox.Recipe.Recipe;

public class DietDP extends AsyncTask<String, String, String>
{

    private int pageNumber = 1000;
    private DietContractHelper contractHelper;
    private Context context;

    public DietDP(Context c)
    {
        context = c;
    }

    @Override
    protected String doInBackground(String... params)
    {
        Log.d("Function", "Launching Query");
        String output = "";


        String host = "recipebox01.database.windows.net";
        String db = "RecipeDB";
        String user = "recipeOSU";
        String password = "recipe32!";
        String url = "jdbc:jtds:sqlserver://recipebox01.database.windows.net:1433;databaseName=RecipeDB;user=recipeOSU@recipebox01;password=recipe32!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;sendStringParametersAsUnicode=false";
        Connection connection = null;

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url);

            String query = params[0];

            String selectSql = "select TOP 50 * from RecipeScrape";

            Log.d("Query", selectSql);
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSql))
            {
                while (resultSet.next())
                {
                    output += resultSet.getString(1) + "```"
                            + resultSet.getString(2) + "```"
                            + resultSet.getString(3) + "```"
                            + resultSet.getString(4) + "```"
                            + resultSet.getString(5) + "```"
                            + resultSet.getString(6) + "```"
                            + resultSet.getString(7) + "```"
                            + resultSet.getString(8) + "```"
                            + resultSet.getString(9) + "```"
                            + resultSet.getString(10) + "```"
                            + resultSet.getString(11) + "```"
                            + resultSet.getString(12) + "```"
                            + resultSet.getString(13) + "```"
                            + resultSet.getString(14) + "```"
                            + resultSet.getString(15) + "```"
                            + resultSet.getString(16)
                            + "~~~";
                }
                connection.close();
            }
        }
        catch (Exception e)
        {
            Log.d("Exception", "Connection failed");
            Log.e("Exception:", e.toString());
        }
        //temporary output. Will change once DB connection is implemented.

        if (output.length() > 0)
            output = output.substring(0, output.length() - 1);

        int iteration = 0;

        ArrayList<DietItem> dietList;

        List<String[]> parsedOutput = new ArrayList<String[]>();
        String[] splitOutput;
        //Parse output
        if (output.split("~~~").length > 0)
        {
            String[] parse;
            splitOutput = output.split("~~~");
            for (String s: splitOutput)
            {
                parse = s.split("```");
                if (16 == parse.length)
                    parsedOutput.add(parse);
                else
                    Log.d("Recipe", parse[1]);
            }
            if (parsedOutput.size() > 1)
            {
                List<Recipe> recipes = Recipe.createRecipesList(parsedOutput.size()-1, parsedOutput);
                DP(params[0], recipes);
            }
        }
        else
        {
            Log.d("Recommendation", "Failed to make recommendation; No output");
        }

        return "";

    }

    @Override
    protected void onPostExecute(String result)
    {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute()
    {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    private void DP(String survey, List<Recipe> recipes)
    {
        double calories = 2000;
        DietValues values = new DietValues();

        //adjust calorie intake based on survey

        //adjust diet values based on calorie intake
        values.adjust(calories / 2000);

        int iterations = 5;

        contractHelper = new DietContractHelper(context);
        ArrayList<DietItem> savedDiets;
        int size = 0;

        for (int i = 0; i < iterations; i++)
        {
            Collections.shuffle(recipes);
            savedDiets = contractHelper.readFromDatabase();
            savedDiets.size();
            ArrayList<String> usedRecipes = new ArrayList<String>();
            for (DietItem d: savedDiets)
            {
                usedRecipes.add(d.getBreakfast().getName());
                usedRecipes.add(d.getLunch().getName());
                usedRecipes.add(d.getDinner().getName());
            }

            List<Recipe> diet = KS(recipes.size() - 1, calories, 3, recipes, new ArrayList<Recipe>(), values, usedRecipes);
            for (Recipe r: diet)
            {
                Log.d("Diet Output", r.getName());
            }
            contractHelper.writeToDatabase(diet);
        }
    }

    private List<Recipe> KS(int n, double calories, int depth, List<Recipe> recipeList, List<Recipe> diet, DietValues values, List<String> usedRecipes)
    {
        if (n < 0 || calories <= 0 || depth == 0)
            return diet;
        else if (Double.parseDouble(recipeList.get(n).getCalories()) > calories)
            return KS(n - 1, calories, depth, recipeList, diet, values, usedRecipes);
        else
        {
            List<Recipe> diet1, diet2;
            diet1 = KS(n - 1, calories, depth, recipeList, diet, values, usedRecipes);

            List<Recipe> tempDiet = new ArrayList<Recipe>();
            for (Recipe r:diet)
            {
                tempDiet.add(r);
            }

            tempDiet.add(recipeList.get(n));

            List<String> tempNames = new ArrayList<String>();
            for (String s: usedRecipes)
            {
                tempNames.add(s);
            }

            tempNames.add(recipeList.get(n).getName());

            diet2 = KS(n - 1, calories - Double.parseDouble(recipeList.get(n).getCalories()), depth - 1, recipeList, tempDiet, values, tempNames);
            //check for complete nutrition
            if (evalDiet(values,diet2,tempNames) > 13)
            {
                return diet2;
            }
            else if(evalDiet(values,diet2,tempNames) > evalDiet(values,diet1,usedRecipes) || diet1.size() < 3)
            {
                //Log.d("Score:", Double.toString(evalDiet(values,diet2,tempNames)));
                for (Recipe r: diet2)
                {
                    //Log.d("Diet Output", r.getName());
                }
                return diet2;
            }
            else
            {
                //Log.d("Score:", Double.toString(evalDiet(values,diet1,usedRecipes)));
                int i = 0;
                for (Recipe r: diet1)
                {
                    i++;
                    //Log.d("Diet Output", r.getName());
                }
                return diet1;
            }
        }
    }

    private class DietValues
    {
        public double proteinRec; //less than
        public double fatRec; //less than
        public double carbRec;   //more than
        public double sugarRec;   //less than
        public double sodiumRec; //less than
        public double cholRec;    //less than
        public double calorieRec;


        public DietValues()
        {
            proteinRec = 50; //less than
            fatRec = 85; //less than
            carbRec = 300;   //more than
            sugarRec = 50;   //less than
            sodiumRec = 2400; //less than, mg
            cholRec = 300;    //less than, mg
            calorieRec = 2000;
        }

        public void adjust(double factor)
        {
            proteinRec *= factor;
            fatRec *= factor;
            carbRec *= factor;
            sugarRec *= factor;
            sodiumRec *= factor;
            cholRec *= factor;
            calorieRec *= factor;
        }
    }

    double coefficientLessEval(double val, double rec)
    {
        //
        double threshold = .1;
        if (Math.abs(val - rec) <= rec * threshold)
            return 1;
        else if ((rec- val) >= rec * threshold * 2.5)
            return .75;
        else if ((rec- val) >= rec * threshold * 5)
            return .5;
        else if ((rec- val) >= rec * threshold * 7.5)
            return .25;
        else
            return 0;
    }

    double coefficientGreaterEval(double val, double rec)
    {
        //
        double threshold = .1;
        if (val >= rec)
            return 1;
        else if (val >= rec - .25 * rec)
            return .75;
        else if (val >= rec - .5 * rec)
            return .5;
        else
            return 0;
    }

    double evalDiet(DietValues vals, List<Recipe> diet, List<String> recipeList)
    {
        //values can be adjusted here for algorithm optimization
        double proteinWeight = 2;
        double fatWeight = 3;
        double carbWeight = 3;
        double sugarWeight = 2;
        double sodiumWeight = 2;
        double cholWeight = 2;

        double protein, fat, carb, sugar, sodium,  chol;
        double proteinScore, fatScore, carbScore, sugarScore, sodiumScore,  cholScore;
        double score;

        protein = 0;
        fat = 0;
        carb = 0;
        sugar = 0;
        sodium = 0;
        chol = 0;

        for (int i = 0; i < diet.size(); i++)
        {
            try
            {
                protein += Double.parseDouble(diet.get(i).getProteins());

            }
            catch (Exception e)
            {

            }
            try
            {
                fat += Double.parseDouble(diet.get(i).getFat());

            }
            catch (Exception e)
            {

            }
            try
            {
                carb += Double.parseDouble(diet.get(i).getCarbs());

            }
            catch (Exception e)
            {

            }
            try
            {
                sugar += Double.parseDouble(diet.get(i).getSugar());

            }
            catch (Exception e)
            {

            }
            try
            {
                sodium += Double.parseDouble(diet.get(i).getSodium());

            }
            catch (Exception e)
            {

            }
            try
            {
                chol = Double.parseDouble(diet.get(i).getCholesterol());
            }
            catch (Exception e)
            {

            }
        }

        proteinScore = coefficientLessEval(protein, vals.proteinRec) * proteinWeight;
        fatScore = coefficientLessEval(fat, vals.fatRec) * fatWeight;
        carbScore = coefficientGreaterEval(carb, vals.calorieRec) * carbWeight;
        sugarScore = coefficientLessEval(sugar, vals.sugarRec) * sugarWeight;
        sodiumScore = coefficientLessEval(sodium, vals.sodiumRec) * sodiumWeight;
        cholScore = coefficientLessEval(chol, vals.cholRec) * cholWeight;

        score = proteinScore + fatScore + carbScore + sugarScore + sodiumScore + cholScore;

        //check if recipe already exists in list
        for (int i = 0; i < recipeList.size(); i++)
        {
            for (Recipe r: diet)
            {
                if (r.getName() == recipeList.get(i))
                    score *= .8;
            }
        }

        return score;
    }
}