package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;
import java.util.stream.Stream;

public class CuriosityActivity extends AppCompatActivity {

    int index = 0;

    ImageButton btnAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Debug", "Curiosity activity called");

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_curiosity);

        btnAgain = (ImageButton) findViewById(R.id.btnAgain);

        Generate();
    }

    public void CycleUp(View view){
        index++;
        Generate();
    }

    public void CycleDown(View view){
        index--;
        Generate();
    }

    public void Again(View view){
        Generate();
    }

    private void Generate(){

        btnAgain.setVisibility(ImageButton.INVISIBLE);

        if(index > 3 ) index = 0;
        if(index < 0) index = 3;

        switch (index){
            case 0:
                this.lookLike();
                break;
            case 1:
                this.sameHouse();
                break;
            case 2:
                this.birthday();
                break;
            case 3:
                this.patronus();
                break;
        }
    }

    private void sameHouse(){
        btnAgain.setVisibility(ImageButton.VISIBLE);
        // QUESTION

        ((TextView) findViewById(R.id.txtQuestion)).setText(getString(R.string.qst_sameHouse));

        // ANSWER

        String search = "";

        long count = Stream.of(Global.characters)
                .filter(c -> {
                    return c.house!=null? c.house.equalsIgnoreCase(Global.user.House.house_id): false;
                }).count();

        Random r = new Random();

        Character character = Stream.of(Global.characters)
                .filter(c -> {
                    return c.house!=null? c.house.equalsIgnoreCase(Global.user.House.house_id): false;
                })
                .skip(r.nextInt((int)count))
                .findAny()
                .orElse(null);

        search = character !=null? "Achei o " + character.name +" que era da " + new Character.House(character.house, this).Name : getString(R.string.no_result);

        ((TextView) findViewById(R.id.txtResponse)).setText(search);
    }

    private void birthday(){
        // QUESTION

        ((TextView) findViewById(R.id.txtQuestion)).setText(getString(R.string.qst_birthday));

        // ANSWER

        String search = "";

        ((TextView) findViewById(R.id.txtResponse)).setText(search.equals("")? search : getString(R.string.no_result));
    }

    private void patronus(){
        btnAgain.setVisibility(ImageButton.VISIBLE);
        // QUESTION

        ((TextView) findViewById(R.id.txtQuestion)).setText(getString(R.string.qst_patronus));

        // ANSWER

        String search = "";

        long count = Stream.of(Global.characters)
                .filter(c -> {
                    return c.patronus!= null? c.patronus.equalsIgnoreCase(Global.user.Patronus) : false;
                }).count();

        if(count > 0) {
            Random r = new Random();

            Character character = Stream.of(Global.characters)
                    .filter(c -> {
                        return c.patronus!= null? c.patronus.equalsIgnoreCase(Global.user.Patronus) : false;
                    })
                    .skip(r.nextInt((int)count))
                    .findAny()
                    .orElse(null);

            search = character !=null? "Achei o " + character.name +" que era da " + new Character.House(character.house, this).Name : getString(R.string.no_patronus);
        }else{
            search = getString(R.string.no_patronus);
        }
        ((TextView) findViewById(R.id.txtResponse)).setText(search);
    }

    private void lookLike(){
        // QUESTION

        ((TextView) findViewById(R.id.txtQuestion)).setText(getString(R.string.qst_lookLike));

        // ANSWER

        Character character = Stream.of(Global.characters)
                .filter(c -> {

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

        ((TextView) findViewById(R.id.txtResponse)).setText(search);
    }
}