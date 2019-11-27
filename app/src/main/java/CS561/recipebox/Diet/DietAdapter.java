package CS561.recipebox.Diet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import CS561.recipebox.R;
import CS561.recipebox.Recipe.Recipe;
import CS561.recipebox.Recipe.RecipeActivity;

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.ViewHolder>
{
    private  List<DietItem> itemList;
    private Context context;
    private DietFragment activity;

    public DietAdapter(ArrayList<DietItem> items, Context context, DietFragment fragment)
    {
        itemList = items;
        context = context;
        activity = fragment;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageButton breakfast, lunch, dinner;

        public ViewHolder(View itemView)
        {
            super(itemView);
            breakfast = (ImageButton) itemView.findViewById(R.id.breakfastButton);
            lunch = (ImageButton) itemView.findViewById(R.id.lunchButton);
            dinner = (ImageButton) itemView.findViewById(R.id.dinnerButton);
        }

    }

    public DietAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View recipeView = inflater.inflate(R.layout.item_diet, parent, false);

        // Return a new holder instance
        DietAdapter.ViewHolder viewHolder = new DietAdapter.ViewHolder(recipeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DietAdapter.ViewHolder viewHolder, int position)
    {
        DietItem item = itemList.get(position);
        ImageButton breakfast = viewHolder.breakfast;
        ImageButton lunch = viewHolder.lunch;
        ImageButton dinner = viewHolder.dinner;

        String pictureLink = item.getBreakfast().getrPictureLink();
        Picasso.get().load(pictureLink).into(breakfast);
        pictureLink = item.getLunch().getrPictureLink();
        Picasso.get().load(pictureLink).into(lunch);
        pictureLink = item.getDinner().getrPictureLink();
        Picasso.get().load(pictureLink).into(dinner);

        breakfast.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openRecipePage(item.getBreakfast());
            }
        });

        lunch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openRecipePage(item.getLunch());
            }
        });

        dinner.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openRecipePage(item.getDinner());
            }
        });
    }

    private void openRecipePage(Recipe recipe)
    {
        Intent intent = new Intent(activity.getActivity(), RecipeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Info", recipe.getInfo());
        intent.putExtra("Name", recipe.getName());
        intent.putExtra("ingredients", recipe.getIngredients());
        intent.putExtra("Picture", recipe.getrPictureLink());
        intent.putExtra("Catagories", recipe.getCatagories());
        intent.putExtra("Serving", recipe.getServing());
        intent.putExtra("Cooktime", recipe.getCooktime());
        intent.putExtra("Calories", recipe.getCalories());
        intent.putExtra("Fat", recipe.getFat());
        intent.putExtra("Carbs", recipe.getCarbs());
        intent.putExtra("Proteins", recipe.getProteins());
        intent.putExtra("Cholesterol", recipe.getCholesterol());
        intent.putExtra("Sodium", recipe.getSodium());
        activity.getActivity().startActivity(intent);
    }

    @Override
    public int getItemCount()
    {
        return itemList.size();
    }
}
