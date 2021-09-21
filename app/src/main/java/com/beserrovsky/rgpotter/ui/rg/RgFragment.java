package com.beserrovsky.rgpotter.ui.rg;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beserrovsky.rgpotter.R;
import com.beserrovsky.rgpotter.data.login.JWTParser;
import com.beserrovsky.rgpotter.data.login.LoginRepository;
import com.beserrovsky.rgpotter.data.user.UserParser;
import com.beserrovsky.rgpotter.data.user.UserRepository;
import com.beserrovsky.rgpotter.models.UserModel;

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

        loginModel = new ViewModelProvider(requireActivity(), loginViewModelFactory).get(LoginViewModel.class);

        UserViewModelFactory userViewModelFactory = new UserViewModelFactory(
                new UserRepository(new UserParser(), loginModel, executorService));

        userModel = new ViewModelProvider(requireActivity(), userViewModelFactory).get(UserViewModel.class);

        userModel.getUser().observe(getViewLifecycleOwner(), user -> {
            userUI(user);
        });

        loginModel.getJWT().observe(getViewLifecycleOwner(), jwt -> {
            if (jwt == null) loginUI();
            else userModel.fetchUser();
        });

        return inflater.inflate(R.layout.fragment_rg, container, false);
    }

    public void Login(View v) {
        loginModel.makeLoginRequest(
                txtEmail.getText().toString(),
                txtPassword.getText().toString(),
                chkSaveCredentials.isChecked());
    }


    TextView txtEmail, txtPassword, txtUserName;
    ConstraintLayout loginLayout, userViewLayout;
    RelativeLayout loadingPanel;
    Button btnLogin;
    CheckBox chkSaveCredentials;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loginLayout = view.findViewById(R.id.rgLoginMenu);
        userViewLayout = view.findViewById(R.id.rgUserViewMenu);
        loadingPanel = view.findViewById(R.id.rgLoadingPanel);
        txtEmail = view.findViewById(R.id.rgTextEmail);
        txtPassword = view.findViewById(R.id.rgTextPassword);
        txtUserName = view.findViewById(R.id.rgTxtUserName);
        btnLogin = view.findViewById(R.id.rgBtnLogin);
        chkSaveCredentials = view.findViewById(R.id.rgChkSaveCredentials);

        btnLogin.setOnClickListener(this::Login);
        super.onViewCreated(view, savedInstanceState);
    }
    
    public void loginUI() {
        resetLayouts();
        setLayout(loginLayout);
    }

    public void userUI(UserModel user) {
        if (user == null) {loginUI(); return;}
        resetLayouts();
        txtUserName.setText(user.name);
        setLayout(userViewLayout);
    }

    public void resetLayouts(){
        userViewLayout.setVisibility(View.INVISIBLE);
        loginLayout.setVisibility(View.INVISIBLE);
        loadingPanel.setVisibility(View.VISIBLE);
    }

    public void setLayout(ConstraintLayout layout){
        loadingPanel.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
    }
}

