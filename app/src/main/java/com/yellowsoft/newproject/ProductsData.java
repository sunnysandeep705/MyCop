package com.yellowsoft.newproject;



import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductsData  implements Serializable{
	String originalprice,discountprice,product_images,product_title,description,product_id;
	public  ArrayList<RequestImages> images;


	public ProductsData(String originalprice,String discountprice,String product_images,String product_title,String description,String product_id){
		this.discountprice=discountprice;
		this.originalprice=originalprice;
		this.product_images=product_images;
		this.description=description;
		this.product_id=product_id;
		this.product_title=product_title;
	}

	public ProductsData(JSONObject jsonObject){
		try {
			this.product_id=jsonObject.getString("id");
			this.product_title=jsonObject.getString("title");
			this.discountprice=jsonObject.getString("discount");
			this.originalprice=jsonObject.getString("price");
			this.description=jsonObject.getString("description");
			//this.product_images = jsonObject.getString("images");

			images = new ArrayList<>();


				for (int i=0;i<=jsonObject.getJSONArray("images").length();i++){
					String image  = jsonObject.getJSONArray("images").getString(i);
					Log.e("images",""+image);
					images.add(new RequestImages(image));
				}



		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
