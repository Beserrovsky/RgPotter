package com.beserrovsky.rgpotter.ui.curiosity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.beserrovsky.rgpotter.R;
import com.beserrovsky.rgpotter.data.character.CharacterParser;
import com.beserrovsky.rgpotter.data.character.CharacterRepository;
import com.beserrovsky.rgpotter.models.CharacterModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuriosityFragment extends Fragment {
    public CuriosityFragment() {}

    CuriosityViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CuriosityViewModelFactory curiosityViewModelFactory = new CuriosityViewModelFactory(
                new CharacterRepository(new CharacterParser(), executorService));

        model = new ViewModelProvider(this, curiosityViewModelFactory).get(CuriosityViewModel.class);

        model.getCharacters().observe(getViewLifecycleOwner(), users -> {
            Log.i("char", users!=null? users.toString() : "null");
        });

        model.fetchCharacters();
        return inflater.inflate(R.layout.fragment_curiosity, container, false);
    }

    RelativeLayout loadingLayout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadingLayout = view.findViewById(R.id.curLoadingLayout);
        super.onViewCreated(view, savedInstanceState);
    }

    private void resetLayouts(){
        loadingLayout.setVisibility(View.VISIBLE);
    }

    private void setLayout(ConstraintLayout layout){
        loadingLayout.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
    }
}