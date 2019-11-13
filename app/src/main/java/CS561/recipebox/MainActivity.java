package CS561.recipebox;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] data = { "Steaks", "Fries", "Teriyaki Chicken", "Chicken wings", "Steak and Kale Soup", "Best Steak Marinade in Existence", "Rolled Flank Steak", "Autumn Spice Ham Steak", "Beer Cheese Philly Steak Casserole", "Sweet Grilled Steak Bites", "Soy Marinated Skirt Steak"};
    private ListView mlistview;
    private ArrayAdapter mAdapter;
    private SearchView sview;

    private AppBarConfiguration mAppBarConfiguration;

    // Initialization for the recyclerview
    public ArrayList<Recipe> recipes = new ArrayList<Recipe>(); //public for unit testing purposes
    public RecyclerView rvRecipes;
    public RecipesAdapter adapter;
    public String output;
    public String[] parsedOutput;

    //value used in unit testing to verify the output of using the search bar; COMMENT OUT FOR RELEASE BUILDS!
    //HIGHLY INSECURE
    public String testOutput[];

    // For the purpose of refilling data when user scroll the recyclerview to the very bottom
    public String savedQuery;
    private int loadCounter = 0;
    public int recyclerViewLen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declare wheel here and hide it :)
        RelativeLayout wheel = (RelativeLayout) findViewById(R.id.loadingPanel);
        wheel.setVisibility(View.INVISIBLE);

        // Create a temporary "box" for attaching adapter
        String[] s = {"Test", "Test", "Test", "Test", "Test"};
        List<String[]> initializer = new ArrayList<String[]>();
        initializer.add(s);

        // Initialize recipes
        RecyclerView rvRecipes = (RecyclerView) findViewById(R.id.rvRecipes);
        recipes = Recipe.createRecipesList(0, initializer);
        // Create adapter passing in the sample user data
        RecipesAdapter adapter = new RecipesAdapter(recipes, getApplicationContext());
        // Attach the adapter to the recyclerview to populate items
        rvRecipes.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rvRecipes.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // Still don't know how to make adapter attached without creating one RecipeList
        recipes.clear();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);

        rvRecipes.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWheel();
                        }
                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("System","Scrolling hits the bottom");
                            loadCounter++;
                            try
                            {
                                String addedOutput = new DBQuery().execute(Integer.toString(loadCounter) + "#" + savedQuery).get();
                                if (addedOutput.split("~~~").length > 0)
                                {
                                    List<String[]> parsedOutput = new ArrayList<String[]>();
                                    String[] parse;
                                    String[] splitOutput = addedOutput.split("~~~");
                                    for (String s: splitOutput)
                                    {
                                        parse = s.split("```");
                                        parsedOutput.add(parse);
                                    }
                                    testOutput = splitOutput;
                                    if (parsedOutput.get(0).length > 1)
                                    {
                                        ArrayList<Recipe> addedRecipes = new ArrayList<Recipe>();
                                        addedRecipes = Recipe.createRecipesList(parsedOutput.size()-1, parsedOutput);

                                        for (Recipe r :addedRecipes) {
                                            recipes.add(r);
                                        }

                                        int insertIndex = (loadCounter * 10)-1;
                                        recipes.addAll(insertIndex, addedRecipes);
                                        recyclerViewLen = recipes.size();
                                        adapter.notifyItemRangeInserted(insertIndex, addedRecipes.size());
                                        adapter.notifyDataSetChanged();
                                        RecipesAdapter adapter = new RecipesAdapter(recipes, getApplicationContext());
                                        rvRecipes.setAdapter(adapter);
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                                        rvRecipes.setLayoutManager(linearLayoutManager);
                                        rvRecipes.scrollToPosition(insertIndex+1);
                                    }
                                }
                            }
                            catch (Exception e){ }
                        }
                    });
                }
            }
        });

        mlistview = (ListView) findViewById(R.id.listview);
        mAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, data);
        mlistview.setAdapter(mAdapter);
        mlistview.setTextFilterEnabled(true);
        final SearchView sView = findViewById(R.id.searchView);
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mlistview.setVisibility(View.GONE);
                rvRecipes.setVisibility(View.VISIBLE);
                savedQuery = query;
                String output;
                //call query function
                Log.d("Test", "Running DBQuery");
                try{
                    loadCounter = 0;
                    output = new DBQuery().execute(Integer.toString(loadCounter) + "#" + query).get();
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

                        // Update recyclerview
                        recipes.clear();
                        if (parsedOutput.get(0).length > 1)
                        {
                            RecyclerView rvRecipes = (RecyclerView) findViewById(R.id.rvRecipes);
                            // Initialize recipes
                            recipes = Recipe.createRecipesList(parsedOutput.size()-1, parsedOutput);
                            // Create adapter passing in the sample user data
                            RecipesAdapter adapter = new RecipesAdapter(recipes, getApplicationContext());
                            // Attach the adapter to the recyclerview to populate items
                            rvRecipes.setAdapter(adapter);
                            // Set layout manager to position the items
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                            rvRecipes.setLayoutManager(linearLayoutManager);
                        }
                    }
                    else
                    {
                        splitOutput = new String[] {output};
                        testOutput = splitOutput;
                        recipes.clear();
                        //Log.d("Parsed result", splitOutput[0]);
                    }
                }
                catch (Exception e) { }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rvRecipes.setVisibility(View.GONE);
                mlistview.setVisibility(View.VISIBLE);

                if (sView.getQuery().length() == 0) {
                    //renderList(true);
                    Log.d("Input", newText);
                }
                mAdapter.getFilter().filter(newText);

                return false;
            }
        });

        mlistview.setClickable(true);
        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                Object recipe_name = mlistview.getItemAtPosition(position);
                String str=(String)recipe_name;//As you are using Default String Adapter
                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();

                mlistview.setVisibility(View.GONE);
                rvRecipes.setVisibility(View.VISIBLE);

                savedQuery = recipe_name.toString();
                sview.setQuery(savedQuery, false);
                Log.d("Test", "Running DBQuery");
                try{
                    loadCounter = 0;
                    output = new DBQuery().execute(Integer.toString(loadCounter) + "#" + recipe_name).get();
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

                        // Update recyclerview
                        recipes.clear();
                        if (parsedOutput.get(0).length > 1)
                        {
                            RecyclerView rvRecipes = (RecyclerView) findViewById(R.id.rvRecipes);
                            // Initialize recipes
                            recipes = Recipe.createRecipesList(parsedOutput.size()-1, parsedOutput);
                            // Create adapter passing in the sample user data
                            RecipesAdapter adapter = new RecipesAdapter(recipes, getApplicationContext());
                            // Attach the adapter to the recyclerview to populate items
                            rvRecipes.setAdapter(adapter);
                            // Set layout manager to position the items
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                            rvRecipes.setLayoutManager(linearLayoutManager);
                        }
                    }
                    else
                    {
                        splitOutput = new String[] {output};
                        testOutput = splitOutput;
                        recipes.clear();
                        //Log.d("Parsed result", splitOutput[0]);
                    }
                }
                catch (Exception e) { }

            }
        });

    }


    // show wheel for the duration of couple seconds
    public void showWheel() {
        int wheelDurationInMilliSeconds = 2500;

        RelativeLayout wheel = (RelativeLayout) findViewById(R.id.loadingPanel);
        wheel.setVisibility(View.INVISIBLE);

        CountDownTimer wheelCountDown;
        wheelCountDown = new CountDownTimer(wheelDurationInMilliSeconds, 1000) {
            @Override
            public void onTick(long l) {
                wheel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                wheel.setVisibility(View.INVISIBLE);
            }
        };
        wheel.setVisibility(View.VISIBLE);
        wheelCountDown.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}