package CS561.recipebox;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String mType;

    public Category(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }

    private static int lastCategoryId = 0;

    public static ArrayList<Category> createCategoryList(String input) {
        ArrayList<Category> category = new ArrayList<Category>();

        String[] split_string = input.split(",");

        for (String s : split_string)
            category.add(new Category(s));


        return category;
    }
}