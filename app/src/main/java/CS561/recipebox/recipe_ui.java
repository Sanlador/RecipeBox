package CS561.recipebox;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class recipe_ui  extends AppCompatActivity
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
            //The key argument here must match that used in the other activity
            Log.d("Intent pass", name);

            setContentView(R.layout.recipe_ui);
            TextView title = (TextView)findViewById(R.id.title);
            title.setText(name);
            //TextView ing = (TextView)findViewById(R.id.ingredients);
            //ing.setText(ingredients);
            TextView Info = (TextView)findViewById(R.id.instructions);
            Info.setText("Ingredients:\n\n" + ingredients + "\n\n\nDirections:\n\n" + info);
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
