package CS561.recipebox.Diet;

import android.provider.BaseColumns;

public final class DietContract {
    private DietContract()
    {

    }

    public static class Diet implements BaseColumns
    {
        public static final String TABLE_NAME = "diet";

        public static final String COLUMN_NAME_RECIPE_BREAKFAST = "breakfast_recipe";
        public static final String COLUMN_NAME_INFO_BREAKFAST = "breakfast_info";
        public static final String COLUMN_NAME_INGREDIENTS_BREAKFAST = "breakfast_ingredients";
        public static final String COLUMN_NAME_URL_BREAKFAST = "breakfast_url";
        public static final String COLUMN_NAME_CATAGORIES_BREAKFAST = "breakfast_catagories";
        public static final String COLUMN_NAME_SERVING_BREAKFAST = "breakfast_serving";
        public static final String COLUMN_NAME_COOKTIME_BREAKFAST = "breakfast_cooktime";
        public static final String COLUMN_NAME_CALORIES_BREAKFAST = "breakfast_calories";
        public static final String COLUMN_NAME_FAT_BREAKFAST = "breakfast_fat";
        public static final String COLUMN_NAME_CARBS_BREAKFAST = "breakfast_carbs";
        public static final String COLUMN_NAME_STRING_PROTEIN_BREAKFAST = "breakfast_protein";
        public static final String COLUMN_NAME_CHOLESTEROL_BREAKFAST = "breakfast_cholesterol";
        public static final String COLUMN_NAME_SODIUM_BREAKFAST = "breakfast_sodium";

        public static final String COLUMN_NAME_RECIPE_LUNCH = "lunch_recipe";
        public static final String COLUMN_NAME_INFO_LUNCH = "lunch_info";
        public static final String COLUMN_NAME_INGREDIENTS_LUNCH = "lunch_ingredients";
        public static final String COLUMN_NAME_URL_LUNCH = "lunch_url";
        public static final String COLUMN_NAME_CATAGORIES_LUNCH = "lunch_catagories";
        public static final String COLUMN_NAME_SERVING_LUNCH = "lunch_serving";
        public static final String COLUMN_NAME_COOKTIME_LUNCH = "lunch_cooktime";
        public static final String COLUMN_NAME_CALORIES_LUNCH = "lunch_calories";
        public static final String COLUMN_NAME_FAT_LUNCH = "lunch_fat";
        public static final String COLUMN_NAME_CARBS_LUNCH = "lunch_carbs";
        public static final String COLUMN_NAME_STRING_PROTEIN_LUNCH = "lunch_protein";
        public static final String COLUMN_NAME_CHOLESTEROL_LUNCH = "lunch_cholesterol";
        public static final String COLUMN_NAME_SODIUM_LUNCH = "lunch_sodium";

        public static final String COLUMN_NAME_RECIPE_DINNER = "dinner_recipe";
        public static final String COLUMN_NAME_INFO_DINNER = "dinner_info";
        public static final String COLUMN_NAME_INGREDIENTS_DINNER = "dinner_ingredients";
        public static final String COLUMN_NAME_URL_DINNER = "dinner_url";
        public static final String COLUMN_NAME_CATAGORIES_DINNER = "dinner_catagories";
        public static final String COLUMN_NAME_SERVING_DINNER = "dinner_serving";
        public static final String COLUMN_NAME_COOKTIME_DINNER = "dinner_cooktime";
        public static final String COLUMN_NAME_CALORIES_DINNER = "dinner_calories";
        public static final String COLUMN_NAME_FAT_DINNER = "dinner_fat";
        public static final String COLUMN_NAME_CARBS_DINNER = "dinner_carbs";
        public static final String COLUMN_NAME_STRING_PROTEIN_DINNER = "dinner_protein";
        public static final String COLUMN_NAME_CHOLESTEROL_DINNER = "dinner_cholesterol";
        public static final String COLUMN_NAME_SODIUM_DINNER = "dinner_sodium";
    }
}
