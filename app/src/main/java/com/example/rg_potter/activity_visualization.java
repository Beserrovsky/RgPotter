package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
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

public class activity_visualization extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_visualization);

        if (Global.characters != null) {

            setContentView(R.layout.activity_main);

            start();
        } else {

            Intent intent = new Intent(this, SetupActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void start () {
        Global.users.add(new User(0, this));

        loadUser(Global.users.get(Global.user_index));

        loadTest();
    }

    private void loadUser (User u){
        u.log(this);
        // TODO: BIND TO UI USER STUFF
    }

    private void loadTest () {

        int randomId = (int) Math.round(Math.random() * Global.characters.length);

        Optional<Character> matchingObject = Stream.of(Global.characters).filter(c -> {
            return c.id == randomId;
        }).findFirst();

        Character c = matchingObject.get();

        ((TextView) this.findViewById(R.id.teste)).setText("Random character is - Name: " + c.name + ", Hogwarts House: " + new Character.House(c.house, this).Name);
    }

}