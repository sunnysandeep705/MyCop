package com.yellowsoft.newproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class MyreferalsActivity extends AppCompatActivity {
	RecyclerView myreferals;

	MyReferals_Adapter myReferals_adapter;

	ArrayList<MyReferalsData> myOrdersData=new ArrayList<MyReferalsData>();

	LinearLayout transaction_details_ll,no_referals_ll,myearnings_ll;

	TextView page_title;
	ImageView back;
	LinearLayout back_btn,menu_btn;
	TextView tv_ref_code;

	String playstorelink;

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	/*	Intent intent = new Intent(MyreferalsActivity.this,HomeActivity.class);
		startActivity(intent);*/
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activtiy_myreferals);
		myreferals =(RecyclerView)findViewById(R.id.referals_recycler);

		transaction_details_ll = (LinearLayout)findViewById(R.id.transaction_details_ll);
		myearnings_ll = (LinearLayout)findViewById(R.id.earnings_ll_myreferrals);

		myearnings_ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyreferalsActivity.this,MyearningsActivity.class);
				startActivity(intent);
			}
		});

		no_referals_ll = (LinearLayout)findViewById(R.id.no_referrals_ll);

		myReferals_adapter = new MyReferals_Adapter(MyreferalsActivity.this,myOrdersData);
		LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MyreferalsActivity.this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

		myreferals.setLayoutManager(linearLayoutManager);
		myreferals.setAdapter(myReferals_adapter);

		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_myreferals);
		setSupportActionBar(toolbar);
		setupActionBar();
		setupHeader();


		tv_ref_code = (TextView) findViewById(R.id.tv_myreferalcode);
		tv_ref_code.setText(Session.getMemberCode(this));

		try {
			playstorelink = ApplicationController.getInstance().settings.getString("playstore");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		tv_ref_code.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//String shareBody = "Hi, I am "+Session.getUserName(MyreferalsActivity.this)+", use my referral code:"+ Session.getMemberCode(MyreferalsActivity.this);
				String shareBody = "Hi, I am "+Session.getUserName(MyreferalsActivity.this)+", join me on My Cop App and register in their Purchase Advance Scheme to get your Referral Code. Enter my code ("+ Session.getMemberCode(MyreferalsActivity.this)+") before making the payment. You can earn Rs. 1000/- for every successful referral." +playstorelink;

				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
				sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share with");
				startActivity(sharingIntent);
			}
		});

		if (Session.getMemberCode(MyreferalsActivity.this).equals("")){
			transaction_details_ll.setVisibility(View.GONE);
			no_referals_ll.setVisibility(View.VISIBLE);

		}
		else {
			CallReferaldetails();
		}

		//CallReferaldetails();


	}


	private void setupActionBar() {
//set action bar
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
				/*Intent intent = new Intent(MyreferalsActivity.this,HomeActivity.class);
				startActivity(intent);*/
				finish();
			}
		});

		menu_btn = (LinearLayout) v.findViewById(R.id.btn_menu_container);

		getSupportActionBar().setCustomView(v, layoutParams);
		Toolbar parent = (Toolbar) v.getParent();

		parent.setContentInsetsAbsolute(0, 0);


	}

	private void setupHeader(){
		page_title.setText("MY REFERRALS");
		//btn_edit.setVisibility(View.VISIBLE);
		//btn_edit.setText("Search");
		//page_title.setText("Home");
	}




	public void CallReferaldetails(){

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/members_referrals.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("res",response);
				Log.e("responcelength",""+response.length());
				if(progressDialog!=null&& progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				try {

					JSONArray jsonArray = new JSONArray(response);
					Log.e("jsonArray",""+jsonArray.toString());
					Log.e("jsonarraylength",""+jsonArray.length());

					if (jsonArray.length()<1){
						transaction_details_ll.setVisibility(View.GONE);
						no_referals_ll.setVisibility(View.VISIBLE);

					}
					else {
						transaction_details_ll.setVisibility(View.VISIBLE);
						no_referals_ll.setVisibility(View.GONE);
					}


					myOrdersData.clear();

					myReferals_adapter.notifyDataSetChanged();


					for (int i = 0;i<jsonArray.length();i++){
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						Log.e("jsonobject",""+jsonObject);
						Log.e("jsonobjectLength",""+jsonObject.length());


						MyReferalsData temp = new MyReferalsData(jsonObject);

						myOrdersData.add(temp);

					}
					myReferals_adapter.notifyDataSetChanged();

					//Log.e("jsonobject",""+jsonArray.getJSONObject())

					//JSONObject jsonObject = new JSONObject(jsonArray.getJSONObject(0));


					//	Log.e("imagessssss",""+jsonArray1.getJSONObject(0).getString("image"));

					/*if (jsonArray1.length()>1){
						Log.e("length","length");
						for (int j=0;j<=jsonArray1.length();j++){
							//slidingImage_data.add(new SlidingImage_Data(jsonArray.getJSONObject(j).getString("image")));
							String s = jsonArray1.getString(j);
							Log.e("s",""+s);
							slidingImage_data.add(new SlidingImage_Data(s));
							Log.e("imagessssss",""+jsonArray1.getString(j));

						}

					}*/


				} catch (JSONException e) {
					e.printStackTrace();
				}
				//	slidingPageAdapter.notifyDataSetChanged();
			}
		},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("error",""+error);
						if(progressDialog!=null)
							progressDialog.dismiss();
						//Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
					}
				}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> parameters = new HashMap<String, String>();
				//parameters.put("email",u_name.getText().toString());
				//	parameters.put("password",password.getText().toString());

				parameters.put("member_code",Session.getMemberCode(MyreferalsActivity.this));

				//parameters.put("member_code","MYC18100001");


				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
//		slidingPageAdapter.notifyDataSetChanged();
	}

}
