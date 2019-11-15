package CS561.recipebox;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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


    public InventoryAdapter(List<InventoryItem> items, Context context)
    {
        itemList = items;
        context = context;
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
        public EditText itemCount;
        LinearLayout parent;

        public ViewHolder(View itemView)
        {
            super(itemView);
            subtractButton = (ImageButton) itemView.findViewById(R.id.subtractButton);
            addButton = (ImageButton) itemView.findViewById(R.id.addButton);
            title = (TextView) itemView.findViewById(R.id.title);
            subtractNum = (TextInputEditText) itemView.findViewById(R.id.subtractNumber);
            addNum = (TextInputEditText) itemView.findViewById(R.id.addNumber);
            itemCount = (EditText) itemView.findViewById(R.id.itemCount);
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

        // Set item views based on your views and data model
        TextView title = viewHolder.title;
        title.setText(item.getName());

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
