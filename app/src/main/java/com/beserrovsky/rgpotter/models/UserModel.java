package com.beserrovsky.rgpotter.models;

public class UserModel {
    public String email;
    public String name;
    public String house_Id;
    public House house;
    class House {
        public String id;
        public String name;
    }
    public String pronoum;
    public Gender gender;
    class Gender {
        public String pronoum;
    }
    public String Password;
    public int lumusSuccess;
    public int lumusFails;
}
