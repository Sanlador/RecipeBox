package CS561.recipebox;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;

public class SearchFragment extends Fragment
{
    public ArrayList<Recipe> recipes = new ArrayList<Recipe>(); //public for unit testing purposes
    public RecyclerView rvRecipes;
    public RecipesAdapter adapter;
    public String output;
    public String[] parsedOutput;

    private String[] data = {};
    private ListView mlistview;
    private ArrayAdapter mAdapter;
    private int loadCounter = 0;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private View root;

    //value used in unit testing to verify the output of using the search bar; COMMENT OUT FOR RELEASE BUILDS!
    //HIGHLY INSECURE
    public String testOutput[];
    public String savedQuery;
    public int recyclerViewLen;


    public static SearchFragment newInstance(int index)
    {
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_search, container, false);

        Context context = this.getContext();

        RelativeLayout wheel = (RelativeLayout) root.findViewById(R.id.loadingPanel);
        wheel.setVisibility(View.INVISIBLE);
        //RECYCLER VIEW

        // Create a temporary "box" for attaching adapter
        String[] s = {"Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test"};
        // Create a temporary "box" for attaching adapter
        List<String[]> initializer = new ArrayList<String[]>();
        initializer.add(s);
        ImageView rpic = root.findViewById(R.id.recipe_pic);

        RecyclerView rvRecipes = (RecyclerView) root.findViewById(R.id.rvRecipes);

        // Initialize recipes
        recipes = Recipe.createRecipesList(0, initializer);
        // Create adapter passing in the sample user data
        RecipesAdapter adapter = new RecipesAdapter(recipes, context);
        // Attach the adapter to the recyclerview to populate items
        rvRecipes.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Still don't know how to make adapter attached without creating one RecipeList
        recipes.clear();
        //SEARCH VIEW

        final SearchView sView = root.findViewById(R.id.searchView);

        rvRecipes.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1))
                {
                    try
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                showWheel();
                            }
                        });
                    } catch (Throwable throwable)
                    {
                        throwable.printStackTrace();
                    }

                    try
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
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
                                            RecipesAdapter adapter = new RecipesAdapter(recipes, context);
                                            rvRecipes.setAdapter(adapter);
                                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                                            rvRecipes.setLayoutManager(linearLayoutManager);
                                            rvRecipes.scrollToPosition(insertIndex+1);
                                        }
                                    }
                                }
                                catch (Exception e)
                                {

                                }
                            }
                        });
                    }
                    catch (Throwable throwable)
                    {
                        throwable.printStackTrace();
                    }
                }
            }
        });

        mlistview = (ListView) root.findViewById(R.id.listview);
        mAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, data);
        mlistview.setAdapter(mAdapter);
        mlistview.setTextFilterEnabled(true);

        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            // parameter query here is the words what users just type in
            public boolean onQueryTextSubmit(String query)
            {
                mlistview.setVisibility(View.GONE);
                rvRecipes.setVisibility(View.VISIBLE);
                savedQuery = query;
                String output;

                //call query function
                Log.d("Test", "Running DBQuery");
                try
                {
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
                            RecyclerView rvRecipes = (RecyclerView) root.findViewById(R.id.rvRecipes);
                            // Initialize recipes
                            recipes = Recipe.createRecipesList(parsedOutput.size()-1, parsedOutput);
                            // Create adapter passing in the sample user data
                            RecipesAdapter adapter = new RecipesAdapter(recipes, context);
                            // Attach the adapter to the recyclerview to populate items
                            rvRecipes.setAdapter(adapter);
                            // Set layout manager to position the items
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            rvRecipes.setLayoutManager(linearLayoutManager);
                        }
                    }
                    else
                    {
                        splitOutput = new String[] {output};
                        testOutput = splitOutput;
                        recipes.clear();
                    }
                }
                catch (Exception e)
                {

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                rvRecipes.setVisibility(View.GONE);
                mlistview.setVisibility(View.VISIBLE);
                String [] add = {};

                if (sView.getQuery().length() == 0) {
                    //renderList(true);
                    Log.d("Input", newText);
                }

                ArrayList<String> data = new ArrayList<>();
                Log.d("Test", "Running DBQuery");
                try
                {
                    loadCounter = 0;
                    output = new DBQuery().execute(Integer.toString(loadCounter) + "#" + newText).get();
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

                            // For autocomplete, 3 is title of recipes
                            // use the array 'data' later for showing it on listView
                            data.add(parse[3]);
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
                catch (Exception e)
                {

                }

                mlistview = (ListView) root.findViewById(R.id.listview);
                mAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, data);
                mlistview.setAdapter(mAdapter);
                mlistview.setTextFilterEnabled(true);
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
                Toast.makeText(context,str,Toast.LENGTH_SHORT).show();

                mlistview.setVisibility(View.GONE);
                rvRecipes.setVisibility(View.VISIBLE);

                savedQuery = recipe_name.toString();

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
                            RecyclerView rvRecipes = (RecyclerView) root.findViewById(R.id.rvRecipes);
                            // Initialize recipes
                            recipes = Recipe.createRecipesList(parsedOutput.size()-1, parsedOutput);
                            // Create adapter passing in the sample user data
                            RecipesAdapter adapter = new RecipesAdapter(recipes, context);
                            // Attach the adapter to the recyclerview to populate items
                            rvRecipes.setAdapter(adapter);
                            // Set layout manager to position the items
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
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
                catch (Exception e)
                {

                }
            }
        });

        return root;
    }

    // show wheel for the duration of couple seconds
    public void showWheel()
    {
        int wheelDurationInMilliSeconds = 2500;

        RelativeLayout wheel = (RelativeLayout) root.findViewById(R.id.loadingPanel);
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
}