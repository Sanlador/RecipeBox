package CS561.recipebox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    //ArrayList<Recipe> recipes;

    // try something new
    public ArrayList<Recipe> recipes = new ArrayList<Recipe>(); //public for unit testing purposes
    public RecyclerView rvRecipes;
    public RecipesAdapter adapter;
    public String output;
    public String[] parsedOutput;


    //value used in unit testing to verify the output of using the search bar; COMMENT OUT FOR RELEASE BUILDS!
    //HIGHLY INSECURE
    public String testOutput[];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a temporary "box" for attaching adapter
        String[] s = {"Test", "Test", "Test", "Test", "Test"};
        // Create a temporary "box" for attaching adapter
        List<String[]> initializer = new ArrayList<String[]>();
        initializer.add(s);
        RecyclerView rvRecipes = (RecyclerView) findViewById(R.id.rvRecipes);

        // Initialize recipes
        recipes = Recipe.createRecipesList(0, initializer);
        // Create adapter passing in the sample user data
        RecipesAdapter adapter = new RecipesAdapter(recipes);
        // Attach the adapter to the recyclerview to populate items
        rvRecipes.setAdapter(adapter);
        // Set layout manager to position the items
        rvRecipes.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // Still don't know how to make adapter attached without creating one RecipeList
        recipes.clear();
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
            // parameter query here is the words what users just type in
            public boolean onQueryTextSubmit(String query) {
                String output;
                //call query function
                Log.d("Test", "Running DBQuery");
                try{
                    output = new DBQuery().execute(query).get();
                    Log.d("Query Output", output);
                    // get the biggest category from the result of search, which is all info from database of each recipe
                    List<String[]> parsedOutput = new ArrayList<String[]>();
                    String[] splitOutput;
                    //Parse output
                    if (output.split("~~~").length > 0)
                    {
                        String[] parse;
                        splitOutput = output.split("~~~");
                        for (String s: splitOutput)
                        {
                            parse = s.split("```");
                            parsedOutput.add(parse);
                        }

                        testOutput = splitOutput;
                        for (int i = 0; i < parsedOutput.size(); i++)
                        {
                            //Log.d("New Line", "");
                            Log.d("Parsed result " + (i+1), parsedOutput.get(i)[0]);
                        }

                        // Update recyclerview
                        recipes.clear();
                        if (parsedOutput.get(0).length > 1)
                        {
                            RecyclerView rvRecipes = (RecyclerView) findViewById(R.id.rvRecipes);
                            // Initialize recipes
                            recipes = Recipe.createRecipesList(parsedOutput.size()-1, parsedOutput);
                            // Create adapter passing in the sample user data
                            RecipesAdapter adapter = new RecipesAdapter(recipes);
                            // Attach the adapter to the recyclerview to populate items
                            rvRecipes.setAdapter(adapter);
                            // Set layout manager to position the items
                            rvRecipes.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        }
                    }
                    else
                    {
                        splitOutput = new String[] {output};
                        testOutput = splitOutput;
                        recipes.clear();
                        Log.d("Parsed result", splitOutput[0]);
                    }
                }
                catch (Exception e) {

                }
                //queryDB(query);
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

        Button button = findViewById(R.id.testButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), recipe_ui.class);
                String[] x = {"Test"};
                intent.putExtra("Input", x);
                startActivity(intent);
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

}
