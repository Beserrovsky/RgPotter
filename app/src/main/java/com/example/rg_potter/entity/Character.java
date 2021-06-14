package com.example.rg_potter.entity;

public class Character{

    public int id = 0;
    public String name = "";
    public String species = "";
    public String gender_id = "";
    public String patronus = "";
    public String house_id = "";

    public House House;
    public Gender Gender;

    public Character(String name, String species, String gender_id, String patronus, String house_id){
        if(name!=null) this.name = name;
        if(species!=null) this.species = species;
        if(gender_id!=null) this.gender_id = gender_id;
        if(patronus!=null) this.patronus = patronus;
        if(house_id!=null) this.house_id = house_id;

        this.Gender = new Gender(this.gender_id);
        this.House = new House(this.house_id);
    }

}
