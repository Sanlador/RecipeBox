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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;

import java.util.ArrayList;
import java.util.List;

import CS561.recipebox.ui.gallery.GalleryViewModel;

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
    private pantryContractHelper pantryHelper;
    private int loadCounter = 0;
    private GalleryViewModel galleryViewModel;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private View root;

    //value used in unit testing to verify the output of using the search bar; COMMENT OUT FOR RELEASE BUILDS!
    //HIGHLY INSECURE
    public String testOutput[];
    public String savedQuery;
    public String last;
    public int recyclerViewLen;
    public String checkbox = "Recipe";
    public String[] selected;
    public List<KeyPairBoolData> listArray = new ArrayList<>();
    public List<String> list = new ArrayList<>();
    public List<String> filter_prefered_tags  = new ArrayList<>();
    public String concat = "";

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
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_search, container, false);
        Context context = this.getContext();
        String tags_output;
        String[] split_output;
        MultiSpinnerSearch searchMultiSpinnerUnlimited = (MultiSpinnerSearch) root.findViewById(R.id.searchMultiSpinnerUnlimited);

        // Building up spinner that based on the input that is given from 'QueryForTags'
        try
        {
            tags_output = new QueryForTags().execute().get();
            Log.d("Query Output", tags_output);
            split_output = tags_output.split("~~~");
            selected = tags_output.split("~~~");
            for (String s : split_output)
            {
                list.add(s);
            }

            for (int i = 0; i < list.size(); i++)
            {
                KeyPairBoolData h = new KeyPairBoolData();
                h.setId(i + 1);
                h.setName(list.get(i));
                h.setSelected(false);
                listArray.add(h);
            }
            searchMultiSpinnerUnlimited.setEmptyTitle("Not Data Found!");
            searchMultiSpinnerUnlimited.setSearchHint("Find Data");
            searchMultiSpinnerUnlimited.setItems(listArray, -1, new SpinnerListener()
            {
                @Override
                public void onItemsSelected(List<KeyPairBoolData> items)
                {
                    filter_prefered_tags.clear();
                    concat = "";
                    for (int i = 0; i < items.size(); i++)
                    {
                        if (items.get(i).isSelected())
                        {
                            Log.i("TAG Selected", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                            filter_prefered_tags.add(items.get(i).getName());
                        }
                    }
                }
            });
        }
        catch (Exception e)
        {

        }

        // Wheel animation
        RelativeLayout wheel = (RelativeLayout) root.findViewById(R.id.loadingPanel);
        wheel.setVisibility(View.INVISIBLE);

        // Radio events
        RadioGroup radioGroup = (RadioGroup) root.findViewById(R.id.radioGroup);
        RadioButton radio_title = (RadioButton) root.findViewById(R.id.radio_title);
        RadioButton radio_ingredient = (RadioButton) root.findViewById(R.id.radio_ingredient);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            // A mmethon does the reponse whenever there is event that happened when radiogroup is
            // updataed
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                if (radio_title.isChecked())
                {
                    Log.d("Radio", "Title");
                    checkbox = "Recipe";
                }
                else if (radio_ingredient.isChecked())
                {
                    Log.d("Radio", "Ingredient");
                    checkbox = "Ingredients";
                }


                ArrayList<String> data = new ArrayList<>();
                Log.d("Test", "Running DBQuery");
                try
                {
                    loadCounter = 0;
                    output = new DBQuery().execute(Integer.toString(loadCounter) + "#" + checkbox + "#" + last).get();
                    //Log.d("Query Output", output);

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
                //mAdapter.getFilter().filter(newText);

            }
        });

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

                // When users scroll down and hit the bottom on recyclerview then this event is
                // triggered
                if (!recyclerView.canScrollVertically(1))
                {

                    Log.d("System", "Scrolling hits the bottom");
                    loadCounter++;
                    if (concat == "")
                    {
                        try {
                            String addedOutput = new DBQuery().execute(Integer.toString(loadCounter) + "#" + checkbox + "#" + savedQuery).get();
                            if (addedOutput.split("~~~").length > 0)
                            {
                                List<String[]> parsedOutput = new ArrayList<String[]>();
                                String[] parse;
                                String[] splitOutput = addedOutput.split("~~~");
                                for (String s : splitOutput)
                                {
                                    parse = s.split("```");
                                    parsedOutput.add(parse);
                                }
                                testOutput = splitOutput;
                                if (parsedOutput.get(0).length > 1)
                                {
                                    ArrayList<Recipe> addedRecipes = new ArrayList<Recipe>();
                                    addedRecipes = Recipe.createRecipesList(parsedOutput.size() - 1, parsedOutput);

                                    for (Recipe r : addedRecipes)
                                    {
                                        recipes.add(r);
                                    }

                                    int insertIndex = (loadCounter * 10) - 1;
                                    recipes.addAll(insertIndex, addedRecipes);
                                    recyclerViewLen = recipes.size();
                                    adapter.notifyItemRangeInserted(insertIndex, addedRecipes.size());
                                    adapter.notifyDataSetChanged();
                                    RecipesAdapter adapter = new RecipesAdapter(recipes, context);
                                    rvRecipes.setAdapter(adapter);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                                    rvRecipes.setLayoutManager(linearLayoutManager);
                                    rvRecipes.scrollToPosition(insertIndex + 1);
                                    showWheel();
                                }
                                else
                                {
                                    Toast.makeText(context,"No more results",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        catch (Exception e)
                        {

                        }
                    }
                    else if (concat != "")
                    {
                        try
                        {
                            String addedOutput = new FilterQuery().execute(Integer.toString(loadCounter) + "#" + checkbox + "#" + concat + "#" + savedQuery).get();
                            if (addedOutput.split("~~~").length > 0)
                            {
                                List<String[]> parsedOutput = new ArrayList<String[]>();
                                String[] parse;
                                String[] splitOutput = addedOutput.split("~~~");
                                for (String s : splitOutput)
                                {
                                    parse = s.split("```");
                                    parsedOutput.add(parse);
                                }
                                testOutput = splitOutput;
                                if (parsedOutput.get(0).length > 1)
                                {
                                    ArrayList<Recipe> addedRecipes = new ArrayList<Recipe>();
                                    addedRecipes = Recipe.createRecipesList(parsedOutput.size() - 1, parsedOutput);

                                    for (Recipe r : addedRecipes)
                                    {
                                        recipes.add(r);
                                    }

                                    int insertIndex = (loadCounter * 10) - 1;
                                    recipes.addAll(insertIndex, addedRecipes);
                                    recyclerViewLen = recipes.size();
                                    adapter.notifyItemRangeInserted(insertIndex, addedRecipes.size());
                                    adapter.notifyDataSetChanged();
                                    RecipesAdapter adapter = new RecipesAdapter(recipes, context);
                                    rvRecipes.setAdapter(adapter);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                                    rvRecipes.setLayoutManager(linearLayoutManager);
                                    rvRecipes.scrollToPosition(insertIndex + 1);
                                    showWheel();
                                }
                                else
                                {
                                    Toast.makeText(context,"No more results",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        catch (Exception e)
                        {

                        }
                    }
                }
            }
        });

        // Update the autocomplete on the listview
        mlistview = (ListView) root.findViewById(R.id.listview);
        mAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, data);
        mlistview.setAdapter(mAdapter);
        mlistview.setTextFilterEnabled(true);

        // A method that listens to whenever there is an event happening on search bar and does the
        // response to it
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
                for (String s : filter_prefered_tags)
                {
                    concat += s + "```";
                }
                Log.d("Submit", concat);
                if (concat == "")
                {
                    //call query function
                    Log.d("Test", "Running DBQuery sumbit without concat");
                    try
                    {
                        loadCounter = 0;
                        output = new DBQuery().execute(Integer.toString(loadCounter) + "#" + checkbox + "#" + query).get();
                        Log.d("Query Output", output);

                        // get the biggest category from the result of search, which is all info from database of each recipe
                        List<String[]> parsedOutput = new ArrayList<String[]>();
                        String[] splitOutput;
                        //Parse output
                        if (output.split("~~~").length > 0)
                        {
                            String[] parse;
                            splitOutput = output.split("~~~");
                            for (String s : splitOutput)
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
                                recipes = Recipe.createRecipesList(parsedOutput.size() - 1, parsedOutput);
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
                            splitOutput = new String[]{output};
                            testOutput = splitOutput;
                            recipes.clear();
                        }
                    }
                    catch (Exception e)
                    {

                    }
                }
                else if (concat != "")
                {
                    Log.d("Test", "Running DBQuery sumbit with concat");
                    try
                    {
                        loadCounter = 0;
                        output = new FilterQuery().execute(Integer.toString(loadCounter) + "#" + checkbox + "#" + concat + "#" + query).get();
                        Log.d("Query Output", output);

                        // get the biggest category from the result of search, which is all info from database of each recipe
                        List<String[]> parsedOutput = new ArrayList<String[]>();

                        String[] splitOutput;


                        //Parse output
                        if (output.split("~~~").length > 0)
                        {
                            String[] parse;
                            splitOutput = output.split("~~~");
                            for (String s : splitOutput)
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
                                recipes = Recipe.createRecipesList(parsedOutput.size() - 1, parsedOutput);
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
                            splitOutput = new String[]{output};
                            testOutput = splitOutput;
                            recipes.clear();
                            //Log.d("Parsed result", splitOutput[0]);
                        }
                    }
                    catch (Exception e)
                    {

                    }
                }
                return false;
            }

            // A method that listens to texts are being changed and does the response to it
            @Override
            public boolean onQueryTextChange(String newText)
            {

                // Disable the recyclerview and enable listview's visibility
                rvRecipes.setVisibility(View.GONE);
                mlistview.setVisibility(View.VISIBLE);

                ArrayList<String> data = new ArrayList<>();
                last = newText;
                if (sView.getQuery().length() == 0)
                {
                    //renderList(true);
                    Log.d("Input", newText);
                }
                String concat = "";
                for (String s : filter_prefered_tags)
                {
                    concat += s + "```";
                }

                // If there are tags preferences then do the filter search
                if (concat != "")
                {
                    //Log.d("Tag", "Empty");
                    Log.d("Test", "Running DBQuery");
                    try
                    {
                        loadCounter = 0;
                        output = new FilterQuery().execute(Integer.toString(loadCounter) + "#" + checkbox + "#" + concat + "#" + newText).get();
                        Log.d("Query Output", output);
                        // get the biggest category from the result of search, which is all info from database of each recipe
                        List<String[]> parsedOutput = new ArrayList<String[]>();
                        String[] splitOutput;
                        //Parse output
                        if (output.split("~~~").length > 0)
                        {
                            String[] parse;
                            splitOutput = output.split("~~~");
                            for (String s : splitOutput)
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
                            splitOutput = new String[]{output};
                            testOutput = splitOutput;
                            recipes.clear();
                            //Log.d("Parsed result", splitOutput[0]);
                        }
                    }
                    catch (Exception e)
                    {

                    }
                }

                // If there is no tags preferences then go straight to searching
                else
                {
                    Log.d("Test", "Running DBQuery");
                    try
                    {
                        loadCounter = 0;
                        output = new DBQuery().execute(Integer.toString(loadCounter) + "#" + checkbox + "#" + newText).get();
                        Log.d("Query Output", output);
                        // get the biggest category from the result of search, which is all info from database of each recipe
                        List<String[]> parsedOutput = new ArrayList<String[]>();
                        String[] splitOutput;
                        //Parse output
                        if (output.split("~~~").length > 0)
                        {
                            String[] parse;
                            splitOutput = output.split("~~~");
                            for (String s : splitOutput)
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
                            splitOutput = new String[]{output};
                            testOutput = splitOutput;
                            recipes.clear();
                            //Log.d("Parsed result", splitOutput[0]);
                        }
                    }
                    catch (Exception e)
                    {

                    }
                }

                // Fill up the listview by the variable 'data'
                mlistview = (ListView) root.findViewById(R.id.listview);
                mAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, data);
                mlistview.setAdapter(mAdapter);
                mlistview.setTextFilterEnabled(true);
                //mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        // Enable listview to be clickable and do the search after users click on it
        mlistview.setClickable(true);
        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3)
            {
                Object recipe_name = mlistview.getItemAtPosition(position);
                String str=(String)recipe_name;//As you are using Default String Adapter
                Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
                mlistview.setVisibility(View.GONE);
                rvRecipes.setVisibility(View.VISIBLE);
                savedQuery = recipe_name.toString();
                Log.d("Test", "Running DBQuery");
                try
                {
                    loadCounter = 0;
                    output = new DBQuery().execute(Integer.toString(loadCounter) + "#" + "Recipe" + "#" + recipe_name).get();
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

    // The method that shows wheel animation for the duration of time
    public void showWheel()
    {
        int wheelDurationInMilliSeconds = 3000;
        RelativeLayout wheel = (RelativeLayout) root.findViewById(R.id.loadingPanel);
        wheel.bringToFront();
        wheel.setVisibility(View.INVISIBLE);
        CountDownTimer wheelCountDown;
        wheelCountDown = new CountDownTimer(wheelDurationInMilliSeconds, 1000)
        {
            @Override
            public void onTick(long l)
            {
                wheel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish()
            {
                wheel.setVisibility(View.INVISIBLE);
            }
        };
        wheel.setVisibility(View.VISIBLE);
        wheelCountDown.start();
    }
}