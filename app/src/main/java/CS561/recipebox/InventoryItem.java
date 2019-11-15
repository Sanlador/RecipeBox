package CS561.recipebox;

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

        for(String[] Item: dbList)
        {
            list.add(new InventoryItem(Integer.parseInt(Item[2]), Item[1]));
        }

        return list;
    }
}
