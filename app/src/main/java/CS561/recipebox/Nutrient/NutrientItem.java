package CS561.recipebox.Nutrient;

import java.util.ArrayList;

public class NutrientItem
{
    private String text;

    public NutrientItem(String s)
    {
        text = s;
    }

    public String getString()
    {
        return text;
    }

    public static ArrayList<NutrientItem> createNutrientList(String[] Nutrient)
    {
        ArrayList<NutrientItem> list = new ArrayList<NutrientItem>();

        for (int i = 0; i < Nutrient.length; i++)
        {
            list.add(new NutrientItem(Nutrient[i]));
        }
        return list;
    }
}
