package com.beserrovsky.rgpotter;

import com.beserrovsky.rgpotter.spells.LeviosaSpell;
import com.beserrovsky.rgpotter.spells.LumusSpell;
import com.beserrovsky.rgpotter.spells.NoxSpell;
import com.beserrovsky.rgpotter.spells.Spell;
import com.beserrovsky.rgpotter.util.OnSwipeTouchListener;
import com.beserrovsky.rgpotter.util.SpellFeedbackView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SpellActivity extends AppCompatActivity {

    Spell[] Spells        = { new LumusSpell(this), new LeviosaSpell(this) };
    Spell[] ReverseSpells = { new  NoxSpell(this) ,              null          };

    public SpellFeedbackView feedbackView;
    public int index = 0;
    public boolean reverse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell);
        feedbackView = findViewById(R.id.spellFeedbackView);
        SetSwipeEvents();
        UpdateState();
    }

    public void SwitchSpell(int i) {
        if ( !running && !reverse && i < Spells.length ) {
            this.index = i;
            UpdateState();
        }else if(reverse){
            Toast.makeText(this, R.string.spell_locked_swipe_reverse, Toast.LENGTH_SHORT).show();
        }else if(running){
            Toast.makeText(this, R.string.spell_locked_swipe_running, Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdateState() {
        ((TextView) findViewById(R.id.spellNameTextView)).setText(getItemName());
        ((TextView) findViewById(R.id.spellDescTitleTextView)).setText(getItemName());
        ((TextView) findViewById(R.id.spellDescTextView)).setText(getItemDesc());
    }

    private int getItemName() {
        if (running) return R.string.spell_running;
        return reverse? ReverseSpells[index].NAME : Spells[index].NAME;
    }

    private int getItemDesc(){
        if (running) return R.string.spell_running_desc;
        return reverse? ReverseSpells[index].DESC : Spells[index].DESC;
    }

    private boolean running;
    public void setRunning(boolean r){ running = r; UpdateState();}

    public void RunSpell(View v)
    {
        if(running) {
            Toast.makeText(this, R.string.spell_locked_swipe_running, Toast.LENGTH_SHORT).show();
            return;
        }

        if(reverse) ReverseSpells[index].Run();
        else Spells[index].Run();
    }

    public void Success() {
        if (reverse) reverse = false;
        else if(ReverseSpells[index]!=null) reverse = true;
        setRunning(false);
    }

    public boolean failed;
    public void Fail() {
        setRunning(false);
        // TODO: EXPLOSION
    }

    public void Next() { SwitchSpell((index + 1) % Spells.length);}
    public void Back() { SwitchSpell((index == 0 ? Spells.length : index) - 1);}
    @SuppressLint("ClickableViewAccessibility")
    private void SetSwipeEvents() {
        ConstraintLayout constraintLayout = findViewById(R.id.spellContentConstraint);
        constraintLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeRight() {
                ((SpellActivity) ctx).Back();
            }

            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeLeft() {
                ((SpellActivity) ctx).Next();
            }

            public void onSwipeTop() {
            }

            public void onSwipeBottom() {
            }
        });
    }

}