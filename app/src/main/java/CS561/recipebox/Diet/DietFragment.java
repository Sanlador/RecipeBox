package CS561.recipebox.Diet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
    private Button startButton, refreshButton;
    private ArrayList<DietItem> dietList;
    private DietAdapter adapter;
    private DietContractHelper contractHelper;

    public RecyclerView recyclerView;

    public static DietFragment newInstance(int index)
    {
        DietFragment fragment = new DietFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_diet, container, false);
        Context context = this.getContext();
        contractHelper = new DietContractHelper(context);
        dietList = contractHelper.readFromDatabase();


        adapter = new DietAdapter(dietList, context, this);
        recyclerView = (RecyclerView) root.findViewById(R.id.dietList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshButton = (Button) root.findViewById(R.id.dietRefresh);

        startButton = (Button) root.findViewById(R.id.dietButton);

        if (dietList.size() > 0)
        {
            startButton.setVisibility(View.INVISIBLE);
            refreshButton.setVisibility(View.VISIBLE);
        }

        startButton.setOnClickListener(new View.OnClickListener()
        {
            double factor = 1;

            @Override
            public void onClick(View view)
            {
                try
                {
                    AlertDialog.Builder factorWeight = new AlertDialog.Builder(context);
                    factorWeight.setTitle("Enter your weight (in pounds)");
                    final EditText inputWeight = new EditText(context);
                    inputWeight.setInputType(InputType.TYPE_CLASS_TEXT);
                    factorWeight.setView(inputWeight);

                    factorWeight.setPositiveButton("Submit", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            try
                            {
                                factor *= Double.parseDouble(inputWeight.getText().toString()) / 170d;
                            }
                            catch (Exception e)
                            {
                                Log.d("Exception", e.toString());
                            }
                        }
                    });

                    factorWeight.show();

                    AlertDialog.Builder factorHeight = new AlertDialog.Builder(context);
                    factorHeight.setTitle("Enter your height (in inches)");
                    final EditText inputHeight = new EditText(context);
                    inputHeight.setInputType(InputType.TYPE_CLASS_TEXT);
                    factorHeight.setView(inputHeight);

                    factorHeight.setPositiveButton("Submit", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            try
                            {
                                factor *= Double.parseDouble(inputHeight.getText().toString()) / 69d;
                            }
                            catch (Exception e)
                            {
                                Log.d("Exception", e.toString());
                            }
                        }
                    });

                    factorHeight.show();

                    AlertDialog.Builder factorActive = new AlertDialog.Builder(context);
                    factorActive.setTitle("How many hours per week do you exercise (be honest)");
                    final EditText inputActive = new EditText(context);
                    inputActive.setInputType(InputType.TYPE_CLASS_TEXT);
                    factorActive.setView(inputActive);

                    factorActive.setPositiveButton("Submit", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            try
                            {
                                factor *= Double.parseDouble(inputHeight.getText().toString()) / 3 * 1.05;
                            }
                            catch (Exception e)
                            {
                                Log.d("Exception", e.toString());
                            }
                        }
                    });

                    factorActive.show();

                    startButton.setVisibility(View.INVISIBLE);
                    refreshButton.setVisibility(View.VISIBLE);
                    new DietDP(context).execute(Double.toString(factor));
                }
                catch (Exception e)
                {
                    Log.d("Exception", e.toString());
                }

                refreshButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        List<DietItem> tempList = contractHelper.readFromDatabase();
                        dietList.clear();
                        for (DietItem d: tempList)
                        {
                            dietList.add(d);
                        }
                        adapter.notifyDataSetChanged();

                    }
                });
            }
        });

        return root;
    }

    private DietItem dummyOutput(String input)
    {
        String output;

        //call query function
        Log.d("Test", "Running DBQuery");
        try
        {
            output = new QueryForPantry().execute(input).get();

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
                    if (16 == parse.length)
                        parsedOutput.add(parse);
                    else
                        Log.d("Recipe", parse[1]);
                }
                if (parsedOutput.size() > 1)
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
            Log.d("Exception", e.toString());
        }
        return null;
    }
}
