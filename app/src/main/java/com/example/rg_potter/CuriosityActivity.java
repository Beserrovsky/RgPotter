package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rg_potter.data.Global;
import com.example.rg_potter.entity.Character;

import java.util.Random;
import java.util.stream.Stream;

public class CuriosityActivity extends AppCompatActivity {

    int index = 0;

    ImageButton btnAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("CuriosityActivity", "Activity called");

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
                this.random();
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
                    return c.house_id.equalsIgnoreCase(Global.user.getHouseId());
                }).count();

        Random r = new Random();

        Character character = Stream.of(Global.characters)
                .filter(c -> {
                    return c.house_id.equalsIgnoreCase(Global.user.getHouseId());
                })
                .skip(r.nextInt((int)count))
                .findAny()
                .orElse(null);

        search = genString(character, false);

        ((TextView) findViewById(R.id.txtResponse)).setText(search);

    }

    private void random(){
        btnAgain.setVisibility(ImageButton.VISIBLE);
        // QUESTION

        ((TextView) findViewById(R.id.txtQuestion)).setText(getString(R.string.qst_random));

        // ANSWER

        String search = "";

        Random r = new Random();

        do {
            Character character = Stream.of(Global.characters)
                    .skip(r.nextInt(Global.characters.length))
                    .findAny()
                    .orElse(null);

            search = genString(character, false);

        }while(search.equals(""));

        ((TextView) findViewById(R.id.txtResponse)).setText(search.equals("")? getString(R.string.error) : search);
    }

    private String genString(Character c, boolean patronus){
        String response = "";

        if(c!=null? (c.name!=null && c.house_id !=null) : false){
            response += "Encontrei " + this.getString(c.Gender.PronounResource) + " " + c.name + "\n";
            response += c.House.NameResource != R.string.house_none? "Que participou da " + this.getString(c.House.NameResource) + "\n" : "Que " + this.getString(R.string.house_none_long).toLowerCase() + "\n";
            response += "Era do sexo " + this.getString(c.Gender.NameResource)+ "\n";
            response += c.patronus !=null && c.patronus != ""? "E tinha o patrono " + c.patronus + "\n" : "";
        }else{

            if(patronus) response += this.getString(R.string.no_patronus);
            else response += this.getString(R.string.no_result);
        }

        return response;
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

            search = genString(character, true);
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

                    boolean sameGender = c.Gender.NameResource == Global.user.getGender().NameResource;

                    return c.house_id.equalsIgnoreCase(Global.user.getHouseId()) && sameGender;
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

        String search = genString(character, false);

        ((TextView) findViewById(R.id.txtResponse)).setText(search);
    }
}