package com.yellowsoft.newproject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by mac on 7/16/18.
 */

public class RequestImages implements Serializable {

   public String image_id;
    public String image_url;


    RequestImages(String image_url)  {

        this.image_url=image_url;


    }


}
