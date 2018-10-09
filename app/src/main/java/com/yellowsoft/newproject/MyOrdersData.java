package com.yellowsoft.newproject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sriven on 4/26/2018.
 */

public class MyOrdersData {
   String images;
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

}
