package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.stream.Stream;

public class CuriosityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Debug", "Curiosity activity called");

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_curiosity);
    }

    private void lookLike(){
        Character character = Stream.of(Global.characters)
                .filter(c -> {
                    Log.d("Gender", c.gender==null? "" : c.gender);

                    boolean sameGender = c.gender != null? (c.gender.equalsIgnoreCase("male") && Global.user.getGender_Id(this).equalsIgnoreCase("m")) || (c.gender.equalsIgnoreCase("female") && Global.user.getGender_Id(this).equalsIgnoreCase("f")): false;

                    return (new Character.House(c.house, this).house_id).equalsIgnoreCase(Global.user.House.house_id) && sameGender;
                })
                .findFirst()
                .orElse(null);

        if(character==null){ // Well, better lie than nothing...
            Random generator = new Random(Global.user.getId());

            int randomId = (int) Math.round(generator.nextDouble() * Global.characters.length);

            character = Stream.of(Global.characters)
                    .filter(c -> { return c.id == randomId;})
                    .findFirst()
                    .orElse(null);
        }

        String search = character!=null? character.name + ", que em hogwarts era da: " + (new Character.House(character.house, this).Name) : getString(R.string.error);

        ((TextView) findViewById(R.id.txtcharacter)).setText(search);
    }
}