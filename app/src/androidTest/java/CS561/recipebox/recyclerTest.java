package CS561.recipebox;

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

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

//Uses testClass as a template to run tests to confirm that query system is correctly implemented
@MediumTest
@RunWith(AndroidJUnit4.class)
public class recyclerTest {

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
        testInput.add("Fries");
        testInput.add("fries");
        testInput.add("fRies");
        testInput.add("FRIES");
        testInput.add("");
        testInput.add("sTEAK");
        testInput.add("STEAK");
        testInput.add("Ste");
        testInput.add("Steak");
        testInput.add("sliders");
        testInput.add("sliders.");
        testInput.add("Sliders");
        testInput.add("s");


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


        for (int i = 0; i < testInput.size(); i++)
        {

            /*Espresso.onView(withId(R.id.searchView)).perform(clickPosition(activity,20,20));
            Espresso.onView(withId(R.id.searchView)).perform(typeText(testInput.get(i) + "\n"));
            Espresso.onView(withId(R.id.searchView)).perform(clickPosition(activity,1000,20));
            Log.d("Unit Test input", testInput.get(i));
            if (i < 4)
                assert(activity.recipes.get(0).getName() == "Chef John's French Fries 1 russet potato, cut into evenly sized strips;1 russet potato, cut into evenly sized strips");   //incorrect value in DB, will fix later
            else if (i == 4)
                assert(activity.testOutput.length == 0);
            else if (i > 4 && i < 10)
                assert(activity.recipes.get(0).getName() == "Marsala Marinated Skirt Steak 2/3 cup Marsala wine; 1/4 cup ketchup; 6 cloves garlic, minced; 2 teaspoons kosher salt; 1 teaspoon dried rosemary; 1 teaspoon ground black pepper; 1 (1 1/2-pound) skirt steak, cut in half across the grain;");
            else if (i >= 9 && i < 12)
                assert(activity.recipes.get(0).getName() == "Slider-Style Mini Burgers 2 pounds ground beef; 1 (1.25 ounce) envelope onion soup mix; 1/2 cup mayonnaise; 2 cups shredded Cheddar cheese; 24 dinner rolls;1/2 cup sliced pickles;");
            else if (i == 12)
            {
                assert(activity.recipes.get(0).getName() == "Slider-Style Mini Burgers 2 pounds ground beef; 1 (1.25 ounce) envelope onion soup mix; 1/2 cup mayonnaise; 2 cups shredded Cheddar cheese; 24 dinner rolls;1/2 cup sliced pickles;");
                assert(activity.recipes.get(0).getName() == "Marsala Marinated Skirt Steak 2/3 cup Marsala wine; 1/4 cup ketchup; 6 cloves garlic, minced; 2 teaspoons kosher salt; 1 teaspoon dried rosemary; 1 teaspoon ground black pepper; 1 (1 1/2-pound) skirt steak, cut in half across the grain;");
                assert(activity.recipes.get(0).getName() == "Chef John's French Fries 1 russet potato, cut into evenly sized strips;1 russet potato, cut into evenly sized strips");
            }
            else if (i > 12)
                assert(activity.recipes.size() == 0);*/
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
}