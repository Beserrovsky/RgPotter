package com.beserrovsky.rgpotter.ui.rg;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beserrovsky.rgpotter.data.RepositoryCallback;
import com.beserrovsky.rgpotter.data.Result;
import com.beserrovsky.rgpotter.data.user.UserRepository;
import com.beserrovsky.rgpotter.models.UserModel;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;
    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final MutableLiveData<UserModel> user = new MutableLiveData<UserModel>();

    public LiveData<UserModel> getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user.postValue(user);
    }

    public void delUser() {
        throw new UnsupportedOperationException();
    }

    public void fetchUser() {
        userRepository.makeGetRequest(new RepositoryCallback<UserModel>() {
            @Override
            public void onComplete(Result<UserModel> result) {
                if (result instanceof Result.Success) {
                    setUser(((Result.Success<UserModel>) result).data);
                } else {
                    Log.e("User", ((Result.Error<UserModel>) result).exception.toString());
                }
            }
        });
    }
}
