package com.example.rg_potter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

final public class PotterContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private PotterContract() {}

    /* Inner class that defines the table contents */
    public static class PotterEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_GENDER = "gender";
        public static final String COLUMN_NAME_IMAGE_PATH = "img";
        public static final String COLUMN_NAME_BIRTH_DATE = "birth";
        public static final String COLUMN_NAME_HOUSE = "house";
        public static final String COLUMN_NAME_PATRONUS = "patronus";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PotterEntry.TABLE_NAME + " (" +
                    PotterEntry._ID + " INTEGER PRIMARY KEY," +
                    PotterEntry.COLUMN_NAME_NAME + " TEXT," +
                    PotterEntry.COLUMN_NAME_GENDER + " TEXT," +
                    PotterEntry.COLUMN_NAME_IMAGE_PATH + " TEXT," +
                    PotterEntry.COLUMN_NAME_BIRTH_DATE + " INTEGER," +
                    PotterEntry.COLUMN_NAME_HOUSE + " TEXT," +
                    PotterEntry.COLUMN_NAME_PATRONUS+ " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PotterEntry.TABLE_NAME;

    public static class PotterContractDbHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Potter.db";

        public PotterContractDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

}
