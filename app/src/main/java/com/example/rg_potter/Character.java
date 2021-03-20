package com.example.rg_potter;

import android.content.Context;

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

        public House(String house_id, Context ctx){
            switch (house_id == null? "" : house_id.toLowerCase()){
                case "gryffindor":
                    this.Name = ctx.getString(R.string.house_Gryffindor);
                    break;
                case "hufflepuff":
                    this.Name = ctx.getString(R.string.house_Hufflepuff);
                    break;
                case "ravenclaw":
                    this.Name = ctx.getString(R.string.house_Ravenclaw);
                    break;
                case "slytherin":
                    this.Name = ctx.getString(R.string.house_Slytherin);
                    break;
                default:
                    this.Name = ctx.getString(R.string.house_none);
                    break;
            }
        }
    }
}
