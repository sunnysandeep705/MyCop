package com.yellowsoft.newproject;

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

public class MyOrdersActivity extends AppCompatActivity {
	RecyclerView orders_rv;
	MyOrders_Adapter recycler_adapter;
	ArrayList<MyOrdersData> myOrdersData=new ArrayList<MyOrdersData>();
	TextView page_title;
	LinearLayout menu_btn,back_btn;
	ImageView back;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorders);
		orders_rv = (RecyclerView)findViewById(R.id.myorders_recycler);
		myOrdersData.add(new MyOrdersData("https://www.clients.yellowsoft.in/mycop/uploads/products/131537966319.png","45000","MYCOP5544663322","10","Yellowsoft,Anvitha Arcade,Near Hanuman Temple,Currency Nagar,Vijayawada,Krishna Dt.520008"));
		myOrdersData.add(new MyOrdersData("https://www.clients.yellowsoft.in/mycop/uploads/products/131537966319.png","30000","MYCOP5544661234","10","Yellowsoft,Anvitha Arcade,Near Hanuman Temple,Currency Nagar,Vijayawada,Krishna Dt.520008"));
		myOrdersData.add(new MyOrdersData("https://www.clients.yellowsoft.in/mycop/uploads/products/131537966319.png","20000","MYCOP5544663322","5","Yellowsoft,Anvitha Arcade,Near Hanuman Temple,Currency Nagar,Vijayawada,Krishna Dt.520008"));
		myOrdersData.add(new MyOrdersData("https://www.clients.yellowsoft.in/mycop/uploads/products/131537966319.png","12000","MYCOP5544660088","7","Yellowsoft,Anvitha Arcade,Near Hanuman Temple,Currency Nagar,Vijayawada,Krishna Dt.520008"));
		myOrdersData.add(new MyOrdersData("https://www.clients.yellowsoft.in/mycop/uploads/products/131537966319.png","13000","MYCOP5544668877","5","Yellowsoft,Anvitha Arcade,Near Hanuman Temple,Currency Nagar,Vijayawada,Krishna Dt.520008"));
		myOrdersData.add(new MyOrdersData("https://www.clients.yellowsoft.in/mycop/uploads/products/131537966319.png","15000","MYCOP5544661144","15","Yellowsoft,Anvitha Arcade,Near Hanuman Temple,Currency Nagar,Vijayawada,Krishna Dt.520008"));

		recycler_adapter = new MyOrders_Adapter(getApplicationContext(),myOrdersData);
		LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		orders_rv.setLayoutManager(linearLayoutManager);
		orders_rv.setAdapter(recycler_adapter);

		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_myorders);
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
		page_title.setText("MY ORDERS");
		//btn_edit.setVisibility(View.VISIBLE);
		//btn_edit.setText("Search");
		//page_title.setText("Home");
	}
}
