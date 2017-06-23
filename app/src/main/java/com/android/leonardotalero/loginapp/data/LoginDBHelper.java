package com.android.leonardotalero.loginapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.leonardotalero.loginapp.data.LoginContract.LoginEntry;

/**
 * Created by leonardotalero on 6/23/17.
 */

public class LoginDBHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "android_test.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 3;


    // Constructor
    public LoginDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold waitlist data
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + LoginEntry.TABLE_NAME + " (" +
                LoginEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LoginEntry.KEY_ID + " INTEGER  NULL, " +
                LoginEntry.KEY_NAME + " TEXT NOT NULL, " +
                LoginEntry.KEY_EMAIL + " TEXT NOT NULL, " +
                LoginEntry.KEY_UID + " TEXT NOT NULL, " +
                LoginEntry.KEY_CREATED_AT + " TIMESTAMP NOT NULL, " +
                LoginEntry.KEY_UPDATED_AT + " TIMESTAMP  NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LoginEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}