package com.example.internship.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.internship.data.usercontract.UserEntry;

public class userDbhelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = userDbhelper.class.getSimpleName();


    private static final String DATABASE_NAME = "shelter.db";


    private static final int DATABASE_VERSION = 1;


    public userDbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_USERS_TABLE =  "CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserEntry.COLUMN_USER_NAME + " TEXT NOT NULL, "
                + UserEntry.COLUMN_USER_EMAIL + " TEXT, "
                + UserEntry.COLUMN_USER_GENDER + " INTEGER NOT NULL, "
                + UserEntry.COLUMN_USER_CREDIT + " INTEGER NOT NULL DEFAULT 100);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_USERS_TABLE);
        String SQL_CREATE_TRANSACTION_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME2 + "("
                + UserEntry.COLUMN_USER_FROMUSER + "INTEGER PRIMARY KEY, "
                +UserEntry.COLUMN_USER_TOUSER + "INTEGER NOT NULL,"
                +UserEntry.COLUMN_USER_CREDITAMOUNT+ "INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_TRANSACTION_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}