package CS561.recipebox.Recipe;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import CS561.recipebox.Category;
import CS561.recipebox.CategoryAdapter;
import CS561.recipebox.R;

public class RecipeFragment extends Fragment
{
    String testName;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private View root;
    Bundle extras;
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
            TextView Servings = (TextView) root.findViewById(R.id.final_servings);
            TextView Prep = (TextView) root.findViewById(R.id.final_prep);
            TextView Cook = (TextView) root.findViewById(R.id.final_cook);
            TextView Ready = (TextView) root.findViewById(R.id.final_ready);
            //TextView Ingredients = (TextView) root.findViewById(R.id.recipe_ingredients);

            Servings.setText("Servings:"+serving);
            Prep.setText(" 10m  ");
            Cook.setText("   20m  ");
            Ready.setText("  30m  ");


            //Ingredients.setText("Ingredients:\n\n\t"+ingredients);

            Info.setText(   "Ingredients:\n\n\t" + ingredients);
            //setContentView(title);

            ImageView pictureLink = (ImageView) root.findViewById(R.id.ui_pic);
            Picasso.get().load(url).into(pictureLink);
            // Category
            RecyclerView rvCategory = (RecyclerView) root.findViewById(R.id.rvCategory);
            category = Category.createCategoryList(catagories);
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
