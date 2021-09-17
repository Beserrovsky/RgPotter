package com.beserrovsky.rgpotter;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.beserrovsky.rgpotter.util.OnSwipeTouchListener;
import com.beserrovsky.rgpotter.util.SpellFeedbackView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SpellActivity extends AppCompatActivity {

    public final int[] Spells         = { R.string.spell_lumus      , R.string.spell_leviosa      };
    public final int[] SpellDesc      = { R.string.spell_lumus_desc , R.string.spell_leviosa_desc };
    public final int[] ReverseSpells  = { R.string.spell_nox        , 0                           };
    public final int[] ReverseDesc    = { R.string.spell_nox_desc   , 0                           };

    public int index = 0;
    public boolean reverse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell);
        SetSwipesEvents();
        UpdateState();
    }

    public void Next() { Spell((index + 1) % Spells.length);}
    public void Back() { Spell((index == 0 ? Spells.length : index) - 1);}

    public void Spell(int i) {
        if ( !reverse && i < Spells.length ) {
            this.index = i;
            UpdateState();
        }else if(reverse){
            Toast.makeText(this, R.string.spell_locked_swipe_warning, Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdateState() {
        ((TextView) findViewById(R.id.spellNameTextView)).setText(getItemName());
        ((TextView) findViewById(R.id.spellDescTitleTextView)).setText(getItemName());
        ((TextView) findViewById(R.id.spellDescTextView)).setText(getItemDesc());

        SpellFeedbackView feedbackView = findViewById(R.id.spellFeedbackView);
        feedbackView.Update(feedbackView.DEFAULT_AGGRESSIVENESS, feedbackView.DEFAULT_PROGRESS);
    }

    private boolean running;
    public void RunSpell(View v)
    {
        running = true;
        UpdateState();
        if(reverse)
        {
            // Runs Reverse

            reverse = false;
        }
        else {

            // Runs Spell


            // Only IF reverseSpells exists, then next run is gonna be it
            if(ReverseSpells[index]!=0) reverse = true;
        }
        running = false;
        UpdateState();
    }

    private int getItemName() {
        if (running) return R.string.spell_running;
        return reverse? ReverseSpells[index] : Spells[index];
    }
    private int getItemDesc(){
        if (running) return R.string.spell_running_desc;
        return reverse? ReverseDesc[index] : SpellDesc[index];
    }

    @SuppressLint("ClickableViewAccessibility")
    private void SetSwipesEvents(){
        ConstraintLayout constraintLayout = findViewById(R.id.spellContentConstraint);
        constraintLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeRight() {
                ((SpellActivity)ctx).Back();
            }
            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeLeft() {
                ((SpellActivity)ctx).Next();
            }
            public void onSwipeTop() {}
            public void onSwipeBottom() {}
        });
    }
}