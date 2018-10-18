package com.yellowsoft.newproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyProfileActivity extends AppCompatActivity {
	TextView page_title,tv_username_myprofile;
	TextView dojoining_tv,referedby_tv;
	LinearLayout back_btn,menu_btn,changepassword_btn;
	ImageView back;
	CardView edit_btn;

	String member;

	EditText ed_fname,ed_lname,ed_phone,ed_email,ed_acc_name,ed_acc_number,ed_ifsc_code,ed_acc_email;
	EditText ed_bankaccnumber_confirm;


	LinearLayout save_ll;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myprofile);
		member = Session.getUserid(MyProfileActivity.this);
		Log.e("memberid",""+member);
		//callAddressListService(member,"3");

		tv_username_myprofile = (TextView)findViewById(R.id.tv_username_myprofile);
		String name = Session.getUserName(MyProfileActivity.this);
		tv_username_myprofile.setText(name);

		referedby_tv = (TextView)findViewById(R.id.referedby_tv);
		dojoining_tv = (TextView)findViewById(R.id.dojoining_tv);

		edit_btn = (CardView)findViewById(R.id.edit_cardview);


		ed_fname = (EditText) findViewById(R.id.ed_usrname_myprofile);
		ed_lname = (EditText) findViewById(R.id.ed_vechilename_myprofile);
		ed_phone = (EditText) findViewById(R.id.ed_phone_myprofile);
		ed_email = (EditText) findViewById(R.id.ed_email_myprofile);

		ed_bankaccnumber_confirm = (EditText) findViewById(R.id.ed_bankaccnumber_confirm);

		ed_acc_name = (EditText) findViewById(R.id.ed_bankaccname);
		ed_acc_number = (EditText) findViewById(R.id.ed_bankaccnumber);
		ed_ifsc_code = (EditText) findViewById(R.id.ed_bankaccifscode);
		ed_acc_email = (EditText) findViewById(R.id.ed_bankaccemail);


		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_myreferals);
		setSupportActionBar(toolbar);
		setupActionBar();
		setupHeader();

		getProfileDetails();

		changepassword_btn = (LinearLayout)findViewById(R.id.changepassword_ll);

		changepassword_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyProfileActivity.this,ChangePasswordActivity.class);
				startActivity(intent);
			}
		});
		save_ll = (LinearLayout) findViewById(R.id.save_ll_btn);

		save_ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if(ed_fname.getText().toString().equals("")){

					Snackbar.make(save_ll,"Enter First Name",Snackbar.LENGTH_SHORT).show();

				}else if(ed_lname.getText().toString().equals("")){

					Snackbar.make(save_ll,"Enter Last Name",Snackbar.LENGTH_SHORT).show();

				}else if(ed_phone.getText().toString().equals("")){

					Snackbar.make(save_ll,"Enter Phone Number",Snackbar.LENGTH_SHORT).show();

				}else if(ed_email.getText().toString().equals("")){

					Snackbar.make(save_ll,"Enter Email Address",Snackbar.LENGTH_SHORT).show();

				}

				else if(  ed_acc_email.getText().toString().equals("")  &&  (ed_acc_name.getText().toString().equals("") || !ed_acc_number.getText().toString().equals((ed_bankaccnumber_confirm.getText().toString())) || ed_ifsc_code.getText().toString().equals("")) ){

					if(!ed_acc_number.getText().toString().equals(ed_bankaccnumber_confirm.getText().toString())){

						Snackbar.make(save_ll,"Enter same account number",Snackbar.LENGTH_SHORT).show();
					}
					else {
						Snackbar.make(save_ll,"Enter bank details or upi id",Snackbar.LENGTH_SHORT).show();
					}
				}else{


					setProfileDetails();


				}

			}
		});



	}



	//setup header
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
		page_title.setText("MY PROFILE");
		//btn_edit.setVisibility(View.VISIBLE);
		//btn_edit.setText("Search");
		//page_title.setText("Home");
	}




	public void getProfileDetails (){


		final ProgressDialog progressDialog = new ProgressDialog(MyProfileActivity.this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/members.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("resMyprofile",response);

				if(progressDialog!=null) {
					progressDialog.dismiss();
				}
				try {
					JSONArray jsonArray = new JSONArray(response);

					JSONObject jsonObject= jsonArray.getJSONObject(0);




						ed_fname.setText(jsonObject.getString("fname"));
						ed_lname.setText(jsonObject.getString("lname"));
						Session.setUserid(MyProfileActivity.this,Session.getUserid(MyProfileActivity.this),jsonObject.getString("fname")+jsonObject.getString("lname"));
						ed_phone.setText(jsonObject.getString("phone"));
						ed_email.setText(jsonObject.getString("email"));

						dojoining_tv.setText(jsonObject.getString("scheme_amount_date"));
						referedby_tv.setText(jsonObject.getString("referred_code"));

						ed_acc_name.setText(jsonObject.getString("bank"));
						ed_acc_number.setText(jsonObject.getString("acno"));
						ed_ifsc_code.setText(jsonObject.getString("ifsc"));
						ed_acc_email.setText(jsonObject.getString("upid"));








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
				parameters.put("member_id",Session.getUserid(MyProfileActivity.this));
				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
	}

	public void setProfileDetails (){


		final ProgressDialog progressDialog = new ProgressDialog(MyProfileActivity.this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);

		String URL = Session.BASE_URL+"api/edit-member.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("resMyprofile",response);

				if(progressDialog!=null) {
					progressDialog.dismiss();
				}
				try {
					//JSONArray jsonArray = new JSONArray(response);
					JSONObject jsonObject= new JSONObject(response);
					String reply  = jsonObject.getString("status");

					if(reply.equals("Success")) {

						Toast.makeText(MyProfileActivity.this, "You have successfully updated your account", Toast.LENGTH_SHORT).show();//You have successfully updated your account
					}
					else {
						Toast.makeText(MyProfileActivity.this,""+jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
					}


				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(MyProfileActivity.this,""+e.getMessage().toString(),Toast.LENGTH_SHORT).show();


				}
			}
		},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if(progressDialog!=null)
							progressDialog.dismiss();
						Toast.makeText(MyProfileActivity.this,"Something went wrong, try after sometime ",Toast.LENGTH_SHORT).show();

						//Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
					}
				}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> parameters = new HashMap<String, String>();
				parameters.put("member_id",Session.getUserid(MyProfileActivity.this));
				parameters.put("fname",ed_fname.getText().toString());
				parameters.put("lname",ed_lname.getText().toString());
				parameters.put("phone",ed_phone.getText().toString());
				parameters.put("email",ed_email.getText().toString());

				if(ed_acc_email.getText().toString().equals("")) {
					parameters.put("type", "1");
					parameters.put("bank",ed_acc_name.getText().toString());
					parameters.put("acno",ed_acc_number.getText().toString());
					parameters.put("ifsc",ed_ifsc_code.getText().toString());


				}else{
					parameters.put("type","2");
					parameters.put("upid",ed_acc_email.getText().toString());

				}





				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
	}


}
