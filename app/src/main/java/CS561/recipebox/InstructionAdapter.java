package CS561.recipebox;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.ViewHolder>
{
    private List<InstructionItem> itemList;
    private Context context;
    private InventoryContractHelper helper;
    private InstructionsFragment activity;
    private TextView text;

    public InstructionAdapter(List<InstructionItem> items, Context context, InstructionsFragment fragment)
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
            text = (TextView) itemView.findViewById(R.id.instructionCheck);
        }

        @Override
        public void onClick(View view)
        {
            Log.d(TAG, "onClick " + getPosition());
        }
    }

    @Override
    public InstructionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View recipeView = inflater.inflate(R.layout.instruction_item, parent, false);

        // Return a new holder instance
        InstructionAdapter.ViewHolder viewHolder = new InstructionAdapter.ViewHolder(recipeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InstructionAdapter.ViewHolder viewHolder, int position)
    {
        // Get the data model based on position
        InstructionItem item = itemList.get(position);
        text.setText(itemList.get(position).getString());
    }

    @Override
    public int getItemCount()
    {
        return itemList.size();
    }
}
