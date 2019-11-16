package CS561.recipebox;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import CS561.recipebox.ui.PagerAdapter;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private pantryContractHelper pantryHelper;
    private int loadCounter = 0;

    private pantryContractHelper PCH;
    private int loadCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TAB VIEW

        PagerAdapter pagerAdapter = new PagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    public void testWrite()
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = PCH.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(pantryContract.Inventory.COLUMN_NAME_TITLE, "Test");
        values.put(pantryContract.Inventory.COLUMN_NAME_COUNT, "Test");

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(pantryContract.Inventory.TABLE_NAME, null, values);
    }

    public void testRead()
    {
        SQLiteDatabase db = PCH.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                pantryContract.Inventory.COLUMN_NAME_TITLE,
                pantryContract.Inventory.COLUMN_NAME_COUNT
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = pantryContract.Inventory.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                pantryContract.Inventory.COLUMN_NAME_COUNT + " DESC";

        Cursor cursor = db.query(
                pantryContract.Inventory.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(pantryContract.Inventory.TABLE_NAME));
            itemIds.add(itemId);
        }
        cursor.close();
    }
}
