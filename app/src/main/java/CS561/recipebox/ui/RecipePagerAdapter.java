package CS561.recipebox.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import CS561.recipebox.Instruction.InstructionsFragment;
import CS561.recipebox.R;
import CS561.recipebox.Recipe.RecipeFragment;

public class RecipePagerAdapter extends FragmentPagerAdapter
{

    @StringRes
    private static final int[] TAB_TITLES = new int[]{ R.string.recipe_text, R.string.instructions_text};
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
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0)
            return RecipeFragment.newInstance(position + 1, extras);
        else
            return InstructionsFragment.newInstance(position + 1, extras);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}