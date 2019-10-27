package CS561.recipebox;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class recipe_ui  extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = (String)extras.get("Name");
            String ingredients = (String)extras.get("ingredients");
            String info = (String)extras.get("Info");
            //The key argument here must match that used in the other activity
            Log.d("Intent pass", name);

            setContentView(R.layout.recipe_ui);
            TextView title = (TextView)findViewById(R.id.title);
            title.setText(name);
            TextView ing = (TextView)findViewById(R.id.ingredients);
            ing.setText(ingredients);
            TextView Info = (TextView)findViewById(R.id.instructions);
            Info.setText(info);
            //setContentView(title);
        }
    }
}
