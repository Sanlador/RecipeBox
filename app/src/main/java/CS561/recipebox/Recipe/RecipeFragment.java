package CS561.recipebox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

public class RecipeFragment extends Fragment
{
    String testName;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private View root;
    Bundle extras;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private ArrayList<Category> category;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_recipe, container, false);
        Context context = this.getContext();
        if (extras != null)
        {
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

            //setContentView(R.layout.RecipeActivity);


            TextView title = (TextView) root.findViewById(R.id.title);
            title.setText(name);
            //TextView ing = (TextView)findViewById(R.id.ingredients);
            //ing.setText(ingredients);
            TextView Info = (TextView) root.findViewById(R.id.instructions);
            Info.setText(   "Calories: "+ calories + " \t" + "Serving: " + serving + "\t" +  "Cook Time: " + cooktime + " (minutes)" +
                    "\n\n" + "Ingredients:\n\n\t" + ingredients +
                    "\n\n\nDirections:\n\n\t" + info
            );
            TextView info_rest = (TextView) root.findViewById(R.id.instructions_rest);
            info_rest.setText("\nNutrition:\n\n" +
                    "\tFat: " + fat + " grams\n" +
                    "\tCarbs: " + carbs + " grams\n" +
                    "\tProteins: " + proteins + " grams\n" +
                    "\tCholesterol: " + cholesterol + " grams\n" +
                    "\tSodium: " + sodium + " grams\n");

            //setContentView(title);

            ImageView pictureLink = (ImageView) root.findViewById(R.id.ui_pic);
            Picasso.get().load(url).into(pictureLink);

            String s = "Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test, Test";


            /*
            // Category
            RecyclerView rvCategory = (RecyclerView) root.findViewById(R.id.rvCategory);
            category = Category.createCategoryList(catagories);
            CategoryAdapter madapter = new CategoryAdapter(category, context);
            rvCategory.setAdapter(madapter);
            rvCategory.setLayoutManager(new LinearLayoutManager(context));
            LinearLayoutManager mlayoutManager= new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false);
            rvCategory.setLayoutManager(mlayoutManager);
             */

            // Using dummy data and also almost every category in each recipe is too to be scrollable
            RecyclerView rvCategory = (RecyclerView) root.findViewById(R.id.rvCategory);
            category = Category.createCategoryList(s);
            CategoryAdapter madapter = new CategoryAdapter(category, context);
            rvCategory.setAdapter(madapter);
            rvCategory.setLayoutManager(new LinearLayoutManager(context));
            LinearLayoutManager mlayoutManager= new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false);
            rvCategory.setLayoutManager(mlayoutManager);
        }
        return root;
    }

    public RecipeFragment(Bundle e)
    {
        extras = e;
    }

    public static RecipeFragment newInstance(int index, Bundle e)
    {
        RecipeFragment fragment = new RecipeFragment(e);
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public String getName()
    {
        return testName;
    }
}
