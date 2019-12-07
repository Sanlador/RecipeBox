package CS561.recipebox.Nutrient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import CS561.recipebox.Inventory.InventoryContractHelper;
import CS561.recipebox.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NutrientAdapter extends RecyclerView.Adapter<NutrientAdapter.ViewHolder>
{
    private List<NutrientItem> itemList;
    private Context context;
    private InventoryContractHelper helper;
    private NutrientFragment activity;
    private TextView text;

    public NutrientAdapter(List<NutrientItem> items, Context context, NutrientFragment fragment)
    {
        itemList = items;
        context = context;
        helper = new InventoryContractHelper(context);
        activity = fragment;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {


        public ViewHolder(View itemView)
        {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.nutrientText);
        }

        @Override
        public void onClick(View view)
        {
            Log.d(TAG, "onClick " + getPosition());
        }
    }

    @Override
    public NutrientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View recipeView = inflater.inflate(R.layout.nutrient_item, parent, false);

        // Return a new holder instance
        NutrientAdapter.ViewHolder viewHolder = new NutrientAdapter.ViewHolder(recipeView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(NutrientAdapter.ViewHolder viewHolder, int position)
    {
        // Get the data model based on position
        NutrientItem item = itemList.get(position);
        text.setText(itemList.get(position).getString());
    }

    @Override
    public int getItemCount()
    {
        return itemList.size();
    }
}
