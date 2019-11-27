package CS561.recipebox.Instruction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import CS561.recipebox.R;

public class InstructionsFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private View root;

    public RecyclerView recyclerView;
    Bundle extras;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_directions, container, false);
        Context context = this.getContext();
        String[] instructions = {};

        if (extras != null)
        {
            String s = (String)extras.get("Info");
            instructions = s.split("^");
            recyclerView = (RecyclerView) root.findViewById(R.id.instructionsList);
            ArrayList<InstructionItem> instructionList = InstructionItem.createInstructionList(instructions);
            InstructionAdapter adapter = new InstructionAdapter(instructionList,context,this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }


        return root;
    }


    public InstructionsFragment(Bundle e)
    {
        extras = e;
    }

    public static InstructionsFragment newInstance(int index, Bundle e)
    {
        InstructionsFragment fragment = new InstructionsFragment(e);
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
