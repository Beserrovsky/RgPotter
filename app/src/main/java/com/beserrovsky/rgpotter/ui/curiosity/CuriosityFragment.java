package com.beserrovsky.rgpotter.ui.curiosity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.beserrovsky.rgpotter.MainActivity;
import com.beserrovsky.rgpotter.R;
import com.beserrovsky.rgpotter.data.character.CharacterParser;
import com.beserrovsky.rgpotter.data.character.CharacterRepository;
import com.beserrovsky.rgpotter.models.CharacterModel;
import com.beserrovsky.rgpotter.ui.spells.SpellFragment;
import com.beserrovsky.rgpotter.util.HouseResolver;
import com.beserrovsky.rgpotter.util.OnSwipeTouchListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuriosityFragment extends Fragment {
    public CuriosityFragment() {}

    CuriosityViewModel model;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CuriosityViewModelFactory curiosityViewModelFactory = new CuriosityViewModelFactory(
                new CharacterRepository(new CharacterParser(), executorService));

        model = new ViewModelProvider(this, curiosityViewModelFactory).get(CuriosityViewModel.class);

        model.getCharacters().observe(getViewLifecycleOwner(), users -> {
            if (users!=null) curiosity();
        });

        model.getUiIndex().observe(getViewLifecycleOwner(), index -> {
            curiosity();
        });

        model.fetchCharacters();
        return inflater.inflate(R.layout.fragment_curiosity, container, false);
    }

    RelativeLayout loadingLayout;
    ConstraintLayout curiosityLayout;

    TextView curiosityTitleText, curiosityDescText,
            curiosityCharacterNameText, curiosityCharacterSpeciesText, curiosityCharacterHouseText;
    ImageView curiosityCharacterHouseImage;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadingLayout = view.findViewById(R.id.curLoadingLayout);
        curiosityLayout = view.findViewById(R.id.curCuriosityLayout);

        curiosityTitleText = view.findViewById(R.id.curCuriosityTitleText);
        curiosityDescText = view.findViewById(R.id.curCuriosityDescText);
        curiosityCharacterNameText = view.findViewById(R.id.curCuriosityCharacterNameText);
        curiosityCharacterSpeciesText = view.findViewById(R.id.curCuriosityCharacterSpeciesText);
        curiosityCharacterHouseText = view.findViewById(R.id.curCuriosityCharacterHouseText);

        curiosityCharacterHouseImage = view.findViewById(R.id.curCuriosityCharacterHouseImage);

        SetSwipeEvents();
        super.onViewCreated(view, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void curiosity() {
        resetLayouts();

        Curiosity curiosity = model.Curiosities[model.getUiIndex().getValue()];
        CharacterModel characterModel = curiosity.getterInterface.getCharacter();

        if (characterModel == null) return;
        setLayout(curiosityLayout);

        curiosityTitleText.setText(curiosity.title);
        curiosityDescText.setText(curiosity.desc);

        curiosityCharacterNameText.setText(characterModel.name);
        curiosityCharacterSpeciesText.setText(characterModel.species);
        curiosityCharacterHouseText.setText(characterModel.house);

        curiosityCharacterHouseImage.setBackgroundResource(HouseResolver.ImageOf(characterModel.house));
    }

    private void resetLayouts(){
        loadingLayout.setVisibility(View.VISIBLE);
    }

    private void setLayout(ConstraintLayout layout){
        loadingLayout.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void SetSwipeEvents() {
        curiosityLayout.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeRight() {
                ((CuriosityFragment)((MainActivity) ctx).fragment).model.Back();
            }

            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeLeft() {
                ((CuriosityFragment)((MainActivity) ctx).fragment).model.Next();
            }

            public void onSwipeTop() {
            }

            public void onSwipeBottom() {
            }
        });
    }
}