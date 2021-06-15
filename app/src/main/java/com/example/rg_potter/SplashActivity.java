package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.rg_potter.data.Global;
import com.example.rg_potter.entity.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    ProgressBar pb;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("SplashActivity", "Activity called");

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_setup);

        pb = findViewById(R.id.progressBar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Global.characters == null){

                    File file = new File(getFilesDir(), Global.LOCAL_JSON);

                    if(file.exists()){

                        Global.loadCharactersJson(SplashActivity.this);
                    }else{

                        Connection();
                    }
                }

                Global.user = new User(0, SplashActivity.this);

                done();
            }
        },500);


    }

    void Connection(){
        new ConnectionCheck(new MyInterface() {
            @Override
            public void myMethod(boolean result) {
                if(result){
                    new CharactersSetup().execute(); // Download
                }else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                    builder.setTitle(getResources().getString(R.string.setup_net_title));
                    builder.setMessage(getString(R.string.setup_net_subtitle));
                    builder.setPositiveButton(getString(R.string.setup_net_retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            Connection();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.exit), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            SplashActivity.this.finishAffinity();
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        }).execute();
    }

    protected void done(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },500);
    }

    protected interface MyInterface {
        public void myMethod(boolean result);
    }

    class ConnectionCheck extends AsyncTask<Void, Void, Boolean> {

        private MyInterface mListener;

        public ConnectionCheck(MyInterface mListener){
            this.mListener  = mListener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            Boolean ConnectionStatus = isConnectionAvailable();

            Log.d("Connection", "" + ConnectionStatus);

            return ConnectionStatus;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (mListener != null)
                mListener.myMethod(result);
        }

        public boolean isConnectionAvailable() {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            return (nInfo != null && nInfo.isAvailable() && nInfo.isConnected());
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

            downloadJson();

            publishProgress(50);

            Global.loadCharactersJson(SplashActivity.this);

            publishProgress(100);

            return null;
        }

        private void downloadJson(){
            if(!Global.SAFE_INSTALL){

                Log.d("Error", "Stopped attempted to install again, probably a repercussion error");
                SplashActivity.this.finishAffinity();
                return;
            }

            Global.SAFE_INSTALL = false;

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {

                URL url = null;
                url = new URL(Global.API_URL + Global.CHARACTERS_ENDPOINT);

                connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream stream = connection.getInputStream();

                String jsonString = Global.convertStreamToString(stream);

                stream.close();

                writeToFile(jsonString, SplashActivity.this);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
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

        @Override
        protected void onProgressUpdate(Integer... progress) {
            pb.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            done();
        }
    }

}