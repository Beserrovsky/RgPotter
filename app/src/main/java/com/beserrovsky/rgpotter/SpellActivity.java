package com.beserrovsky.rgpotter;

import android.os.Bundle;

import com.beserrovsky.rgpotter.util.SpellFeedbackView;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class SpellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell);
    }

    int size = 50;
    public void ClickSpell (View view){
        size += 10;
        ((SpellFeedbackView) view).Update((int)Math.round(Math.random()*255), size);
    }
}