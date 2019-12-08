package CS561.recipebox.Diet;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import CS561.recipebox.Recipe.Recipe;

public class DietDP extends AsyncTask<String, String, String>
{

    private int pageNumber = 1000;

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

            String selectSql = "select * from RecipeScrape";

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

    private void DP(String survey, List<Recipe> recipies)
    {
        double calories = 2000;
        DietValues values = new DietValues();

        //adjust calorie intake based on survey

        //adjust diet values based on calorie intake
        values.adjust(calories / 2000);

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
        else if (Math.abs(val - rec) <= rec * threshold * 2)
            return .75;
        else if (Math.abs(val - rec) <= rec * threshold * 3)
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
        else if (val >= rec - .1 * rec)
            return .75;
        else if (val >= rec - .2 * rec)
            return .25;
        else
            return 0;
    }

    double evalDiet(DietValues vals, DietItem diet, List<String> recipeList)
    {
        //values can be adjusted here for algorithm optimization
        double proteinWeight = 2;
        double fatWeight = 2;
        double carbWeight = 3;
        double sugarWeight = 2;
        double sodiumWeight = 2;
        double cholWeight = 2;
        double calWeight = 10;

        double protein, fat, carb, sugar, sodium,  chol, cal;
        double proteinScore, fatScore, carbScore, sugarScore, sodiumScore,  cholScore, calScore;
        double score;

        Recipe breakfast, lunch, dinner;
        breakfast = diet.getBreakfast();
        lunch = diet.getLunch();
        dinner = diet.getDinner();

        protein = Double.parseDouble(breakfast.getProteins()) + Double.parseDouble(lunch.getProteins()) +Double.parseDouble(dinner.getProteins());
        fat = Double.parseDouble(breakfast.getFat()) + Double.parseDouble(lunch.getFat()) +Double.parseDouble(dinner.getFat());
        carb = Double.parseDouble(breakfast.getCarbs()) + Double.parseDouble(lunch.getCarbs()) +Double.parseDouble(dinner.getCarbs());
        sugar = Double.parseDouble(breakfast.getSugar()) + Double.parseDouble(lunch.getSugar()) +Double.parseDouble(dinner.getSugar());
        sodium = Double.parseDouble(breakfast.getSodium()) + Double.parseDouble(lunch.getSodium()) +Double.parseDouble(dinner.getSodium());
        chol = Double.parseDouble(breakfast.getCholesterol()) + Double.parseDouble(lunch.getCholesterol()) +Double.parseDouble(dinner.getCholesterol());
        cal = Double.parseDouble(breakfast.getCalories()) + Double.parseDouble(lunch.getCalories()) +Double.parseDouble(dinner.getCalories());

        proteinScore = coefficientLessEval(protein, vals.proteinRec) * proteinWeight;
        fatScore = coefficientLessEval(fat, vals.fatRec) * fatWeight;
        carbScore = coefficientGreaterEval(carb, vals.calorieRec) * carbWeight;
        sugarScore = coefficientLessEval(sugar, vals.sugarRec) * sugarWeight;
        sodiumScore = coefficientLessEval(sodium, vals.sodiumRec) * sodiumWeight;
        cholScore = coefficientLessEval(chol, vals.cholRec) * cholWeight;
        calScore = coefficientLessEval(cal, vals.calorieRec) * calWeight;
        score = proteinScore + fatScore + carbScore + sugarScore + sodiumScore + cholScore + calScore;

        //check if recipe already exists in list
        for (int i = 0; i < recipeList.size(); i++)
        {
            if (breakfast.getName() == recipeList.get(i))
                score *= .8;
            if (lunch.getName() == recipeList.get(i))
                score *= .8;
            if (dinner.getName() == recipeList.get(i))
                score *= .8;
        }

        return score;
    }
}