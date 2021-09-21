package com.beserrovsky.rgpotter.models;

public class CredentialsModel {
    public CredentialsModel(String email, String password) {
        this.Email = email;
        this.Password = password;
    }

    public String Email;
    public String Password;
}
