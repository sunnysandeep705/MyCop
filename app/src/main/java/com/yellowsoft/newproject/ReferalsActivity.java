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
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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

public class ReferalsActivity extends AppCompatActivity {

	TextView page_title,referedby_tv;
	ImageView back;
	LinearLayout back_btn,menu_btn,apply_ll_btn_referal,paynow_ll_referal;

	LinearLayout popup,confirm,cancel;
	EditText referalcode_referal;

	String referal_code_global = "";

	EditText upi_id_et;

	EditText ed_bank_name;
	EditText ed_bank_ac;
	EditText ed_ifsc_code;


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_referals);

		referalcode_referal = (EditText)findViewById(R.id.referalcode_referal);
		referalcode_referal.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
		referedby_tv = (TextView)findViewById(R.id.referedby_tv);

		upi_id_et = (EditText) findViewById(R.id.ed_upi_id);

		ed_bank_name = (EditText) findViewById(R.id.ed_bank_name);
		ed_bank_ac = (EditText) findViewById(R.id.ed_ac_number);
		ed_ifsc_code = (EditText) findViewById(R.id.ed_ifcs_code);


		popup = (LinearLayout)findViewById(R.id.popup_referal);

		paynow_ll_referal = (LinearLayout) findViewById(R.id.paynow_ll_referal);
		paynow_ll_referal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(referal_code_global.equals("")){

					Snackbar.make(paynow_ll_referal,"Enter referal code",Snackbar.LENGTH_SHORT).show();

				}  else if(  upi_id_et.getText().toString().equals("")  &&  (ed_bank_ac.getText().toString().equals("") || ed_bank_name.getText().toString().equals("") || ed_ifsc_code.getText().toString().equals("")) ){

					Snackbar.make(paynow_ll_referal,"Enter bank details or upi id",Snackbar.LENGTH_SHORT).show();

				} else {

					popup.setVisibility(View.VISIBLE);
					referedby_tv.setText(referalcode_referal.getText().toString());

					//Intent intent = new Intent(ReferalsActivity.this,HomeActivity.class);
					//startActivity(intent);

				}



			}
		});
		cancel = (LinearLayout)findViewById(R.id.cancel_ll_referal);
		confirm = (LinearLayout)findViewById(R.id.confirm_ll_referal);

		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				popup.setVisibility(View.GONE);


			}
		});
		confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popup.setVisibility(View.GONE);

				final String memberid =  Session.getUserid(ReferalsActivity.this);
				Log.e("memberid",""+memberid);
				final String referalcode = referalcode_referal.getText().toString();
				Log.e("referalcode",""+referalcode);

				addUserIntoSchem(memberid,referalcode);

			}
		});



		referalcode_referal.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE){
					final String memberid =  Session.getUserid(ReferalsActivity.this);
					Log.e("memberid",""+memberid);
					final String referalcode = referalcode_referal.getText().toString();
					Log.e("referalcode",""+referalcode);
					callReferalService(memberid,referalcode);
				}
				return false;
			}
		});

		apply_ll_btn_referal = (LinearLayout)findViewById(R.id.apply_ll_btn_referal);




		//set toolbar
		Toolbar toolbar = (Toolbar)findViewById(R.id.referal_toolbar);
		setSupportActionBar(toolbar);
		setupActionBar();
		setupHeader();

		//apply referal id button conclick
		apply_ll_btn_referal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(!referalcode_referal.getText().equals("")) {

					final String memberid = Session.getUserid(ReferalsActivity.this);
					Log.e("memberid", "" + memberid);
					final String referalcode = referalcode_referal.getText().toString();
					Log.e("referalcode", "" + referalcode);
					callReferalService(memberid, referalcode);
				}


			}
		});
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
		page_title.setText("REFERAL");
		//btn_edit.setVisibility(View.VISIBLE);
		//btn_edit.setText("Search");
		//page_title.setText("Home");
	}

	public void callReferalService(final String member_id,final String referal_code){
		Log.e("memberidreferalcode","id="+member_id+" , code = "+referal_code);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/code_check.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("res",response);
                if(progressDialog!=null) {
                    progressDialog.dismiss();
                }
				try {
					JSONObject jsonObject=new JSONObject(response);
					String reply=jsonObject.getString("status");
					Log.e("status",""+reply);
					if(reply.equals("Success")) {
						String message = jsonObject.getString("message");
						Snackbar.make(apply_ll_btn_referal,""+message,Snackbar.LENGTH_SHORT).show();
						referal_code_global = referal_code;
					}
					else
					{
						String errorMessage =jsonObject.getString("message");
						Snackbar.make(apply_ll_btn_referal,""+errorMessage,Snackbar.LENGTH_SHORT).show();
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
						//  Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
					}
				}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("member_id",member_id);
                parameters.put("code",referal_code);
				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);

	}


	public void addUserIntoSchem(final String member_id,final String referal_code){

		Log.e("memberidreferalcode","id="+member_id+" , code = "+referal_code);
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/join_scheme.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("res",response);
				if(progressDialog!=null) {
					progressDialog.dismiss();
				}
				try {
					JSONObject jsonObject=new JSONObject(response);
					String reply=jsonObject.getString("status");
					Log.e("status",""+reply);
					if(reply.equals("Success")) {

						Session.setMemberCode(ReferalsActivity.this,jsonObject.getString("member_code"));

						String message = jsonObject.getString("message");
						Snackbar.make(apply_ll_btn_referal,""+message,Snackbar.LENGTH_SHORT).show();


					}
					else
					{
						String errorMessage =jsonObject.getString("message");
						Snackbar.make(apply_ll_btn_referal,""+errorMessage,Snackbar.LENGTH_SHORT).show();
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
						//  Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
					}
				}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> parameters = new HashMap<String, String>();
				parameters.put("member_id",member_id);
				parameters.put("code",referal_code);

               if(upi_id_et.getText().toString().equals("")){
				   parameters.put("type","1");
				   parameters.put("bank",ed_bank_name.getText().toString());
				   parameters.put("acno",ed_bank_ac.getText().toString());
				   parameters.put("ifsc",ed_ifsc_code.getText().toString());


			   }else{
				   parameters.put("type","2");
				   parameters.put("upid",upi_id_et.getText().toString());


			   }



				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);

	}

}
