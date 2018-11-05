package com.yellowsoft.newproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class MyOrdersActivity extends AppCompatActivity {
	RecyclerView orders_rv;
	MyOrders_Adapter recycler_adapter;


	ArrayList<MyOrdersData> myOrdersData=new ArrayList<MyOrdersData>();
	/*OrderHistory_Adapter orderHistory_adapter ;
	ArrayList<MessageData> messageData;
	ListView messages_lv;*/

	TextView page_title;
	LinearLayout menu_btn,back_btn,no_orders_ll,shopnow_ll;
	LinearLayout popup;
	LinearLayout shop_ll_toolbar;

	ImageView back,shop_img_toolbar;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorders);

		orders_rv = (RecyclerView)findViewById(R.id.myorders_recycler);


		/*messages_lv = (ListView)findViewById(R.id.order_history_lv);

		//orderHistory_adapter = new OrderHistory_Adapter(MyOrdersActivity.this,messageData);

		messages_lv.setAdapter(orderHistory_adapter);
		//orderHistory_adapter.notifyDataSetChanged();*/


		popup = (LinearLayout)findViewById(R.id.popup_myorders);
		no_orders_ll = (LinearLayout)findViewById(R.id.no_orders_ll);
		shopnow_ll = (LinearLayout)findViewById(R.id.shopnow_ll);
		shopnow_ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyOrdersActivity.this,HomeActivity.class);
				setResult(RESULT_OK,intent);
				finish();
			}
		});

//		myOrdersData.add(new MyOrdersData("https://www.clients.yellowsoft.in/mycop/uploads/products/131537966319.png","45000","MYCOP5544663322","10","Yellowsoft,Anvitha Arcade,Near Hanuman Temple,Currency Nagar,Vijayawada,Krishna Dt.520008"));
//		myOrdersData.add(new MyOrdersData("https://www.clients.yellowsoft.in/mycop/uploads/products/131537966319.png","30000","MYCOP5544661234","10","Yellowsoft,Anvitha Arcade,Near Hanuman Temple,Currency Nagar,Vijayawada,Krishna Dt.520008"));
//		myOrdersData.add(new MyOrdersData("https://www.clients.yellowsoft.in/mycop/uploads/products/131537966319.png","20000","MYCOP5544663322","5","Yellowsoft,Anvitha Arcade,Near Hanuman Temple,Currency Nagar,Vijayawada,Krishna Dt.520008"));
//		myOrdersData.add(new MyOrdersData("https://www.clients.yellowsoft.in/mycop/uploads/products/131537966319.png","12000","MYCOP5544660088","7","Yellowsoft,Anvitha Arcade,Near Hanuman Temple,Currency Nagar,Vijayawada,Krishna Dt.520008"));
//		myOrdersData.add(new MyOrdersData("https://www.clients.yellowsoft.in/mycop/uploads/products/131537966319.png","13000","MYCOP5544668877","5","Yellowsoft,Anvitha Arcade,Near Hanuman Temple,Currency Nagar,Vijayawada,Krishna Dt.520008"));
//		myOrdersData.add(new MyOrdersData("https://www.clients.yellowsoft.in/mycop/uploads/products/131537966319.png","15000","MYCOP5544661144","15","Yellowsoft,Anvitha Arcade,Near Hanuman Temple,Currency Nagar,Vijayawada,Krishna Dt.520008"));

		recycler_adapter = new MyOrders_Adapter(getApplicationContext(),myOrdersData);
		LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		orders_rv.setLayoutManager(linearLayoutManager);
		orders_rv.setAdapter(recycler_adapter);

		AlertDialog.Builder builderSingle = new AlertDialog.Builder(MyOrdersActivity.this);


		orders_rv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//showPopup();
				Intent intent = new Intent(MyOrdersActivity.this,OrderDetailsActivity.class);
				intent.putExtra("details",myOrdersData);
				startActivity(intent);
			}
		});


		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_myorders);
		setSupportActionBar(toolbar);
		setupActionBar();
		setupHeader();

		CallProductdetails();

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

		shop_img_toolbar = (ImageView)v.findViewById(R.id.shop_img_toolbar);
		//shop_img_toolbar.setVisibility(View.VISIBLE);
		shop_ll_toolbar = (LinearLayout)v.findViewById(R.id.shop_ll_toolbar);
		shop_img_toolbar.setVisibility(View.VISIBLE);
		shop_ll_toolbar.setVisibility(View.VISIBLE);
		shop_ll_toolbar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyOrdersActivity.this,HomeActivity.class);
				//startActivity(intent);
				setResult(RESULT_OK,intent);
				finish();
			}
		});
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



	public void CallProductdetails(){

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/order-history.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("resOrderHistory",response);
				if(progressDialog!=null&& progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				try {

					JSONArray jsonArray = new JSONArray(response);
					Log.e("jsonArray",""+jsonArray.toString());
					if (jsonArray.length()<1){
						no_orders_ll.setVisibility(View.VISIBLE);
						orders_rv.setVisibility(View.GONE);
					}
					else {
						no_orders_ll.setVisibility(View.GONE);
						orders_rv.setVisibility(View.VISIBLE);
					}


					myOrdersData.clear();
					recycler_adapter.notifyDataSetChanged();


					for (int i = 0;i<jsonArray.length();i++){
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						Log.e("jsonobject",""+jsonObject);
						Log.e("jsonobjectLength",""+jsonObject.length());
						MyOrdersData temp = new MyOrdersData(jsonObject);

						myOrdersData.add(temp);

					}
					recycler_adapter.notifyDataSetChanged();

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

				parameters.put("member_id",Session.getUserid(MyOrdersActivity.this));

				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
//		slidingPageAdapter.notifyDataSetChanged();
	}
	public void showPopup(){
		popup.setVisibility(View.VISIBLE);
	}

}
