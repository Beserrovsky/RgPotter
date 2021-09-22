package com.beserrovsky.rgpotter.ui.curiosity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.beserrovsky.rgpotter.data.character.CharacterRepository;

public class CuriosityViewModelFactory implements ViewModelProvider.Factory {

    private CharacterRepository mCharacterRepository;

    public CuriosityViewModelFactory(CharacterRepository characterRepository) {
        mCharacterRepository = characterRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CuriosityViewModel(mCharacterRepository);
    }
}
