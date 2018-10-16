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
	LinearLayout back_btn,menu_btn;

	TextView referal_sucess_tv_myer,referal_comm_tv_myer,inprogress_tv,deposited_amt_tv,tearnings_myer,tv_status,tv_status_des;
	LinearLayout ll_buy_gps,ll_refund;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myearnings);

		close = (ImageView)findViewById(R.id.close_img_myer);
		popup = (LinearLayout)findViewById(R.id.popup_myer);

		membercode_myearnings = (TextView)findViewById(R.id.membercode_myearnings);
		membercode_myearnings.setText(Session.getMemberCode(MyearningsActivity.this));

		membercode_myearnings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String shareBody = "My Referral Code: "+""+ Session.getMemberCode(MyearningsActivity.this);
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

						}else{

							tv_status.setText("Processing");
							tv_status_des.setText("(Now you can claim Cash /Buy GPS Tracker)");
							ll_buy_gps.setVisibility(View.VISIBLE);
							ll_refund.setVisibility(View.GONE);
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



	private void callRefundAPI(){



	}




}
