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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
	TextView page_title;
	TextView btn_edit,state;
	LinearLayout prdcheckout_btn;
	LinearLayout menu_btn,back_btn,submit_btn,proceedtopay_ll_btn;
	LinearLayout popup_checkout;

	ArrayList<StatesData> statesData = new ArrayList<StatesData>();

	StatesAdapter statesAdapter;

	EditText firstname,lastname,address,email,phone,city,pincode;
	ListView states_lv;

	String member,name;

	ImageView back;
	@Override
	public void onBackPressed() {
		super.onBackPressed();

		//Intent intent = new Intent(CheckoutActivity.this,HomeActivity.class);
		// startActivity(intent);
		finish();
	}
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);

		//popup
		popup_checkout = (LinearLayout)findViewById(R.id.popup_checkout);
		popup_checkout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popup_checkout.setVisibility(View.GONE);
			}
		});

		states_lv = (ListView)findViewById(R.id.lv_states);

		statesAdapter = new StatesAdapter(CheckoutActivity.this,statesData);

		states_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				popup_checkout.setVisibility(View.GONE);
				state.setText(statesData.get(position).states_);
			}
		});

		//stateslist
		callStatesListService();

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

		state = (TextView)findViewById(R.id.tv_state_checkout);

		state.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popup_checkout.setVisibility(View.VISIBLE);
			}
		});

/*
		statesData.add(new StatesData("app"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));
		statesData.add(new StatesData("ap"));*/

		states_lv.setAdapter(statesAdapter);
		statesAdapter.notifyDataSetChanged();


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
					intent.putExtra("scheme_Amt",getIntent().getStringExtra("schemeAmt_used"));

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

	public void callStatesListService(){


		final ProgressDialog progressDialog = new ProgressDialog(CheckoutActivity.this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/states.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("resStatesList",response);
				if(progressDialog!=null) {
					progressDialog.dismiss();
				}
				try {
					JSONArray jsonArray=new JSONArray(response);

					Log.e("status",""+jsonArray.length());
					if(jsonArray.length()>1) {

						for (int i = 0;i<jsonArray.length();i++){
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							Log.e("jsonobject",""+jsonObject);
							Log.e("jsonobjectLength",""+jsonObject.length());


							Log.e("states",""+jsonObject.getString("title"));


							StatesData temp = new StatesData(jsonObject);

							statesData.add(temp);



						}
						statesAdapter.notifyDataSetChanged();
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

				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
	}
}
