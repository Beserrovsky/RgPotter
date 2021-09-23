package com.beserrovsky.rgpotter.ui.rg;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beserrovsky.rgpotter.data.Result;
import com.beserrovsky.rgpotter.data.user.UserParser;
import com.beserrovsky.rgpotter.data.user.UserRepository;
import com.beserrovsky.rgpotter.models.UserModel;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;
    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final MutableLiveData<UserModel> user = new MutableLiveData<>();

    public LiveData<UserModel> getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user.postValue(user);
    }

    public void fetchUser() {
        userRepository.makeGetRequest(result -> {
            if (result instanceof Result.Success) {
                setUser(((Result.Success<UserModel>) result).data);
            } else {
                Log.e("User Fetch", ((Result.Error<UserModel>) result).exception.toString());
            }
        });
    }

    public MutableLiveData<Boolean> UserCreated = new MutableLiveData<Boolean>();

    public void postUser(UserModel user) {
        String json = new UserParser().parse(user);
        userRepository.makePostRequest(json, result -> {
            if (result instanceof Result.Success) {
                UserCreated.postValue(true);
            } else {
                Log.e("User Post", ((Result.Error<UserModel>) result).exception.toString());
                patchUser(user);
            }
        });
    }

    public void patchUser(UserModel user) {
        String json = new UserParser().parse(user);
        userRepository.makePatchRequest(json ,result -> {
            if (result instanceof Result.Success) {
                setUser(((Result.Success<UserModel>) result).data);
            } else {
                Log.e("User Fetch", ((Result.Error<UserModel>) result).exception.toString());
            }
        });
    }

    public void delUser() {
        userRepository.makeDeleteRequest(result -> {
            if (result instanceof Result.Success) {
                setUser(null);
            } else {
                Log.e("User Delete", ((Result.Error<UserModel>) result).exception.toString());
            }
        });
    }
}
