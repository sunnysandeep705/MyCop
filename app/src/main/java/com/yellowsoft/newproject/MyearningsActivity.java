package com.yellowsoft.newproject;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/*

 myer means myearnings

*/

public class MyearningsActivity extends AppCompatActivity {
	ImageView close;
	LinearLayout popup;
	TextView page_title,membercode_myearnings;
	ImageView back;
	LinearLayout back_btn,menu_btn,notes_ll;

	TextView notes_tv,schemeamt_myearnings;
	String playstorelink;

	TextView referal_sucess_tv_myer,referal_comm_tv_myer,inprogress_tv,deposited_amt_tv,tearnings_myer,tv_status,tv_status_des;
	LinearLayout ll_buy_gps,ll_refund;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myearnings);

		schemeamt_myearnings = (TextView)findViewById(R.id.schemeamt_myearnings);
		try {
			schemeamt_myearnings.setText(ApplicationController.getInstance().settings.getString("scheme_amount"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		close = (ImageView)findViewById(R.id.close_img_myer);
		popup = (LinearLayout)findViewById(R.id.popup_myer);

		notes_ll = (LinearLayout)findViewById(R.id.notes_ll);
		notes_tv = (TextView) findViewById(R.id.notes_tv);

		membercode_myearnings = (TextView)findViewById(R.id.membercode_myearnings);
		membercode_myearnings.setText(Session.getMemberCode(MyearningsActivity.this));

		try {
			playstorelink = ApplicationController.getInstance().settings.getString("playstore");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		membercode_myearnings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//String shareBody = "Hi, I am "+Session.getUserName(MyearningsActivity.this)+", use my referral code: "+""+ Session.getMemberCode(MyearningsActivity.this);
				String shareBody = "Hi, I am "+Session.getUserName(MyearningsActivity.this)+", join me on My Cop App and register in their Purchase Advance Scheme to get your Referral Code. Enter my code ("+ Session.getMemberCode(MyearningsActivity.this)+") before making the payment. You can earn Rs. 1000/- for every successful referral." +playstorelink;

				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
				sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share with");
				startActivity(sharingIntent);

			}
		});

		popup.setVisibility(View.GONE);

		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popup.setVisibility(View.GONE);
			}
		});

		String membercode = Session.getMemberCode(MyearningsActivity.this);
		if (membercode.equals("")){
			//popup.setVisibility(View.VISIBLE);
			/*tv_status.setText("Pending");
			tv_status_des.setText("(You are almost done! Refer two more member and get cash directly to your account or Buy GPS Tracker.)");
			ll_buy_gps.setVisibility(View.GONE);
			ll_refund.setVisibility(View.GONE);*/
		}

		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_myearnings);
		setSupportActionBar(toolbar);
		setupActionBar();
		setupHeader();

		referal_sucess_tv_myer = (TextView) findViewById(R.id.referal_sucess_tv_myer);
		referal_comm_tv_myer = (TextView) findViewById(R.id.referal_comm_tv_myer);
		inprogress_tv = (TextView) findViewById(R.id.inprogress_tv);
		deposited_amt_tv = (TextView) findViewById(R.id.deposited_amt_tv);
		tearnings_myer = (TextView) findViewById(R.id.tearnings_myer);
		tv_status = (TextView) findViewById(R.id.tv_statud);

		tv_status_des = (TextView) findViewById(R.id.tv_status_desc);

		ll_buy_gps = (LinearLayout) findViewById(R.id.ll_buy_gps);
		ll_refund = (LinearLayout) findViewById(R.id.ll_refund);


		ll_buy_gps.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intent = new Intent(MyearningsActivity.this,HomeActivity.class);
				//startActivity(intent);
				setResult(RESULT_OK,intent);
				finish();

			}
		});


		ll_refund.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				callRefundAPI();

			}
		});


		CallEarnService();

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


				finish();
			}
		});

		menu_btn = (LinearLayout) v.findViewById(R.id.btn_menu_container);

		getSupportActionBar().setCustomView(v, layoutParams);
		Toolbar parent = (Toolbar) v.getParent();

		parent.setContentInsetsAbsolute(0, 0);


	}
	private void setupHeader(){
		page_title.setText("MY EARNINGS");
		//btn_edit.setVisibility(View.VISIBLE);
		//btn_edit.setText("Search");
		//page_title.setText("Home");
	}


	public void CallEarnService(){

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/earnings.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("res",response);

				if(progressDialog!=null) {
					progressDialog.dismiss();
				}
				try {
					JSONObject jsonObject=new JSONObject(response);




						referal_sucess_tv_myer.setText(jsonObject.getString("total_referrals"));
						referal_comm_tv_myer.setText(jsonObject.getString("total_amount"));

						inprogress_tv.setText(jsonObject.getString("pending_amount"));
						deposited_amt_tv.setText(jsonObject.getString("deposited_amount"));

						int total_ref = Integer.parseInt(jsonObject.getString("total_referrals"));
						if(total_ref==0){

							tv_status.setText("Pending");
							tv_status_des.setText("(You are almost done! Refer two more member and get cash directly to your account or Buy GPS Tracker.)");
							ll_buy_gps.setVisibility(View.GONE);
							ll_refund.setVisibility(View.GONE);

						}else if(total_ref==1){
							tv_status.setText("Pending");
							tv_status_des.setText("(You are almost done! Refer one more member and get cash directly to your account or Buy GPS Tracker.)");
							ll_buy_gps.setVisibility(View.GONE);
							ll_refund.setVisibility(View.GONE);

						}else {
							if (jsonObject.getString("scheme_amount_used").equals("1")) {
								ll_buy_gps.setVisibility(View.GONE);
								ll_refund.setVisibility(View.GONE);
								//note2
								notes_ll.setVisibility(View.VISIBLE);
								notes_tv.setText(jsonObject.getString("note2"));
								tv_status.setText("Completed");
								tv_status_des.setText("(You have used your investment fund)");

							} else {

								tv_status.setText("Processing");
								tv_status_des.setText("(Now you can claim Cash /Buy GPS Tracker)");
								ll_buy_gps.setVisibility(View.VISIBLE);

								//note1
								notes_ll.setVisibility(View.VISIBLE);
								notes_tv.setText(jsonObject.getString("note1"));
								ll_refund.setVisibility(View.VISIBLE);
							}
						}
						if (jsonObject.getString("scheme_amount_used").equals("1")){
							tv_status.setText("Completed");
							tv_status_des.setText("(You have used your investment fund)");
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
						Toast.makeText(MyearningsActivity.this,"Network error",Toast.LENGTH_SHORT).show();
					}
				}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> parameters = new HashMap<String, String>();

				parameters.put("member_code",Session.getMemberCode(MyearningsActivity.this));

				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
	}




	//refund api
	private void callRefundAPI(){

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/refund.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("res",response);

				if(progressDialog!=null) {
					progressDialog.dismiss();
				}
				try {
					JSONObject jsonObject=new JSONObject(response);
					if(jsonObject.equals("Success")) {

						Toast.makeText(MyearningsActivity.this,""+jsonObject.getString("message"),Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(MyearningsActivity.this,""+jsonObject.getString("message"),Toast.LENGTH_LONG).show();

					}

				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(MyearningsActivity.this,""+e.getMessage().toString(),Toast.LENGTH_LONG).show();

				}
			}
		},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if(progressDialog!=null)
							progressDialog.dismiss();
						Toast.makeText(MyearningsActivity.this,"Internet error",Toast.LENGTH_LONG).show();

					}
				}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> parameters = new HashMap<String, String>();

				parameters.put("member_id",Session.getUserid(MyearningsActivity.this));

				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);

	}




}
