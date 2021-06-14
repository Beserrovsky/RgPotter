package com.example.rg_potter.entity;

public class Character{

    private int id = 0;
    private String name = null;
    private String birth = null;
    private String death = null;
    private String species = null;
    private String ancestry = null;
    private String gender = null;
    private String hair_color = null;
    private String eye_color = null;
    private String wand = null;
    private String patronus = null;
    private String house = null;
    private String[] associated_groups = null;
    private int[] books_featured_in = null;

    public House House = null;
    public Gender Gender = null;

    public Character(){
        this.Gender = new Gender(this.gender);
        this.House = new House(this.house);
    }
}
