package com.beserrovsky.rgpotter.ui.rg;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beserrovsky.rgpotter.R;
import com.beserrovsky.rgpotter.data.login.JWTParser;
import com.beserrovsky.rgpotter.data.login.LoginRepository;
import com.beserrovsky.rgpotter.data.user.UserRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RgFragment extends Fragment {
    public RgFragment() {}

    UserViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        UserViewModelFactory viewModelFactory = new UserViewModelFactory(
                getContext(),
                new LoginRepository(new JWTParser(), executorService),
                new UserRepository());

        model = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);

        model.getJWT().observe(getViewLifecycleOwner(), jwt -> {
            Log.i("Login", "Bearer " + jwt);
        });
        // model.makeLoginRequest("felipe@adm.com", "SecurePassword");
        return inflater.inflate(R.layout.fragment_rg, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

