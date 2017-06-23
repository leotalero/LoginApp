package com.android.leonardotalero.loginapp.utils;

/**
 * Created by leonardotalero on 6/21/17.
 */

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;



/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {
    //"http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]";
    final static String API_KEY="647f581dfe7f1faf564e531821cf1657";
    final static String BASE_URL_LOGIN ="https://jsandoval.000webhostapp.com/android_test/login.php";
    final static String BASE_URL_REGISTER ="https://jsandoval.000webhostapp.com/android_test/register.php";

   // final static  String BASE_URL_IMAGE="http://image.tmdb.org/t/p/w342/";

    final static String PARAM_API= "api_key";
    final static String PARAM_NAME="name";
    final static String PARAM_EMAIL="email";
    final static String PARAM_PASS="password";
    final static String PARAM_QUERY="query";

    /**
     * Builds the URL used to query GitHub.
     *
     * @param name The keyword that will be queried for.
     *pass
     * @return The URL to use to query .
     * movie/popular
     */
    public static URL buildUrl(String name,String pass) {

        Uri builtUri = Uri.parse(BASE_URL_LOGIN)
                .buildUpon()
                //.appendQueryParameter(PARAM_NAME, name)
                //.appendQueryParameter(PARAM_NAME, pass)
                //.appendQueryParameter(PARAM_PAGE,String.valueOf(page))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static URL buildUrlRegister(String name,String pass) {

        Uri builtUri = Uri.parse(BASE_URL_REGISTER)
                .buildUpon()
                //.appendQueryParameter(PARAM_NAME, name)
                //.appendQueryParameter(PARAM_NAME, pass)
                //.appendQueryParameter(PARAM_PAGE,String.valueOf(page))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


    public static String postResponseFromHttpUrl(URL url,String name,String pass) throws IOException {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("email", name);
        params.put("password", pass);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");


        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.getOutputStream().write(postDataBytes);
        try {


            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


    public static String postRegisterResponseFromHttpUrl(URL url,String name,String email,String pass) throws IOException {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", pass);


        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");


        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.getOutputStream().write(postDataBytes);
        try {


            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


    public static Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }
}
