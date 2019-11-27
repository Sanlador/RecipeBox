package CS561.recipebox.ui;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import CS561.recipebox.Inventory.InventoryFragment;
import CS561.recipebox.R;
import CS561.recipebox.Search.SearchFragment;

public class HomePagerAdapter extends FragmentPagerAdapter
{

    @StringRes
    private static final int[] TAB_TITLES = new int[]{ R.string.search_text, R.string.pantry_text, R.string.diet_text};
    private final Context mContext;

    public HomePagerAdapter(Context context, FragmentManager fm)
    {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (0 == position)
            return SearchFragment.newInstance(position + 1);
        else if (1 == position)
            return InventoryFragment.newInstance(position + 1);
        else
            return SearchFragment.newInstance(position + 1);
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
        return 3;
    }
}