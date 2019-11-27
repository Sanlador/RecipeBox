package CS561.recipebox.Diet;

import android.provider.BaseColumns;

public final class DietContract {
    private DietContract()
    {

    }

    public static class Diet implements BaseColumns
    {
        public static final String TABLE_NAME = "diet";

        public static final String COLUMN_NAME_RECIPE_BREAKFAST = "recipe";
        public static final String COLUMN_NAME_INFO_BREAKFAST = "info";
        public static final String COLUMN_NAME_INGREDIENTS_BREAKFAST = "ingredients";
        public static final String COLUMN_NAME_URL_BREAKFAST = "url";
        public static final String COLUMN_NAME_CATAGORIES_BREAKFAST = "catagories";
        public static final String COLUMN_NAME_SERVING_BREAKFAST = "serving";
        public static final String COLUMN_NAME_COOKTIME_BREAKFAST = "cooktime";
        public static final String COLUMN_NAME_CALORIES_BREAKFAST = "calories";
        public static final String COLUMN_NAME_FAT_BREAKFAST = "fat";
        public static final String COLUMN_NAME_CARBS_BREAKFAST = "carbs";
        public static final String COLUMN_NAME_STRING_PROTEIN_BREAKFAST = "protein";
        public static final String COLUMN_NAME_CHOLESTEROL_BREAKFAST = "cholesterol";
        public static final String COLUMN_NAME_SODIUM_BREAKFAST = "sodium";

        public static final String COLUMN_NAME_RECIPE_LUNCH = "recipe";
        public static final String COLUMN_NAME_INFO_LUNCH = "info";
        public static final String COLUMN_NAME_INGREDIENTS_LUNCH = "ingredients";
        public static final String COLUMN_NAME_URL_LUNCH = "url";
        public static final String COLUMN_NAME_CATAGORIES_LUNCH = "catagories";
        public static final String COLUMN_NAME_SERVING_LUNCH = "serving";
        public static final String COLUMN_NAME_COOKTIME_LUNCH = "cooktime";
        public static final String COLUMN_NAME_CALORIES_LUNCH = "calories";
        public static final String COLUMN_NAME_FAT_LUNCH = "fat";
        public static final String COLUMN_NAME_CARBS_LUNCH = "carbs";
        public static final String COLUMN_NAME_STRING_PROTEIN_LUNCH = "protein";
        public static final String COLUMN_NAME_CHOLESTEROL_LUNCH = "cholesterol";
        public static final String COLUMN_NAME_SODIUM_LUNCH = "sodium";

        public static final String COLUMN_NAME_RECIPE_DINNER = "recipe";
        public static final String COLUMN_NAME_INFO_DINNER = "info";
        public static final String COLUMN_NAME_INGREDIENTS_DINNER = "ingredients";
        public static final String COLUMN_NAME_URL_DINNER = "url";
        public static final String COLUMN_NAME_CATAGORIES_DINNER = "catagories";
        public static final String COLUMN_NAME_SERVING_DINNER = "serving";
        public static final String COLUMN_NAME_COOKTIME_DINNER = "cooktime";
        public static final String COLUMN_NAME_CALORIES_DINNER = "calories";
        public static final String COLUMN_NAME_FAT_DINNER = "fat";
        public static final String COLUMN_NAME_CARBS_DINNER = "carbs";
        public static final String COLUMN_NAME_STRING_PROTEIN_DINNER = "protein";
        public static final String COLUMN_NAME_CHOLESTEROL_DINNER = "cholesterol";
        public static final String COLUMN_NAME_SODIUM_DINNER = "sodium";
    }
}
