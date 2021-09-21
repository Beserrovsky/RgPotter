package com.beserrovsky.rgpotter.ui.rg;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.beserrovsky.rgpotter.data.login.JWTParser;
import com.beserrovsky.rgpotter.data.login.LoginRepository;
import com.beserrovsky.rgpotter.data.user.UserRepository;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    private Context mContext;
    private LoginRepository mLoginRepository;
    private UserRepository mUserRepository;

    public UserViewModelFactory(Context Context, LoginRepository loginRepository, UserRepository userRepository) {
        mLoginRepository = loginRepository;
        mUserRepository = userRepository;
        mContext = Context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserViewModel(mContext, mLoginRepository, mUserRepository);
    }
}
