package com.beserrovsky.rgpotter.ui.curiosity;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.beserrovsky.rgpotter.data.Result;
import com.beserrovsky.rgpotter.data.character.CharacterRepository;
import com.beserrovsky.rgpotter.data.login.LoginRepository;
import com.beserrovsky.rgpotter.models.CharacterModel;
import com.beserrovsky.rgpotter.ui.rg.LoginViewModel;

public class CuriosityViewModel extends ViewModel {

    private final CharacterRepository characterRepository;
    public CuriosityViewModel(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public MutableLiveData<CharacterModel[]> Characters = new MutableLiveData<>();

    public LiveData<CharacterModel[]> getCharacters() {
        return this.Characters;
    }

    public void fetchCharacters() {
        characterRepository.makeGetRequest(result -> {
            if (result instanceof Result.Success) {
                Characters.postValue(((Result.Success<CharacterModel[]>) result).data);
            } else {
                Log.e("User Fetch", ((Result.Error<CharacterModel[]>) result).exception.toString());
            }
        });
    }
}

