package com.beserrovsky.rgpotter.spells;

import android.os.Handler;

import com.beserrovsky.rgpotter.R;
import com.beserrovsky.rgpotter.ui.spells.SpellFragment;

public abstract class Spell{

    public final static int SPELL_TIME = 100;
    public final static int SPELL_RUN_TIME = SPELL_TIME / 100;

    public int
            NAME = R.string.spell_null,
            DESC = R.string.spell_null_desc;

    private final SpellFragment ctx;

    public Spell(SpellFragment ctx){
        this.ctx = ctx;
    }

    public void Run(){
        ctx.setRunning(true);
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int p = 1;
            int a = 1;
            public void run() {
                a = Update(a);
                ctx.feedbackView.Update(a, p);
                if (p++ < 100 && !ctx.failed) {
                    handler.postDelayed(this, SPELL_RUN_TIME);
                }else{
                    ctx.Success();
                }
            }
        };
        runnable.run();
    }

    public void Fail() { ctx.Fail(); }

    public abstract int Update(int last_aggressiveness);

    public abstract void OnSuccess();

    public abstract void OnFailure();
}

