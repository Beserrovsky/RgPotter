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
        this.sharedPreferences = ctx.getSharedPreferences("login", Context.MODE_PRIVATE);

        boolean saved = sharedPreferences.getBoolean("saveCredentials", false);

        if (saved) {
            String email = sharedPreferences.getString("email", null);
            String password = sharedPreferences.getString("password", null);
            makeLoginRequest(email, password, true);
            saveCredentials.postValue(true);
        }
        else setJWT(null);
    }

    private String email, password;

    private MutableLiveData<Boolean> saveCredentials = new MutableLiveData<Boolean>(false);
    private final MutableLiveData<String> jwt = new MutableLiveData<>();

    public LiveData<String> getJWT() {
        return jwt;
    }
    public void setJWT(String jwt) { this.jwt.postValue(jwt); }

    private LiveData<Boolean> getSaveCredentials(){ return saveCredentials; }
    private void setSaveCredentials(Boolean save) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (save) {
            editor.putString("email", email);
            editor.putString("password", password);
        }
        editor.putBoolean("saveCredentials", save);
        editor.commit();
        this.saveCredentials.postValue(save);
    }

    public void makeLoginRequest(String email, String password, boolean save) {
        this.email = email;
        this.password = password;
        String jsonBody = "{ email: \"" + email + "\", password: \"" + password + "\" }";
        loginRepository.makeLoginRequest(jsonBody, result -> {
            if (result instanceof Result.Success) {
                setJWT(((Result.Success<String>) result).data);
                setSaveCredentials(save);
            } else {
                setJWT(null);
                Log.e("Login", ((Result.Error<String>) result).exception.toString());
            }
        });
    }

    public void logout() {
        setJWT(null);
    }
}

