package CS561.recipebox.Diet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import CS561.recipebox.Recipe.Recipe;


public class DietContractHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "diet.db";

    private static final String TABLE = DietContract.Diet.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + DietContract.Diet.TABLE_NAME + " (" +
                    DietContract.Diet._ID + " INTEGER PRIMARY KEY," +
                    DietContract.Diet.COLUMN_NAME_RECIPE_BREAKFAST + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_INFO_BREAKFAST + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_INGREDIENTS_BREAKFAST + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_URL_BREAKFAST + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_CATAGORIES_BREAKFAST + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_SERVING_BREAKFAST + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_COOKTIME_BREAKFAST + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_CALORIES_BREAKFAST + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_FAT_BREAKFAST + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_CARBS_BREAKFAST + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_STRING_PROTEIN_BREAKFAST + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_CHOLESTEROL_BREAKFAST + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_SODIUM_BREAKFAST + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_RECIPE_LUNCH + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_INFO_LUNCH + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_INGREDIENTS_LUNCH + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_URL_LUNCH + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_CATAGORIES_LUNCH + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_SERVING_LUNCH + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_COOKTIME_LUNCH + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_CALORIES_LUNCH + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_FAT_LUNCH + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_CARBS_LUNCH + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_STRING_PROTEIN_LUNCH + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_CHOLESTEROL_LUNCH + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_SODIUM_LUNCH + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_RECIPE_DINNER + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_INFO_DINNER + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_INGREDIENTS_DINNER + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_URL_DINNER + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_CATAGORIES_DINNER + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_SERVING_DINNER + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_COOKTIME_DINNER + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_CALORIES_DINNER + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_FAT_DINNER + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_CARBS_DINNER + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_STRING_PROTEIN_DINNER + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_CHOLESTEROL_DINNER + " TEXT," +
                    DietContract.Diet.COLUMN_NAME_SODIUM_DINNER + " TEXT" +
                    ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DietContract.Diet.TABLE_NAME;

    public DietContractHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        catch (Exception e)
        {
            Log.d("Exception", e.toString());
        }
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

    public boolean writeToDatabase(List<Recipe> recipes, int id)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(DietContract.Diet._ID, id);

        values.put(DietContract.Diet.COLUMN_NAME_RECIPE_BREAKFAST, recipes.get(0).getName());
        values.put(DietContract.Diet.COLUMN_NAME_INFO_BREAKFAST, recipes.get(0).getInfo());
        values.put(DietContract.Diet.COLUMN_NAME_INGREDIENTS_BREAKFAST, recipes.get(0).getIngredients());
        values.put(DietContract.Diet.COLUMN_NAME_URL_BREAKFAST, recipes.get(0).getrPictureLink());
        values.put(DietContract.Diet.COLUMN_NAME_CATAGORIES_BREAKFAST, recipes.get(0).getCatagories());
        values.put(DietContract.Diet.COLUMN_NAME_SERVING_BREAKFAST, recipes.get(0).getServing());
        values.put(DietContract.Diet.COLUMN_NAME_COOKTIME_BREAKFAST, recipes.get(0).getCooktime());
        values.put(DietContract.Diet.COLUMN_NAME_CALORIES_BREAKFAST, recipes.get(0).getCalories());
        values.put(DietContract.Diet.COLUMN_NAME_FAT_BREAKFAST, recipes.get(0).getFat());
        values.put(DietContract.Diet.COLUMN_NAME_CARBS_BREAKFAST, recipes.get(0).getCarbs());
        values.put(DietContract.Diet.COLUMN_NAME_STRING_PROTEIN_BREAKFAST, recipes.get(0).getProteins());
        values.put(DietContract.Diet.COLUMN_NAME_CHOLESTEROL_BREAKFAST, recipes.get(0).getCholesterol());
        values.put(DietContract.Diet.COLUMN_NAME_SODIUM_BREAKFAST, recipes.get(0).getSodium());


        values.put(DietContract.Diet.COLUMN_NAME_RECIPE_LUNCH, recipes.get(1).getName());
        values.put(DietContract.Diet.COLUMN_NAME_INFO_LUNCH, recipes.get(1).getInfo());
        values.put(DietContract.Diet.COLUMN_NAME_INGREDIENTS_LUNCH, recipes.get(1).getIngredients());
        values.put(DietContract.Diet.COLUMN_NAME_URL_LUNCH, recipes.get(1).getrPictureLink());
        values.put(DietContract.Diet.COLUMN_NAME_CATAGORIES_LUNCH, recipes.get(1).getCatagories());
        values.put(DietContract.Diet.COLUMN_NAME_SERVING_LUNCH, recipes.get(1).getServing());
        values.put(DietContract.Diet.COLUMN_NAME_COOKTIME_LUNCH, recipes.get(1).getCooktime());
        values.put(DietContract.Diet.COLUMN_NAME_CALORIES_LUNCH, recipes.get(1).getCalories());
        values.put(DietContract.Diet.COLUMN_NAME_FAT_LUNCH, recipes.get(1).getFat());
        values.put(DietContract.Diet.COLUMN_NAME_CARBS_LUNCH, recipes.get(1).getCarbs());
        values.put(DietContract.Diet.COLUMN_NAME_STRING_PROTEIN_LUNCH, recipes.get(1).getProteins());
        values.put(DietContract.Diet.COLUMN_NAME_CHOLESTEROL_LUNCH, recipes.get(1).getCholesterol());
        values.put(DietContract.Diet.COLUMN_NAME_SODIUM_LUNCH, recipes.get(1).getSodium());


        values.put(DietContract.Diet.COLUMN_NAME_RECIPE_DINNER, recipes.get(2).getName());
        values.put(DietContract.Diet.COLUMN_NAME_INFO_DINNER, recipes.get(2).getInfo());
        values.put(DietContract.Diet.COLUMN_NAME_INGREDIENTS_DINNER, recipes.get(2).getIngredients());
        values.put(DietContract.Diet.COLUMN_NAME_URL_DINNER, recipes.get(2).getrPictureLink());
        values.put(DietContract.Diet.COLUMN_NAME_CATAGORIES_DINNER, recipes.get(2).getCatagories());
        values.put(DietContract.Diet.COLUMN_NAME_SERVING_DINNER, recipes.get(2).getServing());
        values.put(DietContract.Diet.COLUMN_NAME_COOKTIME_DINNER, recipes.get(2).getCooktime());
        values.put(DietContract.Diet.COLUMN_NAME_CALORIES_DINNER, recipes.get(2).getCalories());
        values.put(DietContract.Diet.COLUMN_NAME_FAT_DINNER, recipes.get(2).getFat());
        values.put(DietContract.Diet.COLUMN_NAME_CARBS_DINNER, recipes.get(2).getCarbs());
        values.put(DietContract.Diet.COLUMN_NAME_STRING_PROTEIN_DINNER, recipes.get(2).getProteins());
        values.put(DietContract.Diet.COLUMN_NAME_CHOLESTEROL_DINNER, recipes.get(2).getCholesterol());
        values.put(DietContract.Diet.COLUMN_NAME_SODIUM_DINNER, recipes.get(2).getSodium());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DietContract.Diet.TABLE_NAME, null, values);

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

    public int removeFromDatabase(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            return db.delete(TABLE, "_id = ?", new String[] {Integer.toString(id)});
        }
        catch (Exception e)
        {
            Log.e("Database Exception:", e.toString());
        }
        return 0;
    }

    public ArrayList<DietItem> readFromDatabase()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DietContract.Diet.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<DietItem> dbOutput = new ArrayList<DietItem>();

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext())
        {
            //dbOutput.add(new String[]{cursor.getString(1), cursor.getString(2)});
            Recipe breakfast, lunch, dinner;
            List<Recipe> recipeList = new ArrayList<Recipe>();
            breakfast = new Recipe(cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10),
                    cursor.getString(11), cursor.getString(12), cursor.getString(13));
            lunch = new Recipe( cursor.getString(14), cursor.getString(15),
                cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19),
                cursor.getString(20), cursor.getString(21), cursor.getString(22), cursor.getString(23),
                cursor.getString(24), cursor.getString(25), cursor.getString(26));
            dinner = new Recipe( cursor.getString(27), cursor.getString(28),
                    cursor.getString(29), cursor.getString(30), cursor.getString(31), cursor.getString(32),
                    cursor.getString(33), cursor.getString(34), cursor.getString(35), cursor.getString(36),
                    cursor.getString(37), cursor.getString(38), cursor.getString(39));
            recipeList.add(breakfast);
            recipeList.add(lunch);
            recipeList.add(dinner);

            dbOutput.add(new DietItem(recipeList));
        }
        cursor.close();

        return dbOutput;
    }
}
