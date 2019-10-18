package CS561.recipebox;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBQuery extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... params) {
        {
            if (params[0].length() < 1) {
                return "";
            }
            Log.d("Function", "Launching Query");
            List<String> output = new ArrayList<String>();

            String host = "recipebox01.database.windows.net";
            String db = "RecipeDB";
            String user = "recipeOSU";
            String password = "recipe32!";
            String url = "jdbc:jtds:sqlserver://recipebox01.database.windows.net:1433;database=RecipeDB;user=recipeOSU@recipebox01;password=recipe32!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            Connection connection = null;

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(url);

                String selectSql = "SELECT * FROM sys.databases";
                Log.d("Query", selectSql);
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(selectSql)) {
                    while (resultSet.next()) {
                        Log.d("Output", resultSet.getString(1) + " "
                                + resultSet.getString(2));
                    }
                    connection.close();
                }
            } catch (Exception e) {
                Log.d("Exception", "Connection failed");
                Log.e("Exception:", e.toString());
            }
            //temporary output. Will change once DB connection is implemented.
            return params[0];
            //return output;
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
