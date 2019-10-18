package CS561.recipebox;

import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.test.espresso.Espresso;
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

import java.util.Random;

import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


@MediumTest
@RunWith(AndroidJUnit4.class)
public class testClass {

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

        String testInput;

        for (int i = 0; i < 100; i++)
        {
            testInput = "";
            int n = rand.nextInt(10) + 1;
            for (int j = 0; j < n; j++)
            {
                testInput += (char)(rand.nextInt(26) + 'a');
            }
            Espresso.onView(withId(R.id.searchView)).perform(clickXY(20,20));
            Espresso.onView(withId(R.id.searchView)).perform(typeText(testInput + "\n"));
            Espresso.onView(withId(R.id.searchView)).perform(clickXY(1000,20));
            Log.d("Unit Test input", testInput);
            Log.d("Unit Test output", activity.testOutput);
            assert(activity.testOutput == testInput);
        }
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
}

