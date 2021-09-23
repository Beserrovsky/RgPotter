package com.beserrovsky.rgpotter.ui.curiosity;

import com.beserrovsky.rgpotter.models.CharacterModel;

import java.util.function.Function;

public class Curiosity<T> {
    public final int title;
    public final int desc;
    public final GetterInterface getterInterface;

    public Curiosity(int titleRes, int descRes, GetterInterface getterInterface) {
        this.title = titleRes;
        this.desc = descRes;
        this.getterInterface = getterInterface;
    }

    public interface GetterInterface {
        CharacterModel getCharacter();
    }
}
