package com.yellowsoft.newproject;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sriven on 4/26/2018.
 */

public class MyOrdersData {
   String images = "";
    String price,order_id,quantity,address;

   // Context context;
    public MyOrdersData(String images, String price, String order_id, String quantity, String address){
        this.images=images;
        this.price=price;
        this.order_id = order_id;
        this.quantity = quantity;
        this.address = address;
        // this.context=context;
    }


    public MyOrdersData(JSONObject jsonObject){


        try {
            this.price=jsonObject.getString("price");
            this.order_id = jsonObject.getString("id");
            this.quantity = jsonObject.getString("price");
            this.address = jsonObject.getString("address")+", "+jsonObject.getString("city")+", "+jsonObject.getString("state");

            if(jsonObject.getJSONArray("products").length()>0) {

                this.images = jsonObject.getJSONArray("products").getJSONObject(0).getString("images");
                this.quantity = jsonObject.getJSONArray("products").getJSONObject(0).getString("quantity");

            }






        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

}
