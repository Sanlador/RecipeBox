package CS561.recipebox;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class Checkbox  extends AppCompatActivity
{
    String testName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = testName = (String)extras.get("Name");
            String ingredients = (String)extras.get("ingredients");
            String info = (String)extras.get("Info");
            String url = (String)extras.get("Picture");
            String catagories = (String)extras.get("Catagories");
            String serving = (String)extras.get("Serving");
            String cooktime = (String)extras.get("Cooktime");
            String calories = (String)extras.get("Calories");
            String fat = (String)extras.get("Fat");
            String carbs = (String)extras.get("Carbs");
            String proteins = (String)extras.get("Proteins");
            String cholesterol = (String)extras.get("Cholesterol");
            String sodium = (String)extras.get("Sodium");

            //The key argument here must match that used in the other activity
            Log.d("Intent pass", name);

            setContentView(R.layout.recipe_ui);
            TextView title = (TextView)findViewById(R.id.title);
            title.setText(name);
            //TextView ing = (TextView)findViewById(R.id.ingredients);
            //ing.setText(ingredients);
            TextView Info = (TextView)findViewById(R.id.instructions);
            Info.setText(   "Calories: "+ calories + " \t" + "Serving: " + serving + "\t" +  "Cook Time: " + cooktime + " (minutes)" +
                    "\n\n" + "Ingredients:\n\n\t" + ingredients +
                    "\n\n\nDirections:\n\n\t" + info +
                    "\n\nCatagories:\n\n\t" + catagories + "\n" +
                    "\nNutrition:\n\n" +
                    "\tFat: " + fat + " grams\n" +
                    "\tCarbs: " + carbs + " grams\n" +
                    "\tProteins: " + proteins + " grams\n" +
                    "\tCholesterol: " + cholesterol + " grams\n" +
                    "\tSodium: " + sodium + " grams\n"
            );
            //setContentView(title);

            ImageView pictureLink = (ImageView)findViewById(R.id.ui_pic);
            Picasso.get().load(url).into(pictureLink);
        }
    }

    public String getName()
    {
        return testName;
    }
}
