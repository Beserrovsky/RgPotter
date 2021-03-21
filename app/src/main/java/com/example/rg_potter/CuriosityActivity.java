package com.example.rg_potter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CuriosityActivity extends AppCompatActivity {

    Button btnmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_curiosity);

        btnmenu = (Button) findViewById(R.id.btnMenu);
    }

    public void Menu (View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}