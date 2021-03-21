package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if(Global.characters == null){

            File file = new File(getFilesDir(), Global.LOCAL_JSON);

            if(file.exists()){

                SetupActivity.loadCharactersJson(this);
            }else{

                Intent intent = new Intent(this, SetupActivity.class);
                startActivity(intent);
                finish();
                return;
            }

        }

        setContentView(R.layout.activity_main);
        start();

    }

    private void start() {
        Global.users.add(new User(0, this));

        loadUser(Global.users.get(Global.user_index));

        loadTest();
    }
  
     private void start() {
        btnregister = (Button) findViewById(R.id.btnCad);

        btnmore= (Button) findViewById(R.id.btnMore);

        btnvisu = (Button) findViewById(R.id.btnView);

        btncurio = (Button) findViewById(R.id.btnCuriosity);
    }

    public void Regi (View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void Visualization (View view){
        Intent intent = new Intent(this, activity_visualization.class);
        startActivity(intent);
    }

    public void Curiosity (View view){
        Intent intent = new Intent(this, CuriosityActivity.class);
        startActivity(intent);
    }

    public void More (View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://harrypotter.fandom.com/pt-br/wiki/P%C3%A1gina_Principal"));
        startActivity(intent);
    }

    private void loadUser(User u){
        u.log(this);
        // TODO: BIND TO UI USER STUFF
    }

    private void loadTest(){

        int randomId = (int) Math.round(Math.random() * Global.characters.length);

        Optional<Character> matchingObject = Stream.of(Global.characters).filter(c-> {return c.id == randomId;}).findFirst();

        Character c =  matchingObject.get();

        ((TextView) this.findViewById(R.id.teste)).setText("Random character is - Name: " + c.name + ", Hogwarts House: " + new Character.House(c.house , this).Name);
    }

}