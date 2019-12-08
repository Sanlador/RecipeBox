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
    private String rTotalTime;
    private String rCalories;
    private String rFat;
    private String rCarbs;
    private String rProteins;
    private String rCholesterol;
    private String rSodium;
    private String rSugar;
    private String rPrepTime;
    private String rCookTime;


    public Recipe(String name,
                  String ingredients,
                  String info,
                  String pictureLink,
                  String catagories,
                  String serving,
                  String totalTime,
                  String calories,
                  String fat,
                  String carbs,
                  String proteins,
                  String cholesterol,
                  String sodium,
                  String sugar,
                  String prepTime,
                  String cookTime)
    {
        rName = name;
        rInfo = info;
        rIngredients = ingredients;
        rPictureLink = pictureLink;
        rCatagrories = catagories;
        rServing = serving;
        rTotalTime = totalTime;
        rCalories = calories;
        rFat = fat;
        rCarbs = carbs;
        rProteins = proteins;
        rCholesterol = cholesterol;
        rSodium = sodium;
        rSugar = sugar;
        rPrepTime = prepTime;
        rCookTime = cookTime;
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
        return rCookTime;
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

    public String getSugar()
    {
        return rSugar;
    }

    public String getTotalTime()
    {
        return rTotalTime;
    }

    public String getPrepTime()
    {
        return rPrepTime;
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
                recipes.add(new Recipe( parsedOutput.get(i)[1],     // 3 : Name
                        parsedOutput.get(i)[4],     // 4 : Ingredients
                        parsedOutput.get(i)[5],     // 5 : Instructions
                        parsedOutput.get(i)[0],     // 0 : Picture Link
                        parsedOutput.get(i)[2],     // 1 : Catagories
                        parsedOutput.get(i)[3],     // 2 : Servings
                        parsedOutput.get(i)[8],     // 6 : total Time
                        parsedOutput.get(i)[9],     // 7 : Calories
                        parsedOutput.get(i)[10],     // 8 : Fat
                        parsedOutput.get(i)[11],     // 9 : Carbs
                        parsedOutput.get(i)[12],    // 10 : Proteins
                        parsedOutput.get(i)[13],    // 11 : Cholesterol
                        parsedOutput.get(i)[14],   // 12 : Sodium
                        parsedOutput.get(i)[15],    //sugar
                        parsedOutput.get(i)[6],     //prep time
                        parsedOutput.get(i)[7]      //cook time
                ));
            }
            else
                recipes.add(new Recipe( parsedOutput.get(i)[1],     // 3 : Name
                        parsedOutput.get(i)[4],     // 4 : Ingredients
                        parsedOutput.get(i)[5],     // 5 : Instructions
                        parsedOutput.get(i)[0],     // 0 : Picture Link
                        parsedOutput.get(i)[2],     // 1 : Catagories
                        parsedOutput.get(i)[3],     // 2 : Servings
                        parsedOutput.get(i)[8],     // 6 : total Time
                        parsedOutput.get(i)[9],     // 7 : Calories
                        parsedOutput.get(i)[10],     // 8 : Fat
                        parsedOutput.get(i)[11],     // 9 : Carbs
                        parsedOutput.get(i)[12],    // 10 : Proteins
                        parsedOutput.get(i)[13],    // 11 : Cholesterol
                        parsedOutput.get(i)[14],   // 12 : Sodium
                        parsedOutput.get(i)[15],    //sugar
                        parsedOutput.get(i)[6],     //prep time
                        parsedOutput.get(i)[7]      //cook time
                ));
        }
        return recipes;
    }
}
