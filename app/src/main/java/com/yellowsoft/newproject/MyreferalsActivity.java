package com.yellowsoft.newproject;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyreferalsActivity extends AppCompatActivity {
	RecyclerView myreferals;
	MyReferals_Adapter myReferals_adapter;
	ArrayList<MyReferalsData> myOrdersData=new ArrayList<MyReferalsData>();
	TextView page_title;
	ImageView back;
	LinearLayout back_btn,menu_btn;

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		Intent intent = new Intent(MyreferalsActivity.this,HomeActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activtiy_myreferals);
		myreferals =(RecyclerView)findViewById(R.id.referals_recycler);

		myOrdersData.add(new MyReferalsData("45734454","Processing","05677772348"));
		myOrdersData.add(new MyReferalsData("05693465","Processing","05677772348"));
		myOrdersData.add(new MyReferalsData("23452346","Deposited","849562342349"));
		myOrdersData.add(new MyReferalsData("23452345","Processing","05677772348"));
		myOrdersData.add(new MyReferalsData("13461646","Deposited","23452345443"));
		myOrdersData.add(new MyReferalsData("13613466","Processing","05677772348"));
		myOrdersData.add(new MyReferalsData("15763457","Deposited","23459782334"));
		myOrdersData.add(new MyReferalsData("45734454","Processing","05677772348"));
		myOrdersData.add(new MyReferalsData("34573457","Deposited","25678346584"));
		myOrdersData.add(new MyReferalsData("43574577","Processing","05677772348"));
		myOrdersData.add(new MyReferalsData("13461646","Deposited","23452345443"));
		myOrdersData.add(new MyReferalsData("13613466","Processing","05677772348"));
		myOrdersData.add(new MyReferalsData("05693465","Processing","05677772348"));
		myOrdersData.add(new MyReferalsData("23452346","Deposited","849562342349"));
		myOrdersData.add(new MyReferalsData("23452345","Processing","05677772348"));
		myOrdersData.add(new MyReferalsData("45734454","Processing","05677772348"));


		myReferals_adapter = new MyReferals_Adapter(MyreferalsActivity.this,myOrdersData);
		LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

		myreferals.setLayoutManager(linearLayoutManager);
		myreferals.setAdapter(myReferals_adapter);

		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_myreferals);
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
				Intent intent = new Intent(MyreferalsActivity.this,HomeActivity.class);
				startActivity(intent);
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
}
