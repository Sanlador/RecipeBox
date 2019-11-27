package CS561.recipebox.Diet;

import java.util.ArrayList;
import java.util.List;

import CS561.recipebox.Recipe.Recipe;

public class DietItem
{
    private List<Recipe> plan;

    public DietItem(List<Recipe> list)
    {
        plan = list;
    }

    public List<Recipe> getPlan()
    {
        return plan;
    }

    public static ArrayList<DietItem> createDietPlan(List<Recipe[]> dietList)
    {
        ArrayList<DietItem> list = new ArrayList<DietItem>();

        for (int i = 0; i < dietList.size(); i++)
        {
            List<Recipe> l = new ArrayList<Recipe>();
            for (int j = 0; j < dietList.get(i).length; j++)
            {
                l.add(dietList.get(i)[j]);
            }
            list.add(new DietItem(l));
        }
        return list;
    }

    public Recipe getBreakfast()
    {
        return plan.get(0);
    }

    public Recipe getLunch()
    {
        return plan.get(1);
    }

    public Recipe getDinner()
    {
        return plan.get(2);
    }
}
