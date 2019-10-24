package CS561.recipebox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    // Store a member variable for the recipes
    private List<Recipe> mRecipes;

    ////////////////////////////////////////
    private AdapterView.OnItemClickListener onItemClickListener;

    // Pass in the recipe array into the constructor
    public RecipesAdapter(List<Recipe> recipes) {
        this.mRecipes = recipes;
        //mRecipes = recipes;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView infoTextView;
        public Button recipeButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {

            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
            infoTextView = (TextView) itemView.findViewById(R.id.recipe_info);
            recipeButton = (Button) itemView.findViewById(R.id.recipe_button);
        }
    }

    @Override
    // Usually involves inflating a layout from XML and returning the holder
    public RecipesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View recipeView = inflater.inflate(R.layout.item_recipe, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(recipeView);
        return viewHolder;
    }

    public void updateData(ArrayList<Recipe> viewModels) {
        mRecipes.clear();
        mRecipes.addAll(viewModels);
        notifyDataSetChanged();
    }
    public void addItem(int position, Recipe viewModel) {
        mRecipes.add(position, viewModel);
        notifyItemInserted(position);
    }

    public void removeRecipe(int postion) {
        mRecipes.remove(postion);
        notifyDataSetChanged();
    }

    @Override
    // Involves populating data into the item through holder
    public void onBindViewHolder(RecipesAdapter.ViewHolder viewHolder, int position) {

        // Get the data model based on position
        Recipe recipe = mRecipes.get(position);

        // Set item views based on your views and data model
        TextView nameTextView = viewHolder.nameTextView;
        nameTextView.setText(recipe.getName());

        TextView infoTextView = viewHolder.infoTextView;
        infoTextView.setText(recipe.getInfo());

        /*
        Button button = viewHolder.messageButton;
        button.setText(recipe.isOnline() ? "Message" : "Offline");
        button.setEnabled(recipe.isOnline());
        */


    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

}