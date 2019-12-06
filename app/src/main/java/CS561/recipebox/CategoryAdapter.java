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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>
{
    private List<Category> mCategory;
    private Context mContext;

    private AdapterView.OnItemClickListener onItemClickListener;

    public CategoryAdapter(List<Category> category, Context context)
    {
        this.mCategory = category;
        this.mContext = context;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView categoryTextView;
        LinearLayout parent;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            categoryTextView = (TextView)itemView.findViewById(R.id.category_type);
            parent = itemView.findViewById(R.id.parent);
        }

        @Override
        public void onClick(View view)
        {
            Log.d(TAG, "onClick " + getPosition());
        }
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context= parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View categoryView = inflater.inflate(R.layout.item_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(categoryView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position)
    {
        Category category = mCategory.get(position);

        TextView categoryTextView = holder.categoryTextView;
        categoryTextView.setText(category.getType());
    }

    @Override
    public int getItemCount()
    {
        return mCategory.size();
    }
}
