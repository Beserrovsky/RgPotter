package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
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
import java.util.Random;
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
    }

    private void loadUser (){

        Global.user.House = "Hufflepuff";
        Global.user.save(this);


        Character.House house = new Character.House(Global.user.House, this);

        Random generator = new Random(Global.user.getId());
        int num = (int) Math.round(generator.nextDouble() * (99999999));

        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");

        ((TextView) findViewById(R.id.txtnum2)).setText(String.valueOf(num));
        ((TextView) findViewById(R.id.txtsexo2)).setText(Global.user.getGender(this));
        ((TextView) findViewById(R.id.txtname2)).setText(Global.user.Name);
        ((TextView) findViewById(R.id.txtbirthday2)).setText(DateFor.format(Global.user.Birth.getTime()));
        ((TextView) findViewById(R.id.txtpatrono2)).setText(Global.user.Patronus);
        ((TextView) findViewById(R.id.txthouse2)).setText(house.Name);

        // TODO: MAKE SEARCH AND BIND IT

        Character character = Stream.of(Global.characters)
                .filter(c -> {
                    Log.d("Gender", c.gender==null? "" : c.gender);

                    boolean sameGender = c.gender != null? (c.gender.equalsIgnoreCase("male") && Global.user.getGender_Id(this).equalsIgnoreCase("m")) || (c.gender.equalsIgnoreCase("female") && Global.user.getGender_Id(this).equalsIgnoreCase("f")): false;

                    return (new Character.House(c.house, this).house_id).equalsIgnoreCase(Global.user.House) && sameGender;
                })
                .findFirst()
                .orElse(null);

        if(character==null){ // Well, better lie than nothing...
            int randomId = (int) Math.round(generator.nextDouble() * Global.characters.length);

            character = Stream.of(Global.characters)
                    .filter(c -> { return c.id == randomId;})
                    .findFirst()
                    .orElse(null);
        }

        String search = character!=null? character.name + ", que em hogwarts era da: " + new Character.House(character.house, this).Name : getString(R.string.error);

       ((TextView) findViewById(R.id.txtcharacter2)).setText(search);

       findViewById(R.id.frame).setBackgroundColor(this.getColor(house.Color));

    }

    public void Edit(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void Curiosity (View view){
        Intent intent = new Intent(this, CuriosityActivity.class);
        startActivity(intent);
    }

}