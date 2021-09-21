package com.beserrovsky.rgpotter.ui.rg;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.beserrovsky.rgpotter.data.login.LoginRepository;
import com.beserrovsky.rgpotter.data.user.UserRepository;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private Context mContext;
    private LoginRepository mLoginRepository;

    public LoginViewModelFactory(Context Context, LoginRepository loginRepository) {
        mLoginRepository = loginRepository;
        mContext = Context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginViewModel(mContext, mLoginRepository);
    }
}
