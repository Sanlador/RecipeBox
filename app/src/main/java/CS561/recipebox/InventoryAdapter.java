package CS561.recipebox;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder>
{
    // Store a member variable for the recipes
    private List<InventoryItem> itemList;
    private Context context;
    private AdapterView.OnItemClickListener onItemClickListener;
    private InventoryContractHelper helper;


    public InventoryAdapter(List<InventoryItem> items, Context context)
    {
        itemList = items;
        context = context;
        helper = new InventoryContractHelper(context);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageButton subtractButton;
        public ImageButton addButton;
        public TextView title;
        public TextInputEditText subtractNum;
        public TextInputEditText addNum;
        public TextView itemCount;
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

        viewHolder.parent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return itemList.size();
    }
}
