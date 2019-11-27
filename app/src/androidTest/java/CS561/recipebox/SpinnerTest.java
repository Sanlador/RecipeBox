package CS561.recipebox;


import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SearchView;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.tabs.TabLayout;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Handler;

import static CS561.recipebox.testClass.clickXY;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withInputType;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;



//Uses testClass as a template to run tests to confirm that query system is correctly implemented
@MediumTest
@RunWith(AndroidJUnit4.class)
public class SpinnerTest {

    int main()
    {
        Log.d("Test", "Test");

        try
        {
            testFilterSearch();
        }
        catch (Exception e)
        {

        }

        return 0;
    }

    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    @Test
    public void testFilterSearch() throws Exception
    {
        Log.d("Test", "Test executed");
        MainActivity activity = rule.getActivity();
        View searchView = activity.findViewById(R.id.searchView);

        assertThat(searchView, notNullValue());
        assertThat(searchView, instanceOf(SearchView.class));

        Random rand = new Random();

        List<String> testInput = new ArrayList<String>();
        testInput.add("a");
        testInput.add("b");
        testInput.add("c");
        testInput.add("d");
        testInput.add("e");
        testInput.add("f");
        testInput.add("g");
        testInput.add("h");
        testInput.add("i");
        testInput.add("j");
        testInput.add("k");
        testInput.add("l");
        testInput.add("m");

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

        /*
        for (int i = 0; i < testInput.size(); i++)
        {

            // Click on ssearch bar
            Espresso.onView(withId(R.id.searchView)).perform(clickPosition(activity,20,20));

            // Input with hardcoded and random string
            Espresso.onView(withId(R.id.searchView)).perform(typeText(testInput.get(i) + "\n"));

            // Choose one checkbox randomly
            if (rand.nextBoolean())
                Espresso.onView(withId(R.id.radio_title)).perform(click());
            else
                Espresso.onView(withId(R.id.radio_ingredient)).perform(click());

            // Choose one spinner randomly
            if (rand.nextInt() % 4 == 0)
            {
                Espresso.onView(withId(R.id.MultiSpinnerCategory)).perform(click());
            }
            else if (rand.nextInt() % 4 == 1)
            {
                Espresso.onView(withId(R.id.MultiSpinnerIngredient)).perform(click());
            }
            else if (rand.nextInt() % 4 == 2)
            {
                Espresso.onView(withId(R.id.MultiSpinnerCategory)).perform(click());
                Espresso.onView(withId(R.id.MultiSpinnerIngredient)).perform(click());
            }
            else
            {

            }

            // Deleting input on search bar
            Espresso.onView(withId(R.id.searchView)).perform(clickPosition(activity, 20, 20));
            for (int j = 0; j < testInput.size(); j++) {
                Espresso.onView(withId(R.id.searchView))
                        .perform(pressKey(KeyEvent.KEYCODE_DEL));
            }

            Log.d("Unit Test input", testInput.get(i));
        }
        */

/*
        if (rand.nextBoolean())
            Espresso.onView(withId(R.id.radio_title)).perform(click());
        else
            Espresso.onView(withId(R.id.radio_ingredient)).perform(click());
*/


        /*
        if (rand.nextInt() % 4 == 0)
        {
            Espresso.onView(withId(R.id.MultiSpinnerCategory)).perform(click());

        }
        else if (rand.nextInt() % 4 == 1)
        {
            Espresso.onView(withId(R.id.MultiSpinnerIngredient)).perform(click());
        }
        else if (rand.nextInt() % 4 == 2)
        {
            Espresso.onView(withId(R.id.MultiSpinnerCategory)).perform(click());
            Espresso.onView(withId(R.id.MultiSpinnerIngredient)).perform(click());
        }
        else
        {

        }
         */

        Espresso.onView(withId(R.id.MultiSpinnerCategory)).perform(clickXY(0, 0));
        Espresso.onView(withText("Bread")).perform(clickXY(0, 0));
        Espresso.onView(withText("OK")).perform(clickXY(0, 0));

        Thread.sleep(2000);

        //Espresso.onView(withText(endsWith("Ingredient"))).check(matches(isDisplayed()));

        /*
        Espresso.onView(withId(R.id.MultiSpinnerIngredient)).perform(clickXY(0, 0));
        Espresso.onView(withText("peeled apple")).perform(clickXY(0, 0));
        Espresso.onView(withText("OK")).perform(clickXY(0, 0));


         */
        Espresso.pressBack();




        Espresso.onView(withId(R.id.MultiSpinnerIngredient)).perform(clickXY(0, 0));
        Espresso.onView(withText("peeled apple")).perform(clickXY(0, 0));
        Espresso.onView(withText("OK")).perform(clickXY(0, 0));

        Espresso.pressBack();

        Espresso.onView(withId(R.id.searchView)).perform(clickPosition(activity,20,20));
        Espresso.onView(withId(R.id.searchView)).perform(typeText(testInput.get(0) + "\n"));




       // Espresso.onView(withId(R.id.searchView)).perform(click()).perform(typeText(testInput.get(0)));


    }

    public static ViewAction clickXY(final int x, final int y){
        return new GeneralClickAction(
                Tap.SINGLE,
                new CoordinatesProvider() {
                    @Override
                    public float[] calculateCoordinates(View view) {

                        final int[] screenPos = new int[2];
                        view.getLocationOnScreen(screenPos);

                        final float screenX = screenPos[0] + x;
                        final float screenY = screenPos[1] + y;
                        float[] coordinates = {screenX, screenY};

                        return coordinates;
                    }
                },
                Press.FINGER);
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