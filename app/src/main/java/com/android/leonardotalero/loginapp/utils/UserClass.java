package com.android.leonardotalero.loginapp.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by leonardotalero on 6/21/17.
 */

public class UserClass implements Parcelable {
    public String mUid;
    public String mMail;
    public String mName;
    public String mCreated_at;
    public String mUpdated_at;
    public String mError;



    public UserClass(String mUid, String mMail, String mName, String mCreated_at, String mUpdated_at) {
        this.mUid = mUid;
        this.mMail = mMail;
        this.mName = mName;
        this.mCreated_at = mCreated_at;
        this.mUpdated_at = mUpdated_at;
    }

    public UserClass() {
    }

    public UserClass(String mError) {
        this.mError = mError;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mUid);
        dest.writeString(this.mMail);
        dest.writeString(this.mName);
        dest.writeString(this.mCreated_at);
        dest.writeString(this.mUpdated_at);
        dest.writeString(this.mError);
    }

    protected UserClass(Parcel in) {
        this.mUid = in.readString();
        this.mMail = in.readString();
        this.mName = in.readString();
        this.mCreated_at = in.readString();
        this.mUpdated_at = in.readString();
        this.mError = in.readString();
    }

    public static final Creator<UserClass> CREATOR = new Creator<UserClass>() {
        @Override
        public UserClass createFromParcel(Parcel source) {
            return new UserClass(source);
        }

        @Override
        public UserClass[] newArray(int size) {
            return new UserClass[size];
        }
    };

}
