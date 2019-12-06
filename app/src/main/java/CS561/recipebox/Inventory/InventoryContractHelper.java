package CS561.recipebox.Inventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class InventoryContractHelper extends SQLiteOpenHelper
{
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pantry.db";

    private static final String TABLE = InventoryContract.Inventory.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + InventoryContract.Inventory.TABLE_NAME + " (" +
                    InventoryContract.Inventory._ID + " INTEGER PRIMARY KEY," +
                    InventoryContract.Inventory.COLUMN_NAME_TITLE + " TEXT," +
                    InventoryContract.Inventory.COLUMN_NAME_COUNT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + InventoryContract.Inventory.TABLE_NAME;


    public InventoryContractHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }


    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean writeToDatabase(String name, int count)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        
        values.put(InventoryContract.Inventory.COLUMN_NAME_TITLE, name);
        values.put(InventoryContract.Inventory.COLUMN_NAME_COUNT, count);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(InventoryContract.Inventory.TABLE_NAME, null, values);

        if (-1 == newRowId)
        {
            Log.d("Database failure", "Failed to add new data");
            return false;
        }
        else
        {
            Log.d("Database success", "wrote to database");
            return true;
        }
    }

    public int removeFromDatabase(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            return db.delete(TABLE, "title = ?", new String[] {name});
        }
        catch (Exception e)
        {
            Log.e("Database Exception:", e.toString());
        }
        return 0;
    }

    public int changeCountValue(String name, int newCount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("count", newCount);
        try
        {
            return db.update(TABLE, cv,"title = ?", new String[] {name});
        }
        catch (Exception e)
        {
            Log.e("Database Exception:", e.toString());
        }
        return 0;
    }


    public List<String[]> readFromDatabase()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + InventoryContract.Inventory.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        List<String[]> dbOutput = new ArrayList<String[]>();

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext())
        {
            dbOutput.add(new String[]{cursor.getString(1), cursor.getString(2)});
            Log.d("Output data", cursor.getString(0));
            Log.d("Output data", cursor.getString(1));
            Log.d("Output data", cursor.getString(2));
        }
        cursor.close();

        return dbOutput;
    }
}
