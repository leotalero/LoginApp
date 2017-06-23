package com.android.leonardotalero.loginapp.data;

/**
 * Created by leonardotalero on 6/23/17.
 */

import android.provider.BaseColumns;



public class LoginContract {

    public static final class LoginEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_EMAIL = "email";
        public static final String KEY_UID = "uid";
        public static final String KEY_CREATED_AT = "created_at";
        public static final String KEY_UPDATED_AT = "updated_at";
    }

}
