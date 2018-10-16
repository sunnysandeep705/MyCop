package com.yellowsoft.newproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
	TextView quantity,page_title;
	TextView btn_edit,logout,signup_btn;
	LinearLayout prdcheckout_btn;
	LinearLayout menu_btn,back_btn,submit_btn,proceedtopay_ll_btn;

	EditText firstname,lastname,address,email,phone,city,pincode,state;

	String member,name;

	ImageView back;
	@Override
	public void onBackPressed() {
		super.onBackPressed();

		Intent intent = new Intent(CheckoutActivity.this,HomeActivity.class);
		startActivity(intent);
		finish();
	}
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);

		member = Session.getUserid(CheckoutActivity.this);
		Log.e("memberid",""+member);
		name = Session.getUserName(CheckoutActivity.this);
		Log.e("alias",""+name);

		firstname = (EditText)findViewById(R.id.et_firstname_checkout);
		lastname = (EditText)findViewById(R.id.et_lastname_checkout);
		address = (EditText)findViewById(R.id.et_address_checkout);
		email = (EditText)findViewById(R.id.et_email_checkout);
		phone = (EditText)findViewById(R.id.et_phone_checkout);
		city = (EditText)findViewById(R.id.et_city_checkout);
		pincode = (EditText)findViewById(R.id.et_pincode_checkout);

		state = (EditText)findViewById(R.id.et_state_checkout);




		Toolbar toolbar = (Toolbar)findViewById(R.id.checkout_toolBar);
		proceedtopay_ll_btn = (LinearLayout)findViewById(R.id.proceedtopay_ll_btn);
		proceedtopay_ll_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (firstname.getText().toString().equals("")){
					Snackbar.make(firstname,"please enter firstname",Snackbar.LENGTH_SHORT).show();
				}
				else if (lastname.getText().toString().equals("")){
					Snackbar.make(firstname,"please enter lastname",Snackbar.LENGTH_SHORT).show();
				}
				else if(address.getText().toString().equals("")){
					Snackbar.make(firstname,"please enter address",Snackbar.LENGTH_SHORT).show();
				}
				else if(email.getText().toString().equals("")){
					Snackbar.make(firstname,"please enter email",Snackbar.LENGTH_SHORT).show();
				}
				else if(phone.getText().toString().equals("")){
					Snackbar.make(firstname,"please enter phonenumber",Snackbar.LENGTH_SHORT).show();
				}
				else if(city.getText().toString().equals("")){
					Snackbar.make(firstname,"please enter city",Snackbar.LENGTH_SHORT).show();
				}
				else if(pincode.getText().toString().equals("")){
					Snackbar.make(firstname,"please enter pincode",Snackbar.LENGTH_SHORT).show();
				}
				/*else if(country.getText().toString().equals("")){
					Snackbar.make(firstname,"please enter countryname",Snackbar.LENGTH_SHORT).show();
				}*/
				else if(state.getText().toString().equals("")){
					Snackbar.make(firstname,"please enter state",Snackbar.LENGTH_SHORT).show();
				}
				else {
					Intent intent = new Intent(CheckoutActivity.this,PaymentActivity.class);


					intent.putExtra("product",getIntent().getSerializableExtra("product"));
					intent.putExtra("total_price",getIntent().getIntExtra("total_price",0)) ;
					intent.putExtra("price",getIntent().getIntExtra("price",0));
					intent.putExtra("delivery_charges",getIntent().getIntExtra("delivery_charges",0));
					intent.putExtra("discount_amount",getIntent().getIntExtra("discount_amount",0));

					intent.putExtra("firstname",firstname.getText().toString());
					intent.putExtra("lastname",lastname.getText().toString());
					intent.putExtra("email",email.getText().toString());
					intent.putExtra("address",address.getText().toString());
					intent.putExtra("phone",phone.getText().toString());
					intent.putExtra("city",city.getText().toString());
					intent.putExtra("pincode",pincode.getText().toString());
					intent.putExtra("state",state.getText().toString());

					startActivity(intent);
					//finish();

					//sendShippingAddress();
				}

				//CallAddAddressService(member,name,address.getText().toString(),city.getText().toString(),state.getText().toString(),country.getText().toString(),pincode.getText().toString(),phone.getText().toString());
			}
		});
		setSupportActionBar(toolbar);
		setupActionBar();
		setupHeader();

	}
	public void sendShippingAddress(){

	}


	//set action bar
	private void setupActionBar() {

		getSupportActionBar().setHomeButtonEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.MATCH_PARENT);
		LayoutInflater inflater = getLayoutInflater();
		View v = inflater.inflate(R.layout.action_bar_login,null);

		page_title = (TextView) v.findViewById(R.id.page_title);
		back_btn = (LinearLayout)v.findViewById(R.id.btn_back_container);

		back = (ImageView)v.findViewById(R.id.btn_back);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		menu_btn = (LinearLayout) v.findViewById(R.id.btn_menu_container);

		getSupportActionBar().setCustomView(v, layoutParams);
		Toolbar parent = (Toolbar) v.getParent();

		parent.setContentInsetsAbsolute(0, 0);


	}
	private void setupHeader(){
		page_title.setText("CHECKOUT");
		//btn_edit.setVisibility(View.VISIBLE);
		//btn_edit.setText("Search");
		//page_title.setText("Home");
	}
	//api/add-address.php

	public void CallAddAddressService(final String memberid, final String alias,final String address,final String city,final String state,final String country,final String pincode,final String phone){


		final ProgressDialog progressDialog = new ProgressDialog(CheckoutActivity.this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/add-address.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("resCartActivity",response);
				if(progressDialog!=null) {
					progressDialog.dismiss();
				}
				try {
					JSONObject jsonObject=new JSONObject(response);
					String reply=jsonObject.getString("status");
					Log.e("status",""+reply);
					if(reply.equals("Success")) {

						/*//Snackbar.make(discount_tv,"Coupon code applied successfully",Snackbar.LENGTH_SHORT).show();
						String discount = jsonObject.getString("discount_value");
						Log.e("discount",""+discount);
						discount_tv.setText(discount);
						int total = subtotal-Integer.parseInt(discount);
						Log.e("subtotal",""+subtotal);
						total_tv.setText(""+total);
						Log.e("total",""+total);

						Session.setTotalPrice(CheckoutActivity.this,""+total);*/
					}


				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if(progressDialog!=null)
							progressDialog.dismiss();
						//Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
					}
				}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> parameters = new HashMap<String, String>();

				parameters.put("member_id",memberid);
				parameters.put("alias",alias);
				parameters.put("address",address);
				parameters.put("city",city);
				parameters.put("state",state);
				parameters.put("country",country);
				parameters.put("pincode",pincode);
				parameters.put("phone",phone);
				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
	}
}
