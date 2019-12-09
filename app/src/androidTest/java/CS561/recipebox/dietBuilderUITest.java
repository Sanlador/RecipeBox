package CS561.recipebox;

import android.text.InputType;
import android.util.Log;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.tabs.TabLayout;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import CS561.recipebox.Inventory.InventoryContractHelper;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withInputType;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class dietBuilderUITest
{

    int main()
    {
        Log.d("Test", "Test");
        try
        {
            testAddAndRemove();
        }
        catch (Exception e)
        {

        }
        return 0;
    }

    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);



    @Test
    public void testAddAndRemove() throws Exception
    {
        Log.d("Test", "Test executed.");
        MainActivity activity = rule.getActivity();
        View tabLayout = activity.findViewById(R.id.tabs);
        assertThat(tabLayout,notNullValue());
        assertThat(tabLayout, instanceOf(TabLayout.class));
        Random rand = new Random();
        List<String> testInput = new ArrayList<String>();
        List<Integer> testNum = new ArrayList<Integer>();
        InventoryContractHelper helper = new InventoryContractHelper(activity.getApplicationContext());
        List<String[]> verify;


        Espresso.onView(withId(R.id.tabs)).perform(clickPosition(activity,1000,-50));
        Espresso.onView(withId(R.id.dietButton)).perform(click());
        /*Espresso.onView(withText("How many hours per week do you exercise (be honest)")).check(matches(isDisplayed()));
        Espresso.onView(withId(android.R.id.button2)).perform(click());*/

        for (int i = 0; i < testInput.size(); i++)
        {

            Espresso.onView(withId(R.id.addItem)).perform(click());
            Espresso.onView(withText("How many hours per week do you exercise (be honest)")).check(matches(isDisplayed()));
            Espresso.onView(withInputType(InputType.TYPE_CLASS_TEXT)).perform(typeText("15"));
            Espresso.onView(withId(android.R.id.button1)).perform(click());
            Espresso.onView(withText("Enter your height (in inches)")).check(matches(isDisplayed()));
            Espresso.onView(withInputType(InputType.TYPE_CLASS_TEXT)).perform(typeText("72"));
            Espresso.onView(withId(android.R.id.button1)).perform(click());
            Espresso.onView(withText("Enter your weight (in pounds)")).check(matches(isDisplayed()));
            Espresso.onView(withInputType(InputType.TYPE_CLASS_TEXT)).perform(typeText("180"));
            Espresso.onView(withId(android.R.id.button1)).perform(click());

            verify = helper.readFromDatabase();
            assert(verify.get(verify.size() - 1)[0] == testInput.get((i)));

            Espresso.onView(withId(R.id.inventoryList)).perform(RecyclerViewActions.actionOnItemAtPosition(verify.size() - 1,
                    MyViewAction.clickChildViewWithId(R.id.deleteButton)));
            verify = helper.readFromDatabase();
            assert(verify.get(verify.size() - 1)[0] != testInput.get((i)));
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