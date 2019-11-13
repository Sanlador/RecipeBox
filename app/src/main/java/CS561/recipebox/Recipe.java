package CS561.recipebox;

import java.util.ArrayList;
import java.util.List;

// Struct of Recipe
public class Recipe {
    private String rName;
    private String rInfo;
    private String rIngredients;
    private String rPictureLink;

    public Recipe(String name, String ingredients, String info, String pictureLink) {
        rName = name;
        rInfo = info;
        rIngredients = ingredients;
        rPictureLink = pictureLink;
    }

    public String getName() {
        return rName;
    }

    public String getInfo() {
        return rInfo;
    }

    public String getIngredients()
    {
        return rIngredients;
    }

    public String getrPictureLink() { return rPictureLink; }

    // not necessary
    private static int lastRecipeId = 0;

    // Use class Recipe to create an list of information
    public static ArrayList<Recipe> createRecipesList(int numRecipes, List<String[]> parsedOutput) {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        // recipes is an array contains parsed category strings from query database
        // 0 : ID
        // 1 : Ingredient
        // 2 : Instruction
        // 3 : Picture Link
        // 4 : Title
        for (int i = 0; i <= numRecipes; i++)
        {
            if (i == numRecipes)
            {
                recipes.add(new Recipe(parsedOutput.get(i)[3], parsedOutput.get(i)[4], parsedOutput.get(i)[5], parsedOutput.get(i)[0]));
            }
            else
                recipes.add(new Recipe(parsedOutput.get(i)[3], parsedOutput.get(i)[4], parsedOutput.get(i)[5], parsedOutput.get(i)[0]));

        }

        return recipes;
    }
}