package CS561.recipebox.Recipe;

import java.util.ArrayList;
import java.util.List;

// Struct of Recipe
public class Recipe
{
    private String rName;
    private String rInfo;
    private String rIngredients;
    private String rPictureLink;
    private String rCatagrories;
    private String rServing;
    private String rCooktime;
    private String rCalories;
    private String rFat;
    private String rCarbs;
    private String rProteins;
    private String rCholesterol;
    private String rSodium;

    public Recipe(String name,
                  String ingredients,
                  String info,
                  String pictureLink,
                  String catagories,
                  String serving,
                  String cooktime,
                  String calories,
                  String fat,
                  String carbs,
                  String proteins,
                  String cholesterol,
                  String sodium)
    {
        rName = name;
        rInfo = info;
        rIngredients = ingredients;
        rPictureLink = pictureLink;
        rCatagrories = catagories;
        rServing = serving;
        rCooktime = cooktime;
        rCalories = calories;
        rFat = fat;
        rCarbs = carbs;
        rProteins = proteins;
        rCholesterol = cholesterol;
        rSodium = sodium;
    }

    public String getName()
    {
        return rName;
    }

    public String getInfo()
    {
        return rInfo;
    }

    public String getIngredients()
    {
        return rIngredients;
    }

    public String getrPictureLink()
    {
        return rPictureLink;
    }

    public String getCatagories()
    {
        return rCatagrories;
    }

    public String getServing()
    {
        return rServing;
    }

    public String getCooktime()
    {
        return rCooktime;
    }

    public String getCalories()
    {
        return rCalories;
    }

    public String getFat()
    {
        return rFat;
    }

    public String getCarbs()
    {
        return rCarbs;
    }

    public String getProteins()
    {
        return rProteins;
    }
    public String getCholesterol()
    {
        return rCholesterol;
    }

    public String getSodium()
    {
        return rSodium;
    }

    // not necessary
    private static int lastRecipeId = 0;

    // Use class Recipe to create an list of information
    public static ArrayList<Recipe> createRecipesList(int numRecipes, List<String[]> parsedOutput)
    {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        // recipes is an array contains parsed category strings from query database
        // 0 : Picture Link
        // 1 : Catagories
        // 2 : Servings
        // 3 : Name
        // 4 : Ingredients
        // 5 : Instructions
        // 6 : Cook Time
        // 7 : Calories
        // 8 : Fat
        // 9 : Carbs
        // 10 : Proteins
        // 11 : Cholesterol
        // 12 : Sodium
        // 13 : Sugar(Imcoming soon!!
        for (int i = 0; i <= numRecipes; i++)
        {
            if (i == numRecipes)
            {
                recipes.add(new Recipe( parsedOutput.get(i)[3],     // 3 : Name
                        parsedOutput.get(i)[4],     // 4 : Ingredients
                        parsedOutput.get(i)[5],     // 5 : Instructions
                        parsedOutput.get(i)[0],     // 0 : Picture Link
                        parsedOutput.get(i)[1],     // 1 : Catagories
                        parsedOutput.get(i)[2],     // 2 : Servings
                        parsedOutput.get(i)[6],     // 6 : Cook Time
                        parsedOutput.get(i)[7],     // 7 : Calories
                        parsedOutput.get(i)[8],     // 8 : Fat
                        parsedOutput.get(i)[9],     // 9 : Carbs
                        parsedOutput.get(i)[10],    // 10 : Proteins
                        parsedOutput.get(i)[11],    // 11 : Cholesterol
                        parsedOutput.get(i)[12]    // 12 : Sodium
                ));
            }
            else
                recipes.add(new Recipe( parsedOutput.get(i)[3],     // 3 : Name
                        parsedOutput.get(i)[4],     // 4 : Ingredients
                        parsedOutput.get(i)[5],     // 5 : Instructions
                        parsedOutput.get(i)[0],     // 0 : Picture Link
                        parsedOutput.get(i)[1],     // 1 : Catagories
                        parsedOutput.get(i)[2],     // 2 : Servings
                        parsedOutput.get(i)[6],     // 6 : Cook Time
                        parsedOutput.get(i)[7],     // 7 : Calories
                        parsedOutput.get(i)[8],     // 8 : Fat
                        parsedOutput.get(i)[9],     // 9 : Carbs
                        parsedOutput.get(i)[10],    // 10 : Proteins
                        parsedOutput.get(i)[11],    // 11 : Cholesterol
                        parsedOutput.get(i)[12]    // 12 : Sodium
                ));
        }
        return recipes;
    }
}
