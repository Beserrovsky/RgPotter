package com.beserrovsky.rgpotter.ui.rg;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beserrovsky.rgpotter.data.Result;
import com.beserrovsky.rgpotter.data.login.LoginRepository;
import com.beserrovsky.rgpotter.data.RepositoryCallback;
import com.beserrovsky.rgpotter.data.user.UserRepository;
import com.beserrovsky.rgpotter.models.UserModel;

public class UserViewModel extends ViewModel
{
    private final Context ctx;
    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final SharedPreferences sharedPreferences;
    public UserViewModel(Context ctx, LoginRepository loginRepository, UserRepository userRepository) {
        this.ctx = ctx;
        this.loginRepository = loginRepository;
        this.userRepository = userRepository;
        this.sharedPreferences = ctx.getSharedPreferences("jwt", Context.MODE_PRIVATE);
        String possibleJwt = sharedPreferences.getString("jwt", null);
        if ( possibleJwt != null) this.jwt.postValue(possibleJwt);
    }

    private final MutableLiveData<String> jwt = new MutableLiveData<String>();
    public LiveData<String> getJWT() {
        return jwt;
    }
    public void setJWT(String jwt) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jwt", jwt);
        editor.commit();
        this.jwt.postValue(jwt);
    }

    public void makeLoginRequest(String email, String password) {
        String jsonBody = "{ email: \"" + email + "\", password: \"" + password + "\" }";
        loginRepository.makeLoginRequest(jsonBody, new RepositoryCallback<String>() {
            @Override
            public void onComplete(Result<String> result) {
                if (result instanceof Result.Success) {
                    setJWT(((Result.Success<String>) result).data);
                } else {
                    Log.e("Login", ((Result.Error<String>) result).exception.toString());
                }
            }
        });
    }

    private final MutableLiveData<UserModel> user = new MutableLiveData<UserModel>();
    public LiveData<UserModel> getUser() {
        return user;
    }

    public void makeUserGetRequest(String JWT) {

    }
}

