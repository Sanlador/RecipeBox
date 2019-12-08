package CS561.recipebox.Instruction;

import java.util.ArrayList;

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
            if (i == 0)
                list.add(new InstructionItem(Instructions[i]));
            else
                list.add(new InstructionItem(Instructions[i].substring(2,Instructions[i].length())));
        }
        return list;
    }
}
