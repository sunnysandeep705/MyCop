package com.yellowsoft.newproject;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
	RelativeLayout decrease_btn,increase_btn;
	TextView quantity;
	SlidingImageAdapter slidingImageAdapter;
	TextView page_title,discount_price,title,originalprice_tv,description_tv;
	ImageView back;

	ProductsData product;

	ViewPager viewPager;
	TabLayout indicator_tab;
	SlidingImageAdapter slidingPageAdapter;
	Integer i;
	LinearLayout back_btn,menu_btn,cart_btn;
	ArrayList<SlidingImage_Data> slidingImage_data = new ArrayList<SlidingImage_Data>();




	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);

		discount_price = (TextView)findViewById(R.id.discounted_price);
		title = (TextView)findViewById(R.id.mycop_title_tv);
		originalprice_tv = (TextView)findViewById(R.id.originalprice_tv);
		description_tv = (TextView)findViewById(R.id.description_tv);

		product = (ProductsData)(getIntent().getSerializableExtra("product"));




		//product title
		title.setText(product.product_title);
		originalprice_tv.setText(product.discountprice);
		discount_price.setText(product.originalprice);
		description_tv.setText(product.description);

		for (int i =0;i<product.images.size();i++) {
			Log.e("sliderimagers",""+product.images.get(i).image_url);
			slidingImage_data.add(new SlidingImage_Data(product.images.get(i).image_url));
		}
		//Log.e("product",""+product.description);
		//String s=getIntent().getStringExtra("product");
		//Log.e("product",""+s);



		viewPager=(ViewPager)findViewById(R.id.image_slider);

		indicator_tab=(TabLayout)findViewById(R.id.indicator);

		slidingPageAdapter = new SlidingImageAdapter(ProductActivity.this,slidingImage_data);

		viewPager.setAdapter(slidingPageAdapter);

		indicator_tab.setupWithViewPager(viewPager,true);


		//toolbar
		Toolbar toolbar = (Toolbar)findViewById(R.id.product_toolbar);
		setSupportActionBar(toolbar);
		setupActionBar();
		setupHeader();

		//increase button & decrease button
		quantity = (TextView)findViewById(R.id.quantity_tv);
		increase_btn = (RelativeLayout)findViewById(R.id.rl_increase);
		decrease_btn = (RelativeLayout)findViewById(R.id.rl_decrease);
		i = Integer.parseInt(quantity.getText().toString());
		decrease_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String _stringVal;
				if (i > 1) {
					i = i - 1;
					_stringVal = String.valueOf(i);
					quantity.setText(_stringVal);
				} else {
					Log.d("src", "Value can't be less than 0");
				}
			}
		});
		increase_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String _stringVal;

				Log.d("src", "Increasing value...");
				i = i + 1;
				_stringVal = String.valueOf(i);
				quantity.setText(_stringVal);
			}
		});

		final Intent intent = new Intent(ProductActivity.this,CartActivity.class);

		//add to cart button
		cart_btn = (LinearLayout)findViewById(R.id.addtocart_ll_btn);
		cart_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//
				// Toast.makeText(getContext(),"go to cart",Toast.LENGTH_LONG).show();

				String q;
				q=quantity.getText().toString();
				intent.putExtra("quantity",q);
				intent.putExtra("product",product);
				startActivity(intent);
				//getActivity().finish();

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

		page_title.setText(""+getIntent().getStringExtra("title"));
		//btn_edit.setVisibility(View.VISIBLE);
		//btn_edit.setText("Search");
		//page_title.setText("Home");
	}
	/*public void CallProductdetails(){

		final ProgressDialog progressDialog = new ProgressDialog(ProductActivity.this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/products.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("res",response);
				if(progressDialog!=null&& progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				try {

					JSONArray jsonArray = new JSONArray(response);
					Log.e("jsonArray",""+jsonArray.toString());

					String producttitle=jsonArray.getJSONObject(0).getString("title");
					Log.e("pagetitletitle",""+producttitle);
					title.setText(producttitle);


					String price=jsonArray.getJSONObject(0).getString("price");
					Log.e("price",""+price);
					discount_price.setText(price);
					Session.setPrice(ProductActivity.this,price);



					String discount=jsonArray.getJSONObject(0).getString("discount");
					Log.e("discount",""+discount);
					String givenprice = discount+price;
					int i = Integer.parseInt(discount)+Integer.parseInt(price);
					originalprice_tv.setText(""+i);

					String quantitys=jsonArray.getJSONObject(0).getString("quantity");
					Log.e("quantity",""+quantitys);
					quantity.setText(quantitys);


					String description=jsonArray.getJSONObject(0).getString("description");
					Log.e("description",""+description);
					description_tv.setText(Html.fromHtml("<p>"+description+"</p>"));

					String images=jsonArray.getJSONObject(0).getString("images");
					Log.e("images",""+images);

					JSONArray jsonArray1 = new JSONArray(images);
					Log.e("jsonarray1",""+jsonArray1);
					Log.e("jsonarray1length",""+jsonArray1.length());
					//	Log.e("imagessssss",""+jsonArray1.getJSONObject(0).getString("image"));

					if (jsonArray1.length()>1){
						Log.e("length","length");
						for (int j=0;j<=jsonArray1.length();j++){
							//slidingImage_data.add(new SlidingImage_Data(jsonArray.getJSONObject(j).getString("image")));
							String s = jsonArray1.getString(j);
							Log.e("s",""+s);
							slidingImage_data.add(new SlidingImage_Data(s));
							Log.e("imagessssss",""+jsonArray1.getString(j));

						}

					}


				} catch (JSONException e) {
					e.printStackTrace();
				}
				slidingImageAdapter.notifyDataSetChanged();
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
				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
//		slidingPageAdapter.notifyDataSetChanged();
	}*/
}
