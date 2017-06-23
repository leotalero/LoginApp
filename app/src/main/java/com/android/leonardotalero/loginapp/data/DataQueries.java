package com.android.leonardotalero.loginapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.android.leonardotalero.loginapp.utils.UserClass;

import static android.R.id.list;

/**
 * Created by leonardotalero on 6/23/17.
 */

public class DataQueries {



    public static void saveInDb(SQLiteDatabase db, UserClass user){

        ContentValues c=new ContentValues();
        c.put(LoginContract.LoginEntry.KEY_NAME, user.mName);
        c.put(LoginContract.LoginEntry.KEY_CREATED_AT, user.mCreated_at);
        c.put(LoginContract.LoginEntry.KEY_UPDATED_AT, user.mUpdated_at);
        c.put(LoginContract.LoginEntry.KEY_EMAIL, user.mMail);
        c.put(LoginContract.LoginEntry.KEY_UID, user.mUid);

        try
        {
            db.beginTransaction();
            //clear the table first

                db.insert(LoginContract.LoginEntry.TABLE_NAME, null, c);

            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }

    public static void removeData(SQLiteDatabase mDB){
        try
        {
            mDB.beginTransaction();
            //clear the table first
            mDB.delete (LoginContract.LoginEntry.TABLE_NAME,null,null);
            //go through the list and add one by one

            mDB.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            mDB.endTransaction();
        }
    }



}
