package CS561.recipebox;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import CS561.recipebox.Diet.DietContractHelper;
import CS561.recipebox.Diet.DietItem;
import CS561.recipebox.Query.QueryForPantry;
import CS561.recipebox.Recipe.Recipe;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DietDBTest
{

    int main()
    {
        Log.d("Test", "Test");
        try
        {
            testDietDB();
        }
        catch (Exception e)
        {

        }
        return 0;
    }

    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);



    @Test
    public void testDietDB() throws Exception
    {
        Log.d("Test", "Test executed.");
        MainActivity activity = rule.getActivity();
        View searchView = activity.findViewById(R.id.searchView);
        assertThat(searchView,notNullValue());
        assertThat(searchView, instanceOf(SearchView.class));
        Random rand = new Random();
        DietContractHelper contractHelper = new DietContractHelper(activity);

        ArrayList<DietItem> output;
        List<DietItem> dietList = dummyOutput("a");
        Log.d("Output", Integer.toString(dietList.size()));

        for (int i = 0; i < 20; i++)
        {
            List<Recipe> tempList = new ArrayList<Recipe>();
            tempList.add(dietList.get(i).getBreakfast());
            tempList.add(dietList.get(i).getLunch());
            tempList.add(dietList.get(i).getDinner());
            contractHelper.writeToDatabase(tempList);
        }

        List<DietItem> dietDB = contractHelper.readFromDatabase();

        for (int i = 0; i < dietDB.size(); i++)
        {
            assert(dietDB.get(i).getBreakfast().getName() == dietList.get(i).getBreakfast().getName());
            assert(dietDB.get(i).getLunch().getName() == dietList.get(i).getLunch().getName());
            assert(dietDB.get(i).getDinner().getName() == dietList.get(i).getDinner().getName());
        }
    }

    public static ViewAction clickPosition (MainActivity activity, int x, int y)
    {
        CoordinatesProvider coordProvider = new CoordinatesProvider() {
            @Override
            public float[] calculateCoordinates(View view)
            {
                return coords(activity, x, y);
            }
        };
        return new GeneralClickAction(Tap.SINGLE, coordProvider, Press.FINGER);
    }

    public static float[] coords(MainActivity activity, int x, int y)
    {
        int[] screenCoords = new int[2];
        View view = activity.findViewById(R.id.searchView);
        view.getLocationOnScreen(screenCoords);
        float[] coordinates = {screenCoords[0] + x, screenCoords[1] + y};
        return coordinates;
    }

    public static ViewAction clickPosition (Activity activity, int x, int y)
    {
        CoordinatesProvider coordProvider = new CoordinatesProvider() {
            @Override
            public float[] calculateCoordinates(View view)
            {
                return coords(activity, x, y);
            }
        };
        return new GeneralClickAction(Tap.SINGLE, coordProvider, Press.FINGER);
    }

    public static float[] coords(Activity activity, int x, int y)
    {
        int[] screenCoords = new int[2];
        View view = activity.findViewById(R.id.searchView);
        view.getLocationOnScreen(screenCoords);
        float[] coordinates = {screenCoords[0] + x, screenCoords[1] + y};
        return coordinates;
    }

    private List<DietItem> dummyOutput(String input)
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
                    for (int i = 0; i < recipes.size(); i++)
                    {
                        Recipe[] temp = {recipes.get(i), recipes.get(i), recipes.get(i)};
                        inputList.add(temp);
                    }
                    ArrayList<DietItem> diet = DietItem.createDietPlan(inputList);
                    return diet;
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
}