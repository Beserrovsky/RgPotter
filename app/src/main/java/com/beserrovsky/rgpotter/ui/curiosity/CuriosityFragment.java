package com.beserrovsky.rgpotter.ui.curiosity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beserrovsky.rgpotter.R;

public class CuriosityFragment extends Fragment {
    public CuriosityFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO: CHECK CONNECTION
        return inflater.inflate(R.layout.fragment_curiosity, container, false);
    }
}