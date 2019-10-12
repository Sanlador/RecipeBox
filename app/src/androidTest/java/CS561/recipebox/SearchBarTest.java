package CS561.recipebox;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RunWith(AndroidJUnit4.class)
public class SearchBarTest
{


    public String queryDB(String search)
    {
        if (search.length() < 1)
            return null;
        Log.d("Function","Launching Query");
        List<String> output = new ArrayList<String>();

        String host = "recipebox01.database.windows.net";
        String db = "RecipeDB";
        String user = "recipeOSU";
        String password = "recipe32!";
        String url = "jdbc:sqlserver://recipebox01.database.windows.net:1433;database=RecipeDB;user=recipeOSU@recipebox01;password=recipe32!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Connection connection = null;

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            DriverManager.getDriver(url);
            connection = DriverManager.getConnection(url);
            //String schema = connection.getSchema();
            //System.out.println("Successful connection - Schema: " + schema);
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM [dbo].[Test]  WHERE NAME = " + search;
            Log.d("Query", selectSql);
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSql))
            {
                while (resultSet.next())
                {
                    Log.d("Output", resultSet.getString(1) + " "
                            + resultSet.getString(2));
                }
                connection.close();
            }
        }
        catch (Exception e) {
            Log.d("Exception", "Connection failed");
            Log.e("Exception:", e.toString());
        }
        //temporary output. Will change once DB connection is implemented.
        return search;
        //return output;
    }

    @Test
    public void testQueryOutput() throws Exception
    {
       for (int i = 0; i < 1000; i++)
       {
           Random rand = new Random();
           String alphabet = "1234567890abcdefghijklmnopqrstuvwxyz";
           int len  = rand.nextInt(50);
           String input = "";
           for (int j = 0; j < len; j++)
           {
               input += alphabet.charAt(rand.nextInt(alphabet.length()));
           }
           assert( queryDB(input) == "SELECT * FROM [dbo].[Test]  WHERE NAME = " + input);
       }
       assert(queryDB("") == null);
    }
}
