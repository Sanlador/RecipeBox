package CS561.recipebox.Inventory;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class InventoryItem
{
    private int count;
    private String name;


    public InventoryItem(int c, String n)
    {
        count = c;
        name = n;
    }

    public void setCount(int c)
    {
        count = c;
    }

    public String getName()
    {
        return name;
    }

    public int getCount()
    {
        return count;
    }

    public static ArrayList<InventoryItem> createInventoryList(Context context)
    {
        InventoryContractHelper contractHelper = new InventoryContractHelper(context);
        List<String[]> dbList = contractHelper.readFromDatabase();
        ArrayList<InventoryItem> list = new ArrayList<InventoryItem>();

        for(int i = 0; i < dbList.size(); i++)
        {
            list.add(new InventoryItem(Integer.parseInt(dbList.get(i)[1]), dbList.get(i)[0]));
        }

        return list;
    }
}
