package CS561.recipebox.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import CS561.recipebox.Instruction.InstructionsFragment;
import CS561.recipebox.Nutrient.NutrientFragment;
import CS561.recipebox.R;
import CS561.recipebox.RecipeFragment;

public class RecipePagerAdapter extends FragmentPagerAdapter
{

    @StringRes
    private static final int[] TAB_TITLES = new int[]{ R.string.recipe_text, R.string.instructions_text, R.string.nutrition_text};
    private final Context mContext;
    Bundle extras;

    public RecipePagerAdapter(Context context, FragmentManager fm, Bundle e)
    {
        super(fm);
        extras = e;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        int i = 0;
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (0 == position)
            return RecipeFragment.newInstance(position + 1, extras);
        else if (1 == position)
            return InstructionsFragment.newInstance(position + 1, extras);
        else
            //Saurabh: Change this line
            return NutrientFragment.newInstance(position + 1, extras);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}