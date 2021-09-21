package com.beserrovsky.rgpotter.ui.rg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beserrovsky.rgpotter.data.Result;
import com.beserrovsky.rgpotter.data.login.LoginRepository;

public class LoginViewModel extends ViewModel
{
    private final LoginRepository loginRepository;
    private final SharedPreferences sharedPreferences;
    public LoginViewModel(Context ctx, LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
        this.sharedPreferences = ctx.getSharedPreferences("jwt", Context.MODE_PRIVATE);
        String possibleJwt = sharedPreferences.getString("jwt", null);
        if ( possibleJwt != null) this.jwt.postValue(possibleJwt);
    }

    private final MutableLiveData<String> jwt = new MutableLiveData<>();
    public LiveData<String> getJWT() {
        return jwt;
    }
    @SuppressLint("ApplySharedPref")
    public void setJWT(String jwt) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jwt", jwt);
        editor.commit();
        this.jwt.postValue(jwt);
    }

    public void makeLoginRequest(String email, String password) {
        String jsonBody = "{ email: \"" + email + "\", password: \"" + password + "\" }";
        loginRepository.makeLoginRequest(jsonBody, result -> {
            if (result instanceof Result.Success) {
                setJWT(((Result.Success<String>) result).data);
            } else {
                Log.e("Login", ((Result.Error<String>) result).exception.toString());
            }
        });
    }
}

