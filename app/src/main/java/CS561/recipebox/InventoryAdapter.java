package CS561.recipebox;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder>
{
    // Store a member variable for the recipes
    private List<InventoryItem> itemList;
    private Context context;
    private AdapterView.OnItemClickListener onItemClickListener;
    private InventoryContractHelper helper;
    private InventoryFragment activity;


    public InventoryAdapter(List<InventoryItem> items, Context context, InventoryFragment fragment)
    {
        itemList = items;
        context = context;
        helper = new InventoryContractHelper(context);
        activity = fragment;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageButton subtractButton;
        public ImageButton addButton;
        public ImageButton deleteButton;
        public TextView title;
        public TextInputEditText subtractNum;
        public TextInputEditText addNum;
        public TextView itemCount;
        public Button recommend;
        LinearLayout parent;

        public ViewHolder(View itemView)
        {
            super(itemView);
            subtractButton = (ImageButton) itemView.findViewById(R.id.subtractButton);
            addButton = (ImageButton) itemView.findViewById(R.id.addButton);
            title = (TextView) itemView.findViewById(R.id.inventory_name);
            subtractNum = (TextInputEditText) itemView.findViewById(R.id.subtractNumber);
            addNum = (TextInputEditText) itemView.findViewById(R.id.addNumber);
            itemCount = (TextView) itemView.findViewById(R.id.itemCount);
            parent = itemView.findViewById(R.id.inventoryParent);
            deleteButton = (ImageButton) itemView.findViewById(R.id.deleteButton);
            recommend = (Button) itemView.findViewById(R.id.reccomend);
        }

        @Override
        public void onClick(View view)
        {
            Log.d(TAG, "onClick " + getPosition());
        }
    }

    @Override
    public InventoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View recipeView = inflater.inflate(R.layout.item_inventory, parent, false);

        // Return a new holder instance
        InventoryAdapter.ViewHolder viewHolder = new InventoryAdapter.ViewHolder(recipeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InventoryAdapter.ViewHolder viewHolder, int position)
    {

        // Get the data model based on position
        InventoryItem item = itemList.get(position);
        TextView name = viewHolder.title;
        name.setText(item.getName());
        TextView count = viewHolder.itemCount;
        count.setText(String.valueOf(item.getCount()));

        ImageButton add = viewHolder.addButton;
        ImageButton subtract = viewHolder.subtractButton;
        ImageButton delete = viewHolder.deleteButton;
        Button recommend = viewHolder.recommend;

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int addition = item.getCount() + Integer.parseInt(viewHolder.addNum.getText().toString());
                viewHolder.itemCount.setText(String.valueOf(addition));
                item.setCount(addition);
                helper.changeCountValue(item.getName(), addition);
            }
        });

        subtract.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int subtraction = item.getCount() - Integer.parseInt(viewHolder.subtractNum.getText().toString());
                if (subtraction <= 0)
                    subtraction = 0;
                viewHolder.itemCount.setText(String.valueOf(subtraction));
                item.setCount(subtraction);
                helper.changeCountValue(item.getName(), subtraction);
            }
        });

        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                helper.removeFromDatabase(item.getName());
                itemList.remove(viewHolder.getAdapterPosition());
                notifyItemRemoved(viewHolder.getAdapterPosition());
                Log.d("Deleting", "Position " + viewHolder.getAdapterPosition());
            }
        });

        recommend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                recommendQuery(view, item.getName());
            }
        });

        viewHolder.parent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
    }

    private void recommendQuery(View v, String name)
    {
        String output;

        //call query function
        Log.d("Test", "Running DBQuery");
        try
        {
            output = new QueryForPantry().execute(name).get();

            // get the biggest category from the result of search, which is all info from database of each recipe
            List<String[]> parsedOutput = new ArrayList<String[]>();
            String[] splitOutput;
            //Parse output
            if (output.split("~~~").length > 0)
            {
                String[] parse;
                splitOutput = output.split("~~~");
                for (String s: splitOutput)
                {
                    parse = s.split("```");
                    parsedOutput.add(parse);
                }
                if (parsedOutput.get(0).length > 1)
                {
                    List<Recipe> recipes = Recipe.createRecipesList(parsedOutput.size()-1, parsedOutput);
                    Log.d("Output", recipes.get(0).getName());
                    Random random = new Random();
                    int index = random.nextInt(recipes.size());

                    Intent intent = new Intent(activity.getActivity(), recipe_ui.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Info", recipes.get(index).getInfo());
                    intent.putExtra("Name", recipes.get(index).getName());
                    intent.putExtra("ingredients", recipes.get(index).getIngredients());
                    intent.putExtra("Picture", recipes.get(index).getrPictureLink());
                    intent.putExtra("Catagories", recipes.get(index).getCatagories());
                    intent.putExtra("Serving", recipes.get(index).getServing());
                    intent.putExtra("Cooktime", recipes.get(index).getCooktime());
                    intent.putExtra("Calories", recipes.get(index).getCalories());
                    intent.putExtra("Fat", recipes.get(index).getFat());
                    intent.putExtra("Carbs", recipes.get(index).getCarbs());
                    intent.putExtra("Proteins", recipes.get(index).getProteins());
                    intent.putExtra("Cholesterol", recipes.get(index).getCholesterol());
                    intent.putExtra("Sodium", recipes.get(index).getSodium());
                    activity.getActivity().startActivity(intent);
                }
            }
            else
            {
                Log.d("Recommendation", "Failed to make recommendation; No output");
            }
        }
        catch (Exception e)
        {

        }

    }

    @Override
    public int getItemCount()
    {
        return itemList.size();
    }
}
