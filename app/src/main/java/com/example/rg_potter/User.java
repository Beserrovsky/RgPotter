package com.example.rg_potter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.Date;

import static com.example.rg_potter.PotterContract.PotterEntry;
import static com.example.rg_potter.PotterContract.PotterContractDbHelper;

public class User {

    private int Id;
    public String Name;
    private String Gender;
    public String Img;
    public Date Birth;
    public String House;
    public String Patronus;


    public User(int id, Context ctx){
        this.Id = id;

        PotterContractDbHelper dbHelper = new PotterContractDbHelper(ctx);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                PotterEntry.COLUMN_NAME_NAME,
                PotterEntry.COLUMN_NAME_GENDER,
                PotterEntry.COLUMN_NAME_IMAGE_PATH,
                PotterEntry.COLUMN_NAME_BIRTH_DATE,
                PotterEntry.COLUMN_NAME_HOUSE,
                PotterEntry.COLUMN_NAME_PATRONUS,
        };

        String selection = PotterEntry._ID + " = ?";
        String[] selectionArgs = { "" + this.getId() };

        boolean retry = false;

        do {

            Cursor c = db.query(
                    PotterEntry.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    selectionArgs,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    ""               // The sort order
            );

            if (c.moveToFirst()) {
                this.Id = c.getInt(0);
                this.Name = c.getString(1);
                this.Gender = c.getString(2);
                this.Img = c.getString(3);
                this.Birth = new Date(c.getInt(4));
                this.House = c.getString(5);
                this.Patronus = c.getString(6);

                c.close();
            } else {

                c.close();
                init(ctx);
                if(!retry) retry = true; //SAFE TRIGGER FOR ONLY ONE RETRY
                else retry = false;
            }
        }while(retry);

        db.close();
        dbHelper.close();

        Log.d("User > ", "User loaded from database:");
        log(ctx);
    }

    private void init(Context ctx){
        PotterContractDbHelper dbHelper = new PotterContractDbHelper(ctx);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = getContentValues(ctx);
        values.put(PotterEntry._ID, this.getId());

        db.insert(PotterEntry.TABLE_NAME, null, values);

        db.close();
        dbHelper.close();
    }

    public void save(Context ctx){

        PotterContractDbHelper dbHelper = new PotterContractDbHelper(ctx);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = getContentValues(ctx);

        String selection = PotterEntry._ID + "= ?";
        String[] selectionArgs = { "" + this.getId() };

        int count = db.update(
                PotterEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        db.close();
        dbHelper.close();

        Log.d("User > ", "User instance saved into database:");
        log(ctx);
    }

    public void log(Context ctx){
        Log.d("User > ", "Id: " + this.Id);
        Log.d("User > ", "Name: " + this.Name);
        Log.d("User > ", "Pronoum: " + this.getPronoun(ctx));
        Log.d("User > ", "Gender_ID: " + this.Gender);
        Log.d("User > ", "Gender: " + this.getGender(ctx));
        Log.d("User > ", "Img: " + this.Img);
        Log.d("User > ", "Birth: " + this.Birth.toString());
        Log.d("User > ", "House: " + new Character.House(this.House, ctx).Name);
        Log.d("User > ", "Patronous: " + this.Patronus);
    }

    private ContentValues getContentValues(Context ctx){
        ContentValues values = new ContentValues();
        values.put(PotterEntry.COLUMN_NAME_NAME, this.Name == null ? ctx.getString(R.string.user_default_name) : this.Name);
        values.put(PotterEntry.COLUMN_NAME_GENDER, this.Gender == null ? ctx.getString(R.string.user_default_gender) : this.Gender);
        values.put(PotterEntry.COLUMN_NAME_IMAGE_PATH, this.Img == null ? ctx.getString(R.string.user_default_img) : this.Img);

        try{
            values.put(PotterEntry.COLUMN_NAME_BIRTH_DATE, this.Birth.getTime());
        }catch(Exception e){
            values.put(PotterEntry.COLUMN_NAME_BIRTH_DATE, 0);
        }

        values.put(PotterEntry.COLUMN_NAME_HOUSE, this.House == null ? ctx.getString(R.string.user_default_house) : this.House);
        values.put(PotterEntry.COLUMN_NAME_PATRONUS, this.Patronus == null ? ctx.getString(R.string.user_default_patronus) : this.Patronus);

        return values;
    }

    // GETTERS

    public int getId() {
        return Id;
    }

    public String getGender(Context ctx) {
        return this.Gender.toLowerCase().equals("m")? ctx.getString(R.string.gender_m_name): ctx.getString(R.string.gender_f_name);
    }

    public String getPronoun(Context ctx) {
        return this.Gender.toLowerCase().equals("m")? ctx.getString(R.string.gender_m_pronoun): ctx.getString(R.string.gender_f_pronoun);
    }

    // SETTERS

    public enum GenderEnum {
        M("m"),F("f");

        public String value;
        GenderEnum(String v) {
            value = v;
        }
    }

    public void setGender(GenderEnum e){
        this.Gender = e.value;
    }
}
