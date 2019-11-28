package CS561.recipebox.Inventory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import CS561.recipebox.R;

public class InventoryFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private InventoryAdapter adapter;
    private View root;
    private InventoryContractHelper pantryHelper;
    private ArrayList<InventoryItem> inventoryList;
    private String InputText;
    private int InputCount;

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

        //set up recyclerview
        pantryHelper = new InventoryContractHelper(context);
        recyclerView = (RecyclerView) root.findViewById(R.id.inventoryList);
        inventoryList = InventoryItem.createInventoryList(context);
        adapter = new InventoryAdapter(inventoryList, context, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //set up add item button
        FloatingActionButton addItem = (FloatingActionButton) root.findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builderName = new AlertDialog.Builder(context);
                builderName.setTitle("Enter the name of the item");
                final EditText inputName = new EditText(context);
                inputName.setInputType(InputType.TYPE_CLASS_TEXT);
                builderName.setView(inputName);

                // Set up the buttons
                builderName.setPositiveButton("Submit", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        InputText = inputName.getText().toString();

                        AlertDialog.Builder builderCount = new AlertDialog.Builder(context);
                        builderCount.setTitle("Enter Number of the item");
                        final EditText inputCount = new EditText(context);
                        inputCount.setInputType(InputType.TYPE_CLASS_TEXT);
                        builderCount.setView(inputCount);

                        // Set up the buttons
                        builderCount.setPositiveButton("Submit", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                try
                                {
                                    InputCount = Integer.parseInt(inputCount.getText().toString());

                                    pantryHelper.writeToDatabase(InputText, InputCount);
                                    inventoryList.add(new InventoryItem(InputCount, InputText));
                                    adapter.notifyItemInserted(inventoryList.size() - 1);
                                }
                                catch (Exception e)
                                {
                                    Log.d("Exception", e.toString());
                                }
                            }
                        });
                        builderCount.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();
                            }
                        });

                        builderCount.show();
                    }
                });
                builderName.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

                builderName.show();



            }
        });

        return root;
    }

}
