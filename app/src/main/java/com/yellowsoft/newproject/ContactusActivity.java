package com.yellowsoft.newproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ContactusActivity extends AppCompatActivity{
	TextView page_title,topic_select_tv_dropdown;
	ImageView back,location_mycop,fbbtn,gmbtn,twbtn;
	LinearLayout back_btn,menu_btn;
	LinearLayout topselect_popup,topic_bg;
	ListView lv_topselect;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contactus);

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
}
