package CS561.recipebox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static androidx.test.InstrumentationRegistry.getContext;

public final class pantryContract
{
    private pantryContract()
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
