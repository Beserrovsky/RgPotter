package com.example.rg_potter.entity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.Date;

import static com.example.rg_potter.data.PotterContract.PotterEntry;
import static com.example.rg_potter.data.PotterContract.PotterContractDbHelper;

public class User {

    private int Id;
    public String Name;
    public Date Birth;
    public String Patronus;

    private String House_id = "";
    private House House;
    private String Gender_id = "";
    private Gender Gender;

    // Getters
    public House getHouse(){ return this.House; }
    public String getHouseId(){ return this.House_id; }
    public Gender getGender(){ return this.Gender; }
    public int getId(){ return this.Id; }

    // Setters
    public void setHouse(String house_id){ this.House_id = house_id; this.House = new House(house_id); }
    public void setGender(String gender_id){ this.Gender_id = gender_id; this.Gender = new Gender(gender_id); }


    public User(int id, Context ctx){
        this.Id = id;

        PotterContractDbHelper dbHelper = new PotterContractDbHelper(ctx);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                PotterEntry.COLUMN_NAME_NAME,
                PotterEntry.COLUMN_GENDER_NAME,
                PotterEntry.COLUMN_HOUSE_NAME,
                PotterEntry.COLUMN_BIRTH_DATE_NAME,
                PotterEntry.COLUMN_PATRONUS_NAME,
        };

        String selection = PotterEntry._ID + " = ?";
        String[] selectionArgs = { "" + this.Id };

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
                this.setGender(c.getString(2));
                this.setHouse(c.getString(3));
                this.Birth = new Date(Long.parseLong(c.getString(4)));
                this.Patronus = c.getString(5);

                c.close();
            } else {

                c.close();
                init(ctx);
                retry = !retry; //SAFE TRIGGER FOR ONLY ONE RETRY
            }
        }while(retry);

        db.close();
        dbHelper.close();

        Log.d("User", "instance loaded from database:");
        log(ctx);
    }

    private void init(Context ctx){
        PotterContractDbHelper dbHelper = new PotterContractDbHelper(ctx);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = getContentValues();
        values.put(PotterEntry._ID, this.Id);

        db.insert(PotterEntry.TABLE_NAME, null, values);

        db.close();
        dbHelper.close();
    }

    public void save(Context ctx){

        PotterContractDbHelper dbHelper = new PotterContractDbHelper(ctx);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = getContentValues();

        String selection = PotterEntry._ID + "= ?";
        String[] selectionArgs = { "" + this.Id };

        db.update(
                PotterEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        db.close();
        dbHelper.close();

        Log.d("User", "instance saved into database");
        log(ctx);
    }

    public void log(Context ctx){
        Log.d("User", "Id: " + this.Id);
        Log.d("User", "Name: " + this.Name);
        Log.d("User", "Gender: " + ctx.getString(this.Gender.NameResource));
        Log.d("User", "House: " + ctx.getString(this.House.NameResource));
        Log.d("User", "Birth: " + this.Birth.toString());
        Log.d("User", "Patronous: " + this.Patronus);
    }

    private ContentValues getContentValues(){
        ContentValues values = new ContentValues();

        values.put(PotterEntry.COLUMN_NAME_NAME, this.Name);
        values.put(PotterEntry.COLUMN_GENDER_NAME, this.Gender_id);
        values.put(PotterEntry.COLUMN_HOUSE_NAME, this.House_id);

        try{
            values.put(PotterEntry.COLUMN_BIRTH_DATE_NAME, "" + this.Birth.getTime());
        }catch(Exception e){
            values.put(PotterEntry.COLUMN_BIRTH_DATE_NAME, "" + 0);
        }

        values.put(PotterEntry.COLUMN_PATRONUS_NAME, this.Patronus);

        return values;
    }
}
