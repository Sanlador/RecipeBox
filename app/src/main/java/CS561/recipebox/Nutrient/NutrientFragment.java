package CS561.recipebox.Nutrient;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import CS561.recipebox.R;

public class NutrientFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private View root;

    public RecyclerView recyclerView;
    Bundle extras;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_nutri_direction, container, false);
        Context context = this.getContext();
        String[] nutrient = {};

        if (extras != null)
        {
            String fat = (String)extras.get("Fat");
            String carbs = (String)extras.get("Carbs");
            String proteins = (String)extras.get("Proteins");
            String cholesterol = (String)extras.get("Cholesterol");
            String sodium = (String)extras.get("Sodium");
            String sugar = (String)extras.get("Sugar");

            String concat = "Sugar : " + sugar.substring(0, sugar.length() - 2) + " grams," +
                            "Fat : " + fat + " grams," +
                            "Carbs : " + carbs + " grams," +
                            "Proteins : " + proteins + " grams," +
                            "Cholesterol :" + cholesterol + " milligrams," +
                            "Sodium : " + sodium +" milligrams ";

            Log.d("Nutrient", concat);
            nutrient = concat.split(",");
            recyclerView = (RecyclerView) root.findViewById(R.id.nutrientList);
            ArrayList<NutrientItem> nutrientList = NutrientItem.createNutrientList(nutrient);
            NutrientAdapter adapter = new NutrientAdapter(nutrientList,context,this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }


        return root;
    }


    public NutrientFragment(Bundle e)
    {
        extras = e;
    }

    public static NutrientFragment newInstance(int index, Bundle e)
    {
        NutrientFragment fragment = new NutrientFragment(e);
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
}
