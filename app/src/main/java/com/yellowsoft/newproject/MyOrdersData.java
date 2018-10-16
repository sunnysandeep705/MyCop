package com.yellowsoft.newproject;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by sriven on 4/26/2018.
 */

public class MyOrdersData {
    String images = "";
    String price, order_id, quantity, address, tittle, status, date, tracking_link;


    // Context context;
    public MyOrdersData(String images, String price, String order_id, String quantity, String address) {
        this.images = images;
        this.price = price;
        this.order_id = order_id;
        this.quantity = quantity;
        this.address = address;
        // this.context=context;
    }


    public MyOrdersData(JSONObject jsonObject) {


        try {
            this.price = jsonObject.getString("total_price");
            this.order_id = jsonObject.getString("id");


            this.address = jsonObject.getString("address") + ", " + jsonObject.getString("city") + ", " + jsonObject.getString("state");


            this.date = jsonObject.getString("date");

            this.tracking_link = jsonObject.getString("track");

            this.date = getTimeStamp(this.date);

            if (jsonObject.getJSONArray("products").length() > 0) {

                this.images = jsonObject.getJSONArray("products").getJSONObject(0).getString("images");
                this.quantity = jsonObject.getJSONArray("products").getJSONObject(0).getString("quantity");
                this.tittle = jsonObject.getJSONArray("products").getJSONObject(0).getString("title");

            }


            if (jsonObject.getJSONArray("history").length() > 0) {

                this.status = jsonObject.getJSONArray("history").getJSONObject(0).getString("message");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public String getTimeStamp(String str_time_date) {


        try {
            SimpleDateFormat fromFormater = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.ENGLISH);
            Date fromDate = fromFormater.parse(str_time_date);

            SimpleDateFormat curFormater = new SimpleDateFormat(" dd MMM yyyy", Locale.ENGLISH);
            String date = curFormater.format(fromDate);

            return date;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return str_time_date;

    }
}

