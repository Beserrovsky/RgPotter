package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

public class SetupActivity extends AppCompatActivity {

    ProgressBar pb;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_setup);

        pb = findViewById(R.id.progressBar);

        if(!Global.SAFE_INSTALL){

            Log.d("Error", "Stopped attempted to install again, probably a repercussion error");

            if(!isInternetAvailable()){
                AlertDialog.Builder builder = new AlertDialog.Builder(SetupActivity.this);
                builder.setTitle(getResources().getString(R.string.setup_net_title));
                builder.setMessage(getString(R.string.setup_net_subtitle));
                builder.setPositiveButton(getString(R.string.setup_net_retry), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Global.SAFE_INSTALL = true;
                        new CharactersSetup().execute();
                    }
                });
                builder.setNegativeButton(getString(R.string.exit), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        SetupActivity.this.finishAffinity();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }

        } else {
            new CharactersSetup().execute();
        }
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName(Global.API_URL);
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }


    class CharactersSetup extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setProgress(0);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Global.SAFE_INSTALL = false;

            File file = new File(getFilesDir(), Global.LOCAL_JSON);

            Gson gson = new Gson();

            if(!file.exists()){ // Check if file is not already installed

                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try { // Download json file from API

                    URL url = new URL(Global.API_URL + Global.CHARACTERS_ENDPOINT);

                    connection = (HttpURLConnection) url.openConnection();

                    connection.connect();

                    publishProgress(15);

                    InputStream stream = connection.getInputStream();

                    publishProgress(25);

                    String jsonString = convertStreamToString(stream);

                    stream.close();

                    writeToFile(jsonString, SetupActivity.this);

                    Thread.sleep(3000);

                    return null;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
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

            }

            publishProgress(50);

            try { // Read from local file and bind to Global.characters

                file = new File(getFilesDir(), Global.LOCAL_JSON);

                FileInputStream fin = new FileInputStream(file);
                String fileJson = convertStreamToString(fin);

                Log.d("JSON loaded: ", fileJson);

                fin.close();

                Global.characters = gson.fromJson(fileJson, Character[].class);

                publishProgress(100);
                return null;

            }catch (Exception e ) {
                e.printStackTrace();
            }

            return null;
        }

        private void writeToFile(String data,Context context) {
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(Global.LOCAL_JSON, Context.MODE_PRIVATE));
                outputStreamWriter.write(data);
                outputStreamWriter.close();
            }
            catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }

        private String convertStreamToString(InputStream is) throws Exception {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            return sb.toString();
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