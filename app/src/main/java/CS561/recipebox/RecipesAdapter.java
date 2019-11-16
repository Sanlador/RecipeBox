package CS561.recipebox;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder>
{

    // Store a member variable for the recipes
    private List<Recipe> mRecipes;
    private Context mContext;

    // Later on
    private AdapterView.OnItemClickListener onItemClickListener;

    // Pass in the recipe array into the constructor
    public RecipesAdapter(List<Recipe> recipes, Context context)
    {
        this.mRecipes = recipes;
        this.mContext = context;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView infoTextView;
        public ImageView picImageView;
        public Button recipeButton;
        LinearLayout parent;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView)
        {

            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.inventory_name);
            infoTextView = (TextView) itemView.findViewById(R.id.recipe_info);
            picImageView = (ImageView) itemView.findViewById(R.id.recipe_pic);
            parent = itemView.findViewById(R.id.parent);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick " + getPosition());
        }
    }

    @Override
    // Usually involves inflating a layout from XML and returning the holder
    public RecipesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View recipeView = inflater.inflate(R.layout.item_recipe, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(recipeView);
        return viewHolder;
    }

    @Override
    // Involves populating data into the item through holder
    public void onBindViewHolder(RecipesAdapter.ViewHolder viewHolder, int position)
    {

        // Get the data model based on position
        Recipe recipe = mRecipes.get(position);

        // Set item views based on your views and data model
        TextView nameTextView = viewHolder.nameTextView;
        nameTextView.setText(recipe.getName());

        TextView infoTextView = viewHolder.infoTextView;
        infoTextView.setText(recipe.getCatagories());

        ImageView picImageView = viewHolder.picImageView;
        Picasso.get().load(recipe.getrPictureLink()).into(picImageView);

        viewHolder.parent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, recipe_ui.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Info", mRecipes.get(position).getInfo());
                intent.putExtra("Name", mRecipes.get(position).getName());
                intent.putExtra("ingredients", mRecipes.get(position).getIngredients());
                intent.putExtra("Picture", mRecipes.get(position).getrPictureLink());
                intent.putExtra("Catagories", mRecipes.get(position).getCatagories());
                intent.putExtra("Serving", mRecipes.get(position).getServing());
                intent.putExtra("Cooktime", mRecipes.get(position).getCooktime());
                intent.putExtra("Calories", mRecipes.get(position).getCalories());
                intent.putExtra("Fat", mRecipes.get(position).getFat());
                intent.putExtra("Carbs", mRecipes.get(position).getCarbs());
                intent.putExtra("Proteins", mRecipes.get(position).getProteins());
                intent.putExtra("Cholesterol", mRecipes.get(position).getCholesterol());
                intent.putExtra("Sodium", mRecipes.get(position).getSodium());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }
}