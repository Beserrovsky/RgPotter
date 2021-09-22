package com.beserrovsky.rgpotter.ui.rg;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.beserrovsky.rgpotter.R;
import com.beserrovsky.rgpotter.data.login.JWTParser;
import com.beserrovsky.rgpotter.data.login.LoginRepository;
import com.beserrovsky.rgpotter.data.user.UserParser;
import com.beserrovsky.rgpotter.data.user.UserRepository;
import com.beserrovsky.rgpotter.models.UserModel;
import com.beserrovsky.rgpotter.util.HouseResolver;

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

        userModel.getUser().observe(getViewLifecycleOwner(), this::userUI);

        loginModel.getJWT().observe(getViewLifecycleOwner(), jwt -> {
            if (jwt == null) loginUI(true);
            else userModel.fetchUser();
        });

        return inflater.inflate(R.layout.fragment_rg, container, false);
    }

    public void Login(View v) {
        loginModel.makeLoginRequest(
                txtEmail.getText().toString(),
                txtPassword.getText().toString(),
                chkSaveCredentials.isChecked());
        resetLayouts();
    }

    public void CreateAccount(View v) {
        createUI();
    }

    public void Logout(View v) {
        loginModel.logout();
    }

    TextView txtEmail, txtPassword, txtUserName, txtError;
    ConstraintLayout loginLayout, userViewLayout, userHouseLayout;
    RelativeLayout loadingPanel;
    Button btnLogin, btnSignUp, btnLogout;
    CheckBox chkSaveCredentials;
    ImageView imgHouse;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loginLayout = view.findViewById(R.id.rgLoginMenu);
        userViewLayout = view.findViewById(R.id.rgUserViewMenu);
        userHouseLayout = view.findViewById(R.id.rgUserHouseLayout);
        loadingPanel = view.findViewById(R.id.rgLoadingPanel);

        txtEmail = view.findViewById(R.id.rgTextEmail);
        txtPassword = view.findViewById(R.id.rgTextPassword);
        txtUserName = view.findViewById(R.id.rgTxtUserName);
        txtError = view.findViewById(R.id.rgTxtError);

        imgHouse = view.findViewById(R.id.rgImgHouse);

        btnLogin = view.findViewById(R.id.rgBtnLogin);
        btnSignUp = view.findViewById(R.id.rgBtnCreate);
        btnLogout = view.findViewById(R.id.rgBtnLogout);
        chkSaveCredentials = view.findViewById(R.id.rgChkSaveCredentials);

        btnLogin.setOnClickListener(this::Login);
        btnSignUp.setOnClickListener(this::CreateAccount);
        btnLogout.setOnClickListener(this::Logout);
        super.onViewCreated(view, savedInstanceState);
    }

    public void loginUI(boolean error) {
        resetLayouts();
        setLayout(loginLayout);
        if(error) txtError.setVisibility(View.VISIBLE);
    }

    public void userUI(UserModel user) {
        if (user == null) {loginUI(false); return;}

        txtEmail.setText("");
        txtPassword.setText("");

        resetLayouts();
        txtUserName.setText(user.name);
        userHouseLayout.setBackgroundColor(HouseResolver.ColorOf(user.house_Id));
        imgHouse.setBackgroundResource(HouseResolver.ImageOf(user.house_Id));
        setLayout(userViewLayout);
    }

    public void createUI() {
        throw new UnsupportedOperationException();
    }

    public void resetLayouts(){
        userViewLayout.setVisibility(View.INVISIBLE);
        loginLayout.setVisibility(View.INVISIBLE);
        loadingPanel.setVisibility(View.VISIBLE);
        txtError.setVisibility(View.INVISIBLE);
    }

    public void setLayout(ConstraintLayout layout){
        loadingPanel.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
    }
}

