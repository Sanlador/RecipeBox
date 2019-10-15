package com.kuanluntseng.show;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    
    // define widgets as global variables
    TextView textView;
    Button button;
    // storing strings for data
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // binding with id
         button = (Button) findViewById(R.id.button);
         textView = findViewById(R.id.textView);

        //declare a button listener to check whether the button is clicked
        button.setOnClickListener(new View.OnClickListener() {
            
            // event for button clicking
            @Override
            public void onClick(View view) {
                
                // create a thread if botton got clicked and start it
                Thread thread = new Thread(mutiThread);
                thread.start();
            }
        });
    }

    // the purpose of creating threads instead of doing it in 
    // MainActivity() prevents entire function get jammed if 
    // users take a too long time to build up the connection.
    private Runnable mutiThread = new Runnable() {
        @Override
        public void run() {
            try {
                // local database
                URL url = new URL("http://10.0.0.87/GetData.php");
                
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);   // allow output
                connection.setDoInput(true);    // allow input
                connection.setUseCaches(false); // allow no caches
                connection.connect();           // building connection
                
                // respondse from the connection
                int responseCode = connection.getResponseCode();
                
                // if there is no error from response 
                if(responseCode == HttpURLConnection.HTTP_OK) {
                    // get input strings
                    InputStream inputStream = connection.getInputStream();
                    // load the strings
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    // where to put strings
                    String box = "";
                    // where to load strings
                    String line = null;
                    // append strings if there are string it reads
                    while ((line = bufReader.readLine()) != null) {
                        box += line + "\n";
                    }
                    // close the stream
                    inputStream.close();
                    // store it into global vairable
                    result = box;
                }
            } catch(Exception e) {
                    // report if something happened
                    result = e.toString();
            }
            
            // whenever a thread has done then change the text in textview
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(result);
                }
            });
        }
    };
}
