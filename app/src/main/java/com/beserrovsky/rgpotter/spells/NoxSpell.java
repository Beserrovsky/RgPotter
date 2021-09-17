package com.beserrovsky.rgpotter.spells;

import com.beserrovsky.rgpotter.R;
import com.beserrovsky.rgpotter.SpellActivity;

public class NoxSpell extends Spell {

    public NoxSpell(SpellActivity ctx) {
        super(ctx);
        this.NAME = R.string.spell_nox;
        this.DESC = R.string.spell_nox_desc;
    }

    @Override
    public int Update(int a){
        return 1;
    }

    @Override
    public void OnSuccess() {

    }

    @Override
    public void OnFailure() {

    }
}