package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SetupActivity extends AppCompatActivity {

    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_setup);

        pb = findViewById(R.id.progressBar);
        new CharactersSetup().execute();
    }

    class CharactersSetup extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setProgress(0);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(Global.SAFE_INSTALL == false){
                Log.d("Error", "Stopped attemped to install again, probably repercusivenesse");
                return null;
            }

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {

                URL url = new URL(Global.API_URL + Global.CHARACTERS_ENDPOINT);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                publishProgress(25);

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }

                publishProgress(50);

                Gson gson = new Gson();

                Global.characters = gson.fromJson(buffer.toString(), Character[].class);

                // TODO: DECIDE!!!! OR BIND TO SQL OR BIND TO MEMORY ALL TIMES THAT IT INITIALIZES

                publishProgress(100);

                Global.SAFE_INSTALL = false;

                return null;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            pb.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(SetupActivity.this, MainActivity.class);
            startActivity(intent);
            SetupActivity.this.finish();
        }
    }
}