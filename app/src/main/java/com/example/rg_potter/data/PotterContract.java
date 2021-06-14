package com.example.rg_potter.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

final public class PotterContract {

    private PotterContract(){} // Non-instantiating Class


    /* Inner class that defines the table contents */
    public static class PotterEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_GENDER_NAME = "gender_id";
        public static final String COLUMN_HOUSE_NAME = "house_id";
        public static final String COLUMN_BIRTH_DATE_NAME = "birth";
        public static final String COLUMN_PATRONUS_NAME = "patronus";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PotterEntry.TABLE_NAME + " (" +
                    PotterEntry._ID                      + " INTEGER PRIMARY KEY," +
                    PotterEntry.COLUMN_NAME_NAME         + " TEXT," +
                    PotterEntry.COLUMN_GENDER_NAME       + " TEXT," +
                    PotterEntry.COLUMN_HOUSE_NAME        + " TEXT," +
                    PotterEntry.COLUMN_BIRTH_DATE_NAME   + " TEXT," +
                    PotterEntry.COLUMN_PATRONUS_NAME     + " TEXT"  +
                    ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PotterEntry.TABLE_NAME;

    public static class PotterContractDbHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 3;
        public static final String DATABASE_NAME = "Potter.db";

        public PotterContractDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // On database upgrade, data is gone :(
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

}
