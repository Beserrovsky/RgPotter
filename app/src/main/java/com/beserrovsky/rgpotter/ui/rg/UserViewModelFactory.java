package com.beserrovsky.rgpotter.ui.rg;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.beserrovsky.rgpotter.data.login.LoginRepository;
import com.beserrovsky.rgpotter.data.user.UserRepository;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    private final UserRepository mUserRepository;

    public UserViewModelFactory(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserViewModel(mUserRepository);
    }
}
