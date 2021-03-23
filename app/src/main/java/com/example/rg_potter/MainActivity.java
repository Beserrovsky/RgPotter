package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("Debug", "Main activity called");

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if(Global.characters == null){

            File file = new File(getFilesDir(), Global.LOCAL_JSON);

            if(file.exists()){

                Global.loadCharactersJson(this);
            }else{

                Intent intent = new Intent(MainActivity.this, SetupActivity.class);
                startActivity(intent);
                finish();
            }
        }

        Global.user = new User(0, this);
        setContentView(R.layout.activity_main);
    }

    public void Regi (View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void Visualization (View view){
        Intent intent = new Intent(this, VisualizationActivity.class);
        startActivity(intent);
    }

    public void More (View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://harrypotter.fandom.com/pt-br/wiki/P%C3%A1gina_Principal"));
        startActivity(intent);
    }

}