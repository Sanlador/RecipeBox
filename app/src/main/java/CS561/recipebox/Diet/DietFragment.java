package CS561.recipebox.Diet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import CS561.recipebox.R;

public class DietFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";
    //private InventoryAdapter adapter;
    private View root;
    //private InventoryContractHelper pantryHelper;
    //private ArrayList<InventoryItem> inventoryList;
    //private String InputText;
    //private int InputCount;

    public static DietFragment newInstance(int index)
    {
        DietFragment fragment = new DietFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_diet, container, false);
        Context context = this.getContext();

        return root;
    }
}
