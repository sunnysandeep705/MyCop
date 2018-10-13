package com.yellowsoft.newproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContactusActivity extends AppCompatActivity{
	TextView page_title,topic_select_tv_dropdown;
	ImageView back,location_mycop,fbbtn,gmbtn,twbtn;
	EditText et_phone_contactus,et_name_contactus,et_email_contactus,et_message_contactus;
	LinearLayout back_btn,menu_btn;
	LinearLayout topselect_popup,topic_bg,submit_ll_contact;
	ListView lv_topselect;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contactus);

		et_phone_contactus = (EditText)findViewById(R.id.et_phone_contactus);
		et_name_contactus = (EditText)findViewById(R.id.et_name_contactus);
		et_email_contactus = (EditText)findViewById(R.id.et_email_contactus);
		et_message_contactus = (EditText)findViewById(R.id.et_message_contactus);







		//mycop location
		location_mycop = (ImageView)findViewById(R.id.mycop_location_img);
		location_mycop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String uri = "https://maps.google.com/?cid=457372523366386631";
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			}
		});

		topselect_popup = (LinearLayout)findViewById(R.id.topselect_popup);
//		final Typeface face= Typeface.createFromAsset(ContactusActivity.this.getAssets(), "fonts/gothamroundedmedium.otf");

		//popup background onclick closes popup
		topic_bg = (LinearLayout)findViewById(R.id.topic_ll_bg);
		topic_bg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				topselect_popup.setVisibility(View.GONE);
			}
		});


		topic_select_tv_dropdown = (TextView)findViewById(R.id.topic_select_tv_dropdown);
		topic_select_tv_dropdown.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				topselect_popup.setVisibility(View.VISIBLE);
			}
		});

		//topicselect list view
		lv_topselect = (ListView)findViewById(R.id.topicselect_lv);
		lv_topselect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position==0){
					topselect_popup.setVisibility(View.GONE);
					topic_select_tv_dropdown.setText("My Orders");
				}else if (position==1){
					topselect_popup.setVisibility(View.GONE);
					topic_select_tv_dropdown.setText("My Profile");
				}
				else if (position==2){
					topselect_popup.setVisibility(View.GONE);
					topic_select_tv_dropdown.setText("My Referrals");
				}
				else if (position==3){
					topselect_popup.setVisibility(View.GONE);
					topic_select_tv_dropdown.setText("My Referral Commission");
				}
				else if (position==4){
					topselect_popup.setVisibility(View.GONE);
					topic_select_tv_dropdown.setText("Investment Refund");
				}
				else if (position==5){
					topselect_popup.setVisibility(View.GONE);
					topic_select_tv_dropdown.setText("General");
				}
			}
		});


		//social links
		fbbtn = (ImageView)findViewById(R.id.fb_btn_contactus);
		gmbtn = (ImageView)findViewById(R.id.gm_btn_contactus);
		twbtn = (ImageView)findViewById(R.id.tw_btn_contactus);

		fbbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.facebook.com")));
			}
		});

		gmbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.googleplus.com")));
			}
		});

		twbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.twitter.com")));
			}
		});

		//subit button
		submit_ll_contact  = (LinearLayout)findViewById(R.id.submit_ll_contact);
		submit_ll_contact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(et_name_contactus.getText().toString().equals("")){
					Snackbar.make(et_phone_contactus,"Please enter name",Snackbar.LENGTH_SHORT).show();

				}
				else if(et_email_contactus.getText().toString().equals("")){

					Snackbar.make(et_phone_contactus,"Please enter email",Snackbar.LENGTH_SHORT).show();


				}
				else if(et_phone_contactus.getText().toString().equals("")){
					Snackbar.make(et_phone_contactus,"Please enter phonenumber",Snackbar.LENGTH_SHORT).show();


				}
				else if(et_message_contactus.getText().toString().equals("")){
					Snackbar.make(et_phone_contactus,"Please enter message",Snackbar.LENGTH_SHORT).show();


				}

				else {
					submitForm(et_phone_contactus.getText().toString(),et_name_contactus.getText().toString(),et_email_contactus.getText().toString(),et_message_contactus.getText().toString(),topic_select_tv_dropdown.getText().toString());
				}

			}
		});


		Toolbar toolbar = (Toolbar)findViewById(R.id.contactus_toolbar);
		setSupportActionBar(toolbar);
		setupActionBar();
		setupHeader();


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
		page_title.setText("CONTACT US");
		//btn_edit.setVisibility(View.VISIBLE);
		//btn_edit.setText("Search");
		//page_title.setText("Home");
	}
	public void submitForm(final String phone,final String name,final String email,final String message,final String topic){

		//Log.e("memberidreferalcode","id="+member_id+" , code = "+referal_code);
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/contact-us.php";

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

						//Session.setMemberCode(ContactusActivity.this,jsonObject.getString("member_code"));

						String message = jsonObject.getString("message");
						Snackbar.make(et_email_contactus,"Submitted successfully",Snackbar.LENGTH_SHORT).show();





					}
					else
					{
						String errorMessage =jsonObject.getString("message");
					//	Snackbar.make(apply_ll_btn_referal,""+errorMessage,Snackbar.LENGTH_SHORT).show();
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
				parameters.put("name",name);
				parameters.put("email",email);

				parameters.put("phone",phone);
				if (topic.toString().equals("")){
					parameters.put("topic","General");
				}
				else {
					parameters.put("topic",topic);
					Log.e("topic",topic);
				}


				parameters.put("message",message);




				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);

	}
}
