package com.example.rg_potter.entity;

import com.example.rg_potter.R;

public class House {

    public int NameResource = R.string.house_none;
    public int Color = R.color.house_None;
    public int Image = R.drawable.house_none;
    public int SpinnerIndex = 4;

    public House(String house_id){

        switch (house_id.toLowerCase()){
            case "gryffindor":
                this.NameResource = R.string.house_gryffindor;
                this.Color = R.color.house_Gryffindor;
                this.Image = R.drawable.house_gryffindor;
                this.SpinnerIndex = 0;
                break;
            case "hufflepuff":
                this.NameResource = R.string.house_hufflepuff;
                this.Color = R.color.house_Hufflepuff;
                this.Image = R.drawable.house_hufflepuff;
                this.SpinnerIndex = 1;
                break;
            case "ravenclaw":
                this.NameResource = R.string.house_ravenclaw;
                this.Color = R.color.house_Ravenclaw;
                this.Image = R.drawable.house_ravenclaw;
                this.SpinnerIndex = 2;
                break;
            case "slytherin":
                this.NameResource = R.string.house_slytherin;
                this.Color = R.color.house_Slytherin;
                this.Image = R.drawable.house_slytherin;
                this.SpinnerIndex = 3;
                break;
            default:
                // Already inherited on initialization
                break;
        }
    }

}
