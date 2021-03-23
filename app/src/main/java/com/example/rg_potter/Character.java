package com.example.rg_potter;

import android.content.Context;
import android.graphics.Color;

public class Character {
    public int id;
    public String name;
    public String birth;
    public String death;
    public String species;
    public String ancestry;
    public String gender;
    public String hair_color;
    public String eye_color;
    public String wand;
    public String patronus;
    public String house;
    public String[] associated_groups;
    public int[] books_featured_in;

    public static class House{

        public String Name;
        public int Color;
        public int Image;
        public String house_id;
        public int SpinnerIndex;

        public House(String house, Context ctx){

            this.house_id = house == null? "" : house.toLowerCase();

            switch (house_id){
                case "gryffindor":
                    this.Name = ctx.getString(R.string.house_Gryffindor);
                    this.Color = R.color.house_Gryffindor;
                    this.Image = R.drawable.house_gryffindor;
                    this.SpinnerIndex = 0;
                    break;
                case "hufflepuff":
                    this.Name = ctx.getString(R.string.house_Hufflepuff);
                    this.Color = R.color.house_Hufflepuff;
                    this.Image = R.drawable.house_hufflepuff;
                    this.SpinnerIndex = 1;
                    break;
                case "ravenclaw":
                    this.Name = ctx.getString(R.string.house_Ravenclaw);
                    this.Color = R.color.house_Ravenclaw;
                    this.Image = R.drawable.house_ravenclaw;
                    this.SpinnerIndex = 2;
                    break;
                case "slytherin":
                    this.Name = ctx.getString(R.string.house_Slytherin);
                    this.Color = R.color.house_Slytherin;
                    this.Image = R.drawable.house_slytherin;
                    this.SpinnerIndex = 3;
                    break;
                default:
                    this.Name = ctx.getString(R.string.house_none);
                    this.Color = R.color.black;
                    this.Image = R.drawable.ic_launcher_foreground;
                    this.SpinnerIndex = 4;
                    break;
            }
        }
    }
}
