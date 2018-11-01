package com.yellowsoft.newproject;

import org.json.JSONException;
import org.json.JSONObject;

public class MyReferalsData {

String referalid,status,transdetails,date;
String referredby;

		public MyReferalsData(String referalid,String status,String transdetails,String referredby){
				this.referalid = referalid;
				this.status = status;
				this.transdetails = transdetails;
				this.referredby = referredby;
				}



	public MyReferalsData(JSONObject jsonObject) {


		try {
			this.referalid = jsonObject.getString("id");
			this.status = jsonObject.getString("status");
			this.transdetails = jsonObject.getString("transaction");//transaction
			this.date = jsonObject.getString("date");
			this.referredby = jsonObject.getString("name");


		} catch (JSONException e) {
			e.printStackTrace();
		}


	}

}
