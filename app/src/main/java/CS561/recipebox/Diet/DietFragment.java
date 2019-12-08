package CS561.recipebox.Diet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import CS561.recipebox.Query.QueryForDiet;
import CS561.recipebox.Query.QueryForPantry;
import CS561.recipebox.R;
import CS561.recipebox.Recipe.Recipe;

public class DietFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private View root;
    private Button button;
    private ArrayList<DietItem> dietList;
    private DietAdapter adapter;
    private DietContractHelper contractHelper;

    public RecyclerView recyclerView;

    public static DietFragment newInstance(int index)
    {
        DietFragment fragment = new DietFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_diet, container, false);
        Context context = this.getContext();
        contractHelper = new DietContractHelper(context);
        dietList = contractHelper.readFromDatabase();

        adapter = new DietAdapter(dietList, context, this);
        recyclerView = (RecyclerView) root.findViewById(R.id.dietList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        button = (Button) root.findViewById(R.id.dietButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                button.setVisibility(View.INVISIBLE);
                //Play loading animation?
                dietList.add(dummyOutput("a"));
                adapter.notifyDataSetChanged();
                try
                {
                    new QueryForDiet().execute("");
                }
                catch (Exception e)
                {
                    Log.d("Exception", e.toString());
                }
            }
        });

        return root;
    }

    private DietItem dummyOutput(String input)
    {
        String output;

        //call query function
        Log.d("Test", "Running DBQuery");
        try
        {
            output = new QueryForPantry().execute(input).get();

            // get the biggest category from the result of search, which is all info from database of each recipe
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
                    List<Recipe[]> inputList = new ArrayList<Recipe[]>();
                    Recipe[] i = {recipes.get(0), recipes.get(1), recipes.get(2)};
                    inputList.add(i);
                    ArrayList<DietItem> diet = DietItem.createDietPlan(inputList);
                    return diet.get(0);
                }
            }
            else
            {
                Log.d("Recommendation", "Failed to make recommendation; No output");
            }
        }
        catch (Exception e)
        {
            Log.d("Exception", e.toString());
        }
        return null;
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
            sodiumRec = 2.4; //less than
            cholRec = .3;    //less than
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
