package CS561.recipebox;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CategoryQuery extends AsyncTask<String, String, String> {

    private int pageNumber = 10;
    private String checkbox = "Recipe";
    private String prefer_category = "";
    private String prefer_ingridient = "";

    @Override
    protected String doInBackground(String... params) {
        {
            /*
            if (params[0].length() < 1) {
                return null;
            }

             */


            Log.d("Function", "Launching Query");
            String output = "";
            String selectSql;

            String host = "recipebox01.database.windows.net";
            String db = "RecipeDB";
            String user = "recipeOSU";
            String password = "recipe32!";
            String url = "jdbc:jtds:sqlserver://recipebox01.database.windows.net:1433;databaseName=RecipeDB;user=recipeOSU@recipebox01;password=recipe32!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;sendStringParametersAsUnicode=false";
            Connection connection = null;

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(url);

                String[] Q = params[0].split("#");

                int loadCounter = Integer.parseInt(Q[0]);
                checkbox = Q[1];
                prefer_category = Q[2];
                prefer_ingridient = Q[3];
                String query = Q[4];



                String[] tok_category = prefer_category.split("```");
                String[] tok_ingedient = prefer_ingridient.split("```");

                String concat_category = "";
                String concat_ingredient = "";


                for (String s : tok_category)
                {
                    //Log.d("Tag", s);
                    concat_category += "Categories like " + "'%" + s + "%' " + "and ";
                }

                for (String s : tok_ingedient)
                {
                    concat_ingredient += "Ingredient like " + "'%" + s + "%'" + "and ";
                }

                if (params[0].length() < 1)
                {
                    selectSql = "select * from Webscrape where " + concat_category;
                    selectSql = selectSql.substring(0, selectSql.length() - 4);
                }

                else
                {
                    concat_category += "Recipe like '%" + query + "%'";
                    selectSql = "select * from Webscrape where " + concat_category;
                }

                Log.d("Query", selectSql);
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(selectSql)) {
                    while (resultSet.next()) {
                        output += (resultSet.getString(1) + "```"
                                + resultSet.getString(2) + "```"
                                + resultSet.getString(3) + "```"
                                + resultSet.getString(4) + "```"
                                + resultSet.getString(5) + "```"
                                + resultSet.getString(6) + "```"
                                + resultSet.getString(7) + "```"
                                + resultSet.getString(8) + "```"
                                + resultSet.getString(9) + "```"
                                + resultSet.getString(10) + "```"
                                + resultSet.getString(11) + "```"
                                + resultSet.getString(12) + "```"
                                + resultSet.getString(13))
                                + "~~~";
                    }
                    connection.close();
                }
            }
            catch (Exception e) {
                Log.d("Exception", "Connection failed");
                Log.e("Exception:", e.toString());
            }
            //temporary output. Will change once DB connection is implemented.

            if (output.length() > 0)
                output = output.substring(0, output.length() - 1);
            //return params[0];
            Log.d("Test", "Exiting DBQuery");
            return output;
        }
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onProgressUpdate(Progress[])
     */
}