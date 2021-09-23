package com.beserrovsky.rgpotter.ui.curiosity;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beserrovsky.rgpotter.R;
import com.beserrovsky.rgpotter.data.Result;
import com.beserrovsky.rgpotter.data.character.CharacterRepository;
import com.beserrovsky.rgpotter.models.CharacterModel;

import java.util.Random;
import java.util.stream.Stream;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CuriosityViewModel extends ViewModel {

    private final CharacterRepository characterRepository;
    public CuriosityViewModel(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    private final MutableLiveData<Integer> UiIndex = new MutableLiveData<Integer>(0);

    public LiveData<Integer> getUiIndex() { return UiIndex; }

    private final MutableLiveData<CharacterModel[]> Characters = new MutableLiveData<>(new CharacterModel[0]);

    public Curiosity[] Curiosities = new Curiosity[]{
            new Curiosity(
                    R.string.cur_curiosities_random_title,
                    R.string.cur_curiosities_random_desc,
                    ()-> Stream.of(Characters.getValue())
                            .skip(Characters.getValue().length == 0? 0 :
                                    new Random().nextInt(Characters.getValue().length ))
                            .findFirst().orElse(null)
            ),
            new Curiosity(
                    R.string.cur_curiosities_harry_title,
                    R.string.cur_curiosities_harry_desc,
                    ()-> Stream.of(Characters.getValue()).filter(c -> c.id == 326)
                            .findAny().get())
    };

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

    public void Back() {
        int lastIndex = UiIndex.getValue();
        this.UiIndex.postValue( (lastIndex == 0 ? Curiosities.length : lastIndex) - 1 );
    }

    public void Next() {
        this.UiIndex.postValue( (UiIndex.getValue() + 1) % Curiosities.length);
    }
}

