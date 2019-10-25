package CS561.recipebox;

import java.util.ArrayList;
import java.util.List;

// Struct of Recipe
public class Recipe {
    private String rName;
    private String rInfo;

    public Recipe(String name, String info) {
        rName = name;
        rInfo = info;
    }

    public String getName() {
        return rName;
    }

    public String getInfo() {
        return rInfo;
    }

    // not necessary
    private static int lastRecipeId = 0;

    // Use class Recipe to create an list of information
    public static ArrayList<Recipe> createRecipesList(int numRecipes, List<String[]> parsedOutput) {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        // 0 : ID
        // 1 : Ingredient
        // 2 : Instruction
        // 3 : Picture Link
        // 4 : Titile
        for (int i = 0; i <= numRecipes; i++) {
            recipes.add(new Recipe(parsedOutput.get(4)[i] , parsedOutput.get(1)[i]));
        }

        return recipes;
    }
}