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

public class VisualizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_visualization);
        start();
    }

    private void start () {
        loadUser();
        loadTest();
    }

    private void loadUser (){
        // TODO: BIND TO UI Global.user STUFF
    }

    private void loadTest () {

        int randomId = (int) Math.round(Math.random() * Global.characters.length);

        Character c = Stream.of(Global.characters)
                .filter(character -> { return character.id == randomId;})
                .findFirst()
                .orElse(null);

        ((TextView) this.findViewById(R.id.teste)).setText(c!=null? "Random character is - Name: " + c.name + ", Hogwarts House: " + new Character.House(c.house, this).Name : getString(R.string.error));
    }

}