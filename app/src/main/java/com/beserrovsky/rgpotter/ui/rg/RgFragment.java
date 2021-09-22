package com.beserrovsky.rgpotter.ui.rg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    boolean LoginJustOpened = true;

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

        userModel.UserCreated.observe(getViewLifecycleOwner(), created ->{
            loginUI(LoginMode.UserJustCreated);
        });

        loginModel.getJWT().observe(getViewLifecycleOwner(), jwt -> {
            if (jwt == null) {
                if (LoginJustOpened) {LoginJustOpened = false; loginUI(LoginMode.LoginJustOpened);}
                else loginUI(LoginMode.Error);
            }
            else userModel.fetchUser();
        });

        return inflater.inflate(R.layout.fragment_rg, container, false);
    }

    ConstraintLayout loginLayout, formLayout, userLayout;
    RelativeLayout loadingLayout;

    TextView loginEmailText, loginPasswordText, loginErrorText,
            formEmailText, formNameText, formPasswordText,
            userEmailText, userNameText, userHouseText, userLumusText;
    Button loginBtn, loginSignupBtn, formSaveBtn, userLogoutBtn, userEditBtn;
    Spinner formHouseSpinner, formPronoumSpinner;
    CheckBox loginCredentialsChk;
    ConstraintLayout userHouseLayout;
    ImageView userHouseImg;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Layouts
        loginLayout = view.findViewById(R.id.rgLoginLayout);
        formLayout = view.findViewById(R.id.rgFormLayout);
        userLayout = view.findViewById(R.id.rgUserLayout);
        loadingLayout = view.findViewById(R.id.rgLoadingLayout);

        // Login
        loginEmailText = view.findViewById(R.id.rgLoginEmailText);
        loginPasswordText = view.findViewById(R.id.rgLoginPasswordText);
        loginErrorText = view.findViewById(R.id.rgLoginErrorText);
        loginBtn = view.findViewById(R.id.rgLoginBtn);
        loginSignupBtn = view.findViewById(R.id.rgLoginSignupBtn);
        loginCredentialsChk = view.findViewById(R.id.rgLoginCredentialsChk);

        // Form
        formEmailText = view.findViewById(R.id.rgFormEmailText);
        formNameText = view.findViewById(R.id.rgFormNameText);
        formPasswordText = view.findViewById(R.id.rgFormPasswordText);
        formSaveBtn = view.findViewById(R.id.rgFormSaveBtn);
        formHouseSpinner = view.findViewById(R.id.rgFormHouseSpinner);
        formPronoumSpinner = view.findViewById(R.id.rgFormPronoumSpinner);

        // User
        userNameText = view.findViewById(R.id.rgUserNameText);
        userLogoutBtn = view.findViewById(R.id.rgUserLogoutBtn);
        userEditBtn = view.findViewById(R.id.rgUserEditBtn);
        userHouseLayout = view.findViewById(R.id.rgUserHouseLayout);
        userHouseImg = view.findViewById(R.id.rgUserHouseImg);

        // Click Handlers
        loginSignupBtn.setOnClickListener(this::SignUp);
        loginBtn.setOnClickListener(this::Login);
        formSaveBtn.setOnClickListener(this::Create);
        userEditBtn.setOnClickListener(this::Edit);
        userLogoutBtn.setOnClickListener(this::Logout);
        super.onViewCreated(view, savedInstanceState);
    }

    // Button Handlers

    public void SignUp(View v) {
        userFormUI();
    }

    public void Create(View v) {
        // TODO: send data
    }

    public void Login(View v) {
        loginModel.makeLoginRequest(
                loginEmailText.getText().toString(),
                loginPasswordText.getText().toString(),
                loginCredentialsChk.isChecked());
        resetLayouts();
    }

    public void Edit(View v) {
        userFormUI(userModel.getUser().getValue());
    }

    public void Logout(View v) {
        loginModel.logout();
    }

    // UI Handlers

    private enum LoginMode { None, Error, UserJustCreated, LoginJustOpened }
    public void loginUI(LoginMode mode) {
        resetLayouts();
        loginErrorText.setVisibility(View.INVISIBLE);
        setLayout(loginLayout);
        switch (mode) {
            case Error:
                loginErrorText.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void userUI(UserModel user) {
        if (user == null) { loginUI(LoginMode.UserJustCreated); return;}

        loginEmailText.setText("");
        loginPasswordText.setText("");

        resetLayouts();
        userNameText.setText(user.name);
        userHouseLayout.setBackgroundColor(HouseResolver.ColorOf(user.house_Id));
        userHouseImg.setBackgroundResource(HouseResolver.ImageOf(user.house_Id)); //FIXME: NOT WORKING PROPERLY
        setLayout(userLayout);
    }

    private void userFormUI() {
        resetLayouts();
        setLayout(formLayout);
    }

    private void userFormUI(UserModel user) {
        resetLayouts();
        if (user == null) { loginUI(LoginMode.None); return;}
        setLayout(formLayout);
    }

    private void resetLayouts(){
        loginLayout.setVisibility(View.INVISIBLE);
        formLayout.setVisibility(View.INVISIBLE);
        userLayout.setVisibility(View.INVISIBLE);

        loadingLayout.setVisibility(View.VISIBLE);
    }

    private void setLayout(ConstraintLayout layout){
        loadingLayout.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
    }
}

