package com.yellowsoft.newproject;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sriven on 6/1/2018.
 */

public class SlidingImage_Data {
	String name,description,id,image_url;

	/*public SlidingImage_Data(int image){
		this.image=image;
	}*/
	public SlidingImage_Data(String  image_url){

		this.image_url=image_url;


		//this.id = jsonObject.getString("id");
		//	this.name = jsonObject.getString("name");
		//	this.description = jsonObject.getString("desc");
	}
}
