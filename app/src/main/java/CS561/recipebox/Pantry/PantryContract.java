package CS561.recipebox.Pantry;

import android.provider.BaseColumns;

public final class PantryContract
{
    private PantryContract()
    {

    }

    /* Inner class that defines the table contents */
    public static class Inventory implements BaseColumns
    {
        public static final String TABLE_NAME = "inventory_test";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_COUNT = "count";
    }

}
