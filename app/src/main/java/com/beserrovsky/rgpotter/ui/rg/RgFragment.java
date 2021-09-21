package com.beserrovsky.rgpotter.ui.rg;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beserrovsky.rgpotter.R;
import com.beserrovsky.rgpotter.data.login.JWTParser;
import com.beserrovsky.rgpotter.data.login.LoginRepository;
import com.beserrovsky.rgpotter.data.user.UserParser;
import com.beserrovsky.rgpotter.data.user.UserRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RgFragment extends Fragment {
    public RgFragment() {}

    LoginViewModel loginModel;
    UserViewModel userModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        LoginViewModelFactory loginViewModelFactory = new LoginViewModelFactory(
                getContext(),
                new LoginRepository(new JWTParser(), executorService));

        loginModel = new ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel.class);

        UserViewModelFactory userViewModelFactory = new UserViewModelFactory(
                new UserRepository(new UserParser(), loginModel, executorService));

        userModel = new ViewModelProvider(this, userViewModelFactory).get(UserViewModel.class);

        userModel.getUser().observe(getViewLifecycleOwner(), user -> {
            Log.i("User", user.toString());
            // TODO: Update UI
        });

        loginModel.getJWT().observe(getViewLifecycleOwner(), jwt -> {
            Log.i("Login", "Bearer " + jwt);
            userModel.fetchUser();
        });

        // loginModel.makeLoginRequest("felipe@adm.com", "SecurePassword");
        return inflater.inflate(R.layout.fragment_rg, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

