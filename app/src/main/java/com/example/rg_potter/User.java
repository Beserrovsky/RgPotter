package com.example.rg_potter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.Date;

import static com.example.rg_potter.PotterContract.PotterEntry;
import static com.example.rg_potter.PotterContract.PotterContractDbHelper;

public class User {

    private int Id;
    public String Name;
    public String Gender;
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

        Cursor c = db.query(
                PotterEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                ""               // The sort order
        );

        if (c.moveToFirst()){
            this.Id = c.getInt(0);
            this.Name = c.getString(1);
            this.Gender = c.getString(2);
            this.Img = c.getString(3);
            this.Birth = new Date(c.getInt(4));
            this.House = c.getString(5);
            this.Patronus = c.getString(6);
        }else{
            init();
        }

        c.close();
        db.close();
        dbHelper.close();
    }

    private void init(){
        // TODO: CREATE NEW ENTRY ON DATABASE IF IT DOES NOT EXISTS
    }

    public void save(Context ctx){

        PotterContractDbHelper dbHelper = new PotterContractDbHelper(ctx);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PotterEntry.COLUMN_NAME_NAME, this.Name);
        values.put(PotterEntry.COLUMN_NAME_GENDER, this.Gender);
        values.put(PotterEntry.COLUMN_NAME_IMAGE_PATH, this.Img);
        values.put(PotterEntry.COLUMN_NAME_BIRTH_DATE, this.Birth.getTime());
        values.put(PotterEntry.COLUMN_NAME_HOUSE, this.House);
        values.put(PotterEntry.COLUMN_NAME_PATRONUS, this.Patronus);

        String selection = PotterEntry._ID + "= ?";
        String[] selectionArgs = { "" + this.getId() };

        int count = db.update(
                PotterEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        db.close();
        dbHelper.close();
    }

    // GETTERS / SETTERS

    public int getId() {
        return Id;
    }
}
