package com.yellowsoft.newproject;

import org.json.JSONException;
import org.json.JSONObject;

public class MyReferalsData {

String referalid,status,transdetails,date;

		public MyReferalsData(String referalid,String status,String transdetails){
				this.referalid = referalid;
				this.status = status;
				this.transdetails = transdetails;
				}



	public MyReferalsData(JSONObject jsonObject) {


		try {
			this.referalid = jsonObject.getString("id");
			this.status = jsonObject.getString("status");
			this.transdetails = jsonObject.getString("transaction");
			this.date = jsonObject.getString("date");


		} catch (JSONException e) {
			e.printStackTrace();
		}


	}

}
