package CS561.recipebox;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class InstructionItem
{
    private String text;

    public InstructionItem(String s)
    {
        text = s;
    }

    public String getString()
    {
        return text;
    }

    public static ArrayList<InstructionItem> createInstructionList(String[] Instructions)
    {
        ArrayList<InstructionItem> list = new ArrayList<InstructionItem>();

        for (int i = 0; i < Instructions.length; i++)
        {
            list.add(new InstructionItem(Instructions[i]));
        }
        return list;
    }
}
