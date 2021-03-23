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
        public String house_id;

        public House(String house, Context ctx){

            this.house_id = house == null? "" : house.toLowerCase();

            switch (house_id){
                case "gryffindor":
                    this.Name = ctx.getString(R.string.house_Gryffindor);
                    this.Color = R.color.house_Gryffindor;
                    break;
                case "hufflepuff":
                    this.Name = ctx.getString(R.string.house_Hufflepuff);
                    this.Color = R.color.house_Hufflepuff;
                    break;
                case "ravenclaw":
                    this.Name = ctx.getString(R.string.house_Ravenclaw);
                    this.Color = R.color.house_Ravenclaw;
                    break;
                case "slytherin":
                    this.Name = ctx.getString(R.string.house_Slytherin);
                    this.Color = R.color.house_Slytherin;
                    break;
                default:
                    this.Name = ctx.getString(R.string.house_none);
                    this.Color = R.color.black;
                    break;
            }
        }
    }
}
