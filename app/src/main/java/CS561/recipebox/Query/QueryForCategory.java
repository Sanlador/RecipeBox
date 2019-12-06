package CS561.recipebox.Query;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueryForCategory extends AsyncTask<String, String, String>
{

    private int pageNumber = 10;

    @Override
    protected String doInBackground(String... params)
    {
        {
            /*
            if (params[0].length() < 1) {
                return null;
            }
            */

            Log.d("Function", "Launching Query");
            String output = "";


            String host = "recipebox01.database.windows.net";
            String db = "RecipeDB";
            String user = "recipeOSU";
            String password = "recipe32!";
            String url = "jdbc:jtds:sqlserver://recipebox01.database.windows.net:1433;databaseName=RecipeDB;user=recipeOSU@recipebox01;password=recipe32!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;sendStringParametersAsUnicode=false";
            Connection connection = null;

            try
            {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(url);

                String selectSql = "select * from recipe_categories";

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectSql);

                while (resultSet.next())
                {
                    output += (resultSet.getString(1) + "~~~");
                }
                connection.close();
            }
            catch (Exception e)
            {
                Log.d("Exception", "Connection failed");
                Log.e("Exception:", e.toString());
            }
            //temporary output. Will change once DB connection is implemented.

            if (output.length() > 0)
                output = output.substring(0, output.length() - 3);
            //return params[0];
            Log.d("Test", "Exiting DBQuery");

            return output;
        }
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(String result)
    {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute()
    {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onProgressUpdate(Progress[])
     */
}