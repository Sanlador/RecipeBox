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
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import CS561.recipebox.Inventory.InventoryContractHelper;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

//Uses testClass as a template to run tests to confirm that query system is correctly implemented
@MediumTest
@RunWith(AndroidJUnit4.class)
public class DBTest
{

    int main()
    {
        Log.d("Test", "Test");
        try {
            testSearchBar();
        }
        catch (Exception e)
        {

        }
        return 0;
    }

    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);



    @Test
    public void testSearchBar() throws Exception {
        Log.d("Test", "Test executed.");
        MainActivity activity = rule.getActivity();
        View searchView = activity.findViewById(R.id.searchView);
        assertThat(searchView,notNullValue());
        assertThat(searchView, instanceOf(SearchView.class));
        Random rand = new Random();

        List<String> testInput = new ArrayList<String>();
        testInput.add("s");
        testInput.add("S");
        testInput.add("Pie");
        testInput.add("Key Lime");
        testInput.add("x");
        InventoryContractHelper pantryHelper;

        pantryHelper = new InventoryContractHelper(activity);

        String randInput;
        for (int i = 0; i < 10; i++)
        {
            randInput = "";
            int n = rand.nextInt(10) + 1;
            for (int j = 0; j < n; j++)
            {
                randInput += (char)(rand.nextInt(26) + 'a');
            }
            testInput.add(randInput);
        }

        List<String[]> output;
        for (int i = 0; i < testInput.size(); i++)
        {
            pantryHelper.writeToDatabase(testInput.get(i), i);

            if (i == 0)
            {
                output = pantryHelper.readFromDatabase();
                assert(output.size() == 1);
                assert(output.get(0)[0] == "1");
                assert(output.get(0)[1] == "s");
                assert(output.get(0)[2] == "0");
            }
            else if (i == 2)
            {
                output = pantryHelper.readFromDatabase();
                assert(output.size() == 3);
                assert(output.get(0)[0] == "1");
                assert(output.get(0)[1] == "s");
                assert(output.get(0)[2] == "0");
                assert(output.get(1)[0] == "2");
                assert(output.get(1)[1] == "S");
                assert(output.get(1)[2] == "1");
                assert(output.get(2)[0] == "3");
                assert(output.get(2)[1] == "Pie");
                assert(output.get(2)[2] == "2");

                pantryHelper.removeFromDatabase("Pie");
                assert(output.size() == 2);
            }
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
}

