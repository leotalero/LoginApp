package com.android.leonardotalero.loginapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.leonardotalero.loginapp.data.LoginDBHelper;
import com.android.leonardotalero.loginapp.utils.NetworkUtils;
import com.android.leonardotalero.loginapp.utils.UserClass;
import com.android.leonardotalero.loginapp.utils.UserJsonUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static android.R.attr.duration;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<UserClass> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    private static final int LOADER_ID =30 ;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    // private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mNameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private URL SearchUrl;
    private TextView mErrorMessageDisplay;
    private Boolean flagLoadingData=false;
    private SQLiteDatabase mDB;
    private String mEmail;
    private String mPass;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email_register);
        mNameView=(AutoCompleteTextView) findViewById(R.id.name_register);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display_register);

        mPasswordView = (EditText) findViewById(R.id.password_register);


        Button mEmailSignInButton = (Button) findViewById(R.id.email_register_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
        int loaderId = LOADER_ID;
        LoginDBHelper dbHelper = new LoginDBHelper(this);

        mDB = dbHelper.getWritableDatabase();

    }





    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (flagLoadingData) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        mEmail = mEmailView.getText().toString();
        mPass = mPasswordView.getText().toString();
        mName =mNameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(mPass) && !isPasswordValid(mPass)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(mEmail)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(mName)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {


            searchQuery(mEmail,mPass);
            // mAuthTask = new UserLoginTask(email, password);
            // mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }



    private void searchQuery(String name,String pass) {

        SearchUrl = NetworkUtils.buildUrlRegister(name.toString(),pass.toString());
        Bundle queryBundle = new Bundle();
        LoaderManager loaderManager = getSupportLoaderManager();
        android.support.v4.content.Loader<UserClass> SearchLoader = loaderManager.getLoader(LOADER_ID);
        if (SearchLoader == null) {
            loaderManager.initLoader(LOADER_ID, queryBundle, this);
        } else {
            loaderManager.restartLoader(LOADER_ID, queryBundle, this);
        }

        //new QueryTask().execute(SearchUrl);


    }

    @Override
    public android.support.v4.content.Loader<UserClass> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<UserClass>(this) {

            public UserClass mDataCache = null;
            @Override
            protected void onStartLoading() {
                flagLoadingData=true;
                if (mDataCache != null) {
                    deliverResult(mDataCache);
                } else {
                    mProgressView.setVisibility(View.VISIBLE);
                    forceLoad();
                }

            }

            @Override
            public UserClass loadInBackground() {

                try {

                    if(NetworkUtils.isOnline()){
                        String jsonWeatherResponse = NetworkUtils
                                .postRegisterResponseFromHttpUrl(SearchUrl,mName,mEmail,mPass);

                        UserClass UserData = UserJsonUtils
                                .getSimpleStringsFromJson(RegisterActivity.this, jsonWeatherResponse);

                        return UserData;
                    }else{
                        return null;
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }



            @Override
            public void deliverResult(UserClass data) {
                mDataCache = data;
                super.deliverResult(data);
            }
        };

    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<UserClass> loader, UserClass data) {

        //smProgressView.setVisibility(View.INVISIBLE);
        mProgressView.setVisibility(View.GONE);
        if (data != null && !data.equals("") && data.mError==null) {

            showJsonDataView();

            goToMainActivity(data);

        } else {
            if(data != null){
                showErrorMessage(data.mError);
            }else{
                showErrorMessage("");
            }

        }
        flagLoadingData=false;
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<UserClass> loader) {

    }




    private void showJsonDataView() {
        // First, make sure the error is invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

    }

    private void showErrorMessage(String error) {
        // First, hide the currently visible data
        //mSearchResultsGridView.setVisibility(View.INVISIBLE);
        // Then, show the error
        if(error!=null && !error.equals("")){
            mErrorMessageDisplay.setText(error);
            showToastMessage(this,error);
        }

        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        //mErrorMessageDisplay.setVisibility(View.GONE);

    }

    private void showToastMessage(Context context, String error){
        Toast toast = Toast.makeText(context, error, Toast.LENGTH_LONG);
        toast.show();
    }

    private void goToMainActivity(UserClass user){
        if(user!=null){
            Intent intentToStartDetailActivity = new Intent(RegisterActivity.this, Main2Activity.class);

            intentToStartDetailActivity.putExtra("UserObject" , (Parcelable) user);
            startActivity(intentToStartDetailActivity);
        }

    }



}

