package com.beserrovsky.rgpotter.ui.spells;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.beserrovsky.rgpotter.MainActivity;
import com.beserrovsky.rgpotter.R;
import com.beserrovsky.rgpotter.spells.LeviosaSpell;
import com.beserrovsky.rgpotter.spells.LumusSpell;
import com.beserrovsky.rgpotter.spells.NoxSpell;
import com.beserrovsky.rgpotter.spells.Spell;
import com.beserrovsky.rgpotter.util.OnSwipeTouchListener;
import com.beserrovsky.rgpotter.util.SpellFeedbackView;

public class SpellFragment extends Fragment {
    public SpellFragment() {}

    Spell[] Spells        = { new LumusSpell(this), new LeviosaSpell(this) };
    Spell[] ReverseSpells = { new NoxSpell(this) ,              null          };

    public SpellFeedbackView feedbackView;
    int index = 0;
    boolean reverse;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spell, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        nameTextView = view.findViewById(R.id.spellNameTextView);
        descNameTextView = view.findViewById(R.id.spellDescTitleTextView);
        descTextView = view.findViewById(R.id.spellDescTextView);
        constraintLayout = view.findViewById(R.id.spellContentConstraint);
        feedbackView = view.findViewById(R.id.spellFeedbackView);
        feedbackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(running) {
                    Toast.makeText(getContext(), R.string.spell_locked_swipe_running, Toast.LENGTH_SHORT).show();
                    return;
                }

                if(reverse) ReverseSpells[index].Run();
                else Spells[index].Run();
            }
        });
        SetSwipeEvents();
        UpdateState();
        super.onViewCreated(view, savedInstanceState);
    }

    public void SwitchSpell(int i) {
        if ( !running && !reverse && i < Spells.length ) {
            this.index = i;
            UpdateState();
        }else if(reverse){
            Toast.makeText(getContext(), R.string.spell_locked_swipe_reverse, Toast.LENGTH_SHORT).show();
        }else if(running){
            Toast.makeText(getContext(), R.string.spell_locked_swipe_running, Toast.LENGTH_SHORT).show();
        }
    }

    TextView nameTextView, descNameTextView, descTextView;

    private void UpdateState() {
        nameTextView.setText(getItemName());
        descNameTextView.setText(getItemName());
        descTextView.setText(getItemDesc());
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

    ConstraintLayout constraintLayout;
    public void Next() { SwitchSpell((index + 1) % Spells.length);}
    public void Back() { SwitchSpell((index == 0 ? Spells.length : index) - 1);}
    @SuppressLint("ClickableViewAccessibility")
    private void SetSwipeEvents() {
        constraintLayout.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeRight() {
                ((SpellFragment)((MainActivity) ctx).fragment).Back();
            }

            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeLeft() {
                ((SpellFragment)((MainActivity) ctx).fragment).Next();
            }

            public void onSwipeTop() {
            }

            public void onSwipeBottom() {
            }
        });
    }
}