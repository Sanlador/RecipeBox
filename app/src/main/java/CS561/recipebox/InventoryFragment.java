package CS561.recipebox;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InventoryFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private InventoryAdapter adapter;
    private View root;
    private InventoryContractHelper pantryHelper;
    private ArrayList<InventoryItem> inventoryList;

    public RecyclerView recyclerView;

    public static InventoryFragment newInstance(int index)
    {
        InventoryFragment fragment = new InventoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_pantry, container, false);
        Context context = this.getContext();

        recyclerView = (RecyclerView) root.findViewById(R.id.inventoryList);
        inventoryList = InventoryItem.createInventoryList(context);
        adapter = new InventoryAdapter(inventoryList, context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }

}
