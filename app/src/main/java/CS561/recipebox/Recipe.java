package CS561.recipebox;

import java.util.ArrayList;
import java.util.List;

// Struct of Recipe
public class Recipe {
    private String rName;
    private String rInfo;
    private String rIngredients;

    public Recipe(String name, String ingredients, String info) {
        rName = name;
        rInfo = info;
        rIngredients = ingredients;
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
            recipes.add(new Recipe(parsedOutput.get(i)[4] , parsedOutput.get(i)[1], parsedOutput.get(i)[2]));
        }

        return recipes;
    }
}