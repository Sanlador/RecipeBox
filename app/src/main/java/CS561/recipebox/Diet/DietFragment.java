package CS561.recipebox.Diet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import CS561.recipebox.Query.QueryForPantry;
import CS561.recipebox.R;
import CS561.recipebox.Recipe.Recipe;

public class DietFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private View root;
    private Button button;
    private ArrayList<DietItem> dietList;
    private DietAdapter adapter;

    public RecyclerView recyclerView;

    public static DietFragment newInstance(int index) {
        DietFragment fragment = new DietFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_diet, container, false);
        Context context = this.getContext();
        dietList = new ArrayList<DietItem>();
        adapter = new DietAdapter(dietList, context, this);
        recyclerView = (RecyclerView) root.findViewById(R.id.dietList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        button = (Button) root.findViewById(R.id.dietButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                button.setVisibility(View.INVISIBLE);
                //Play loading animation?

                //Acquire diet plan (Will eventually use DP algorithm)
                dietList.add(dummyOutput());
                dietList.add(dummyOutput());
                adapter.notifyDataSetChanged();
            }
        });

        return root;
    }

    private DietItem dummyOutput()
    {
        String output;

        //call query function
        Log.d("Test", "Running DBQuery");
        try
        {
            output = new QueryForPantry().execute("a").get();

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
                if (parsedOutput.get(0).length > 1)
                {
                    List<Recipe> recipes = Recipe.createRecipesList(parsedOutput.size()-1, parsedOutput);
                    List<Recipe[]> inputList = new ArrayList<Recipe[]>();
                    Recipe[] i = {recipes.get(0), recipes.get(1), recipes.get(2)};
                    inputList.add(i);
                    ArrayList<DietItem> diet = DietItem.createDietPlan(inputList);
                    return diet.get(0);
                }
            }
            else
            {
                Log.d("Recommendation", "Failed to make recommendation; No output");
            }
        }
        catch (Exception e)
        {

        }
        return null;
    }
}
