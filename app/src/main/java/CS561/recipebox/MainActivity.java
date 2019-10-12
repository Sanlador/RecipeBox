package CS561.recipebox;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        final SearchView sView = findViewById(R.id.searchView);

        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //call query function
                queryDB(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (sView.getQuery().length() == 0) {
                    //renderList(true);
                    Log.d("Input", newText);
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Uses user's search input to populate query, which is sent to SQL DB
    public String queryDB(String search)
    {
        if (search.length() < 1)
            return null;
        Log.d("Function","Launching Query");
        List<String> output = new ArrayList<String>();

        String host = "recipebox01.database.windows.net";
        String db = "RecipeDB";
        String user = "recipeOSU";
        String password = "recipe32!";
        String url = "jdbc:sqlserver://recipebox01.database.windows.net:1433;database=RecipeDB;user=recipeOSU@recipebox01;password=recipe32!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Connection connection = null;

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            DriverManager.getDriver(url);
            connection = DriverManager.getConnection(url);
            //String schema = connection.getSchema();
            //System.out.println("Successful connection - Schema: " + schema);
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM [dbo].[Test]  WHERE NAME = " + search;
            Log.d("Query", selectSql);
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSql))
            {
                while (resultSet.next())
                {
                    Log.d("Output", resultSet.getString(1) + " "
                            + resultSet.getString(2));
                }
                connection.close();
            }
        }
        catch (Exception e) {
            Log.d("Exception", "Connection failed");
            Log.e("Exception:", e.toString());
        }
        //temporary output. Will change once DB connection is implemented.
        return search;
        //return output;
    }
}
