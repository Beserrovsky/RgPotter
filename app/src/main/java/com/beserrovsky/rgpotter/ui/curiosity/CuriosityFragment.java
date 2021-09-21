package com.beserrovsky.rgpotter.ui.curiosity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.beserrovsky.rgpotter.R;
import com.beserrovsky.rgpotter.models.CharacterModel;

public class CuriosityFragment extends Fragment {
    public CuriosityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_curiosity, container, false);
    }

    CuriosityViewModel model;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        model = new ViewModelProvider(this).get(CuriosityViewModel.class);

//        model.getCharacters().observe(getViewLifecycleOwner(), users -> {
//            // update UI
//        });

        super.onViewCreated(view, savedInstanceState);
    }
}