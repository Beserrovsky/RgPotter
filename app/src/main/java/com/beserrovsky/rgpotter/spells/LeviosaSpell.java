package com.beserrovsky.rgpotter.spells;

import com.beserrovsky.rgpotter.R;
import com.beserrovsky.rgpotter.ui.spells.SpellFragment;

public class LeviosaSpell extends Spell {

    public LeviosaSpell(SpellFragment ctx) {
        super(ctx);
        this.NAME = R.string.spell_leviosa;
        this.DESC = R.string.spell_leviosa_desc;
    }

    @Override
    public int Update(int a){
        return 0;
    }

    @Override
    public void OnSuccess() {

    }

    @Override
    public void OnFailure() {

    }
}