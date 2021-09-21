package com.beserrovsky.rgpotter.models;

public class UserModel {
    public String Email;
    public String Name;
    public String House_ID;
    public House House;
    class House {
        public String ID;
        public String Name;
    }
    public String Pronoum;
    public Gender Gender;
    class Gender {
        public String Pronoum;
    }
    public String Password;
    public int lumusSuccess;
    public int lumusFails;
}
