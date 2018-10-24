package com.yellowsoft.newproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by mac on 7/2/18.
 */

public class Session {

    static String SESSION_ID="session_id";
    public static final String BASE_URL = "http://app.mycop.in/";
    static String DEVICE_ID="device_id";
    static String USER_ID="user_id";
    static String USER_mobile="user_mobile";
    public static final String USERNAME = "MYCOP USER";
    static String USER_IMAGE="user_image";


    public static void setSessionId(Context context, String session_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SESSION_ID, session_id);
        editor.apply();
    }



    public static String getSessionId(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(SESSION_ID, "0");
    }



    //member code (or) myreferal code
    public static void setMemberCode(Context context, String membercode) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("membercode", membercode);
        editor.apply();
    }



    public static String getMemberCode(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("membercode", "");
    }

    public static void setPrice(Context context, String price) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Price", price);
        editor.apply();
    }



    public static String getPrice(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("Price", "5000");
    }

    //shipping charges
    public static void setShippingCharge(Context context, String shippingcharge) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ShippingCharges", shippingcharge);
        editor.apply();
    }



    public static String getShippingCharge(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("ShippingCharges", "0");
    }

    //scheme amount
    public static void setSchemeAmt(Context context, String scheme_amt) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SchemeAmount", scheme_amt);
        editor.apply();
    }



    public static String getSchemeAmt(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("SchemeAmount", "2200");
    }


    //set total price
    public static void setTotalPrice(Context context, String totalPrice) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("totalPrice", totalPrice);
        editor.apply();
    }



    public static String getTotalPrice(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("totalPrice", "5000");
    }

    //set user id (or) memberid

    public static void setUserid(Context context, String member_id, String name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, member_id);
        editor.putString(USERNAME, name);
        editor.apply();

    }
    public static String getUserid(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_ID, "0");

    }
    public static String getUserName(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USERNAME, "MYCOP USER");
    }



    public static String getUSER_mobile(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_mobile, "Mobile");
    }



    public static void setUSER_mobile(Context context, String session_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_mobile, session_id);
        editor.apply();
    }



    public static String getUserImage(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_IMAGE, "Mobile");
    }



    public static void setUserImage(Context context, String session_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_IMAGE, session_id);
        editor.apply();
    }

    public static void logoutUser(Context context){
        // Clearing all data from Shared Preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.getString(USER_ID, "-1");
        sharedPreferences.getString(USERNAME, "user name");
        editor.clear();
        editor.commit();


    }



}
