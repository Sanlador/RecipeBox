package CS561.recipebox;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class recipe_ui  extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String[] value = (String[])extras.get("Input");
            //The key argument here must match that used in the other activity
            Log.d("Intent pass", value[0]);
        }
    }
}
