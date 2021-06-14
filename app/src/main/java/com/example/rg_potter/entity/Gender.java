package com.example.rg_potter.entity;

import com.example.rg_potter.R;

public class Gender {

    public int NameResource = R.string.gender_u_name;
    public int PronounResource = R.string.gender_u_pronoun;

    public Gender(String gender_id){
        gender_id= gender_id==null? "" : gender_id;
        switch (gender_id.toLowerCase()){
            case "male":
                this.NameResource = R.string.gender_m_name;
                this.PronounResource = R.string.gender_m_pronoun;
                break;
            case "female":
                this.NameResource = R.string.gender_f_name;
                this.PronounResource = R.string.gender_f_pronoun;
                break;
            default:
                // Already inherited on initialization
                break;
        }
    }
}
