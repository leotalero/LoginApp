package com.android.leonardotalero.loginapp.utils;

import android.content.Context;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by leonardotalero on 6/21/17.
 */

public class UserJsonUtils {


    public static UserClass getSimpleStringsFromJson(Context context, String jsonStr)
            throws JSONException {

        final String OWM_MESSAGE_CODE="error";
        final String OWM_USER_NAME = "name";
        final String OWM_MAIL="email";
        final String OWM_CREATED = "created_at";
        final String OWM_UPDATED = "updated_at";
        final String OWM_RESULTS="user";
        final String OWM_MSG="error_msg";
        /* String array to hold each day's weather String */
        //List<MovieClass> parsedData=new ArrayList<MovieClass>();



        JSONObject Json = new JSONObject(jsonStr);

        /* Is there an error? */
        if (Json.has(OWM_MESSAGE_CODE)) {
            String errorCode = Json.getString(OWM_MESSAGE_CODE);


            switch (errorCode) {
                case "false":
                    break;
                case "true":
                    /* error */
                    String errorMessage = Json.getString(OWM_MSG);
                    UserClass user=new UserClass(errorMessage);
                    return   user;
                default:
                    /* Server probably down */
                    return null;
            }
        }
        String uid = Json.getString("uid");


        JSONObject user_json = Json.getJSONObject(OWM_RESULTS);
            String name;
            String email;
            String created_at;
            String updated_at;

            /* Get the JSON object representing the day */




        name = user_json.getString(OWM_USER_NAME);
        email = user_json.getString(OWM_MAIL);
        created_at=user_json.getString(OWM_CREATED);
        updated_at=user_json.getString(OWM_UPDATED);
        UserClass user=new UserClass(uid,email,name,created_at,updated_at);


        return user;
    }





}
