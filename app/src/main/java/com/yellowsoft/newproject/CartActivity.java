package com.yellowsoft.newproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CartActivity extends AppCompatActivity {

	TextView quantity,page_title,total_tv,price_cart_tv,subtotal_tv,discount_tv;
	TextView productTitle,productPrice,totalProductPrice;
	TextView discount_tv_payment,shippingcharge_tv,referalmoney_payment;

	LinearLayout prdcheckout_btn,apply_ll_btn;
	LinearLayout menu_btn,back_btn,coupencode_ll;
	LinearLayout usescheme_money_ll;


	Integer cartquantity;
	ProductsData product;
	ImageView back,product_image;

	EditText coupon_code;
	int subtotal;
	int totalss,addShippingCharges;
	int totals;
	int discount_int = 0;
	int  shippingcharges_int;

	CheckBox checkBox;

	String coupon_code_str = "";
	boolean schemeAmtUsed;

	String scheme_amt="0";

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();

	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);

		callReferalMoney();

		checkBox = (CheckBox) findViewById(R.id.checkBox);

		coupencode_ll = (LinearLayout) findViewById(R.id.coupencode_ll);
		usescheme_money_ll = (LinearLayout) findViewById(R.id.usescheme_money_ll);

		productPrice = (TextView) findViewById(R.id.product_price_tv_item);
		totalProductPrice = (TextView) findViewById(R.id.totalprice_tv_item);
		productTitle = (TextView) findViewById(R.id.producttitle_tv_item);
		quantity = (TextView) findViewById(R.id.quantity_tv_cart_item);
		total_tv = (TextView) findViewById(R.id.total_tv);

		product_image = (ImageView)findViewById(R.id.product_img_item);


		subtotal_tv = (TextView)findViewById(R.id.subtotal_tv);
		discount_tv = (TextView)findViewById(R.id.discount_tv);
		shippingcharge_tv = (TextView)findViewById(R.id.shippingcharge_tv);

		discount_tv_payment = (TextView)findViewById(R.id.discount_tv_payment);
		referalmoney_payment = (TextView)findViewById(R.id.referalmoney_payment);

		product = (ProductsData)getIntent().getSerializableExtra("product");

		cartquantity = Integer.parseInt(getIntent().getStringExtra("quantity"));

		quantity.setText(cartquantity.toString());

		if (product.images.size()>1){

			Picasso.get().load(product.images.get(0).image_url).into(product_image);

			}

		productPrice.setText(product.originalprice);

		productTitle.setText(product.product_title);

		//
		Log.e("producttitle",product.product_title);




		coupon_code = (EditText)findViewById(R.id.ed_couponcode);
		coupon_code.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
		coupon_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE){
					CallCoupenService(coupon_code.getText().toString(),total_tv.getText().toString());

				}
				return false;
			}
		});


		apply_ll_btn = (LinearLayout)findViewById(R.id.apply_ll_btn);
		apply_ll_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CallCoupenService(coupon_code.getText().toString(),total_tv.getText().toString());
			}
		});


		//item total
		final Integer itemTotal;

		itemTotal = cartquantity*Integer.parseInt(product.originalprice);
		totalProductPrice.setText(""+itemTotal);
		subtotal_tv.setText(itemTotal.toString());

		subtotal = itemTotal;

		discount_tv.setText("0");

		//checkbox
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){



					schemeAmtUsed=true;
					coupencode_ll.setVisibility(View.GONE);

					//callReferalMoney();
					discount_tv.setText(""+scheme_amt);
					totals=addShippingCharges-Integer.parseInt(scheme_amt);

					coupon_code_str="";
					coupon_code.setText("");

					discount_int=0;

					total_tv.setText(""+totals);

				}
				else {
					schemeAmtUsed=false;

					coupencode_ll.setVisibility(View.VISIBLE);
					discount_tv.setText("0");

					total_tv.setText(""+addShippingCharges);
					//schemeAmtUsed  = false;\
					discount_int =0;

				}
			}
		});


        //    getting shipping charges


		final String shippingcharges;
		try {

			shippingcharges = ApplicationController.getInstance().settings.getString("shipping_charges");

			Log.e("shippingcharges",""+shippingcharges);
			shippingcharge_tv.setText(""+shippingcharges);

			shippingcharges_int = Integer.parseInt(shippingcharges);
			addShippingCharges = subtotal+Integer.parseInt(shippingcharges);
			totals = addShippingCharges;
			total_tv.setText(""+addShippingCharges);



		} catch (JSONException e) {
			e.printStackTrace();
		}



		Toolbar toolbar = (Toolbar)findViewById(R.id.cart_toolBar);
		setSupportActionBar(toolbar);
		setupActionBar();
		setupHeader();

		prdcheckout_btn = (LinearLayout)findViewById(R.id.proceedtocheckout_ll_btn);
		prdcheckout_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int total_to_send;
				total_to_send = subtotal+shippingcharges_int;
				Intent intent1  = new Intent(CartActivity.this,CheckoutActivity.class);
				intent1.putExtra("product",product);

				intent1.putExtra("price",subtotal);
				intent1.putExtra("delivery_charges",shippingcharges_int);
				if (coupon_code_str.equals("")) {
					intent1.putExtra("discount_amount", "");
					intent1.putExtra("coupon_code", "");

				}
				else {

					intent1.putExtra("discount_amount", discount_int);
					intent1.putExtra("coupon_code", coupon_code_str);
					total_to_send =total_to_send-discount_int;
				}


				if (schemeAmtUsed==true) {
					intent1.putExtra("schemeAmt_used", "1");
					intent1.putExtra("scheme_amount",scheme_amt);
					total_to_send =total_to_send-Integer.parseInt(scheme_amt);
				}
				else {
					intent1.putExtra("schemeAmt_used", "0");

					intent1.putExtra("scheme_amount","0");
				}

				intent1.putExtra("total_price",total_to_send);
				Log.e("total_tosend",""+total_to_send);
				startActivity(intent1);
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
		page_title.setText("CART");
		//btn_edit.setVisibility(View.VISIBLE);
		//btn_edit.setText("Search");
		//page_title.setText("Home");
	}


	//
	public void CallCoupenService(final String coupen, final String total){

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/coupon-check.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("resCartActivity",response);
				if(progressDialog!=null) {
					progressDialog.dismiss();
				}
				try {
					JSONObject jsonObject=new JSONObject(response);
					String reply=jsonObject.getString("status");
					Log.e("status",""+reply);
					if(reply.equals("Success")) {

						Snackbar.make(discount_tv,"Coupon code applied successfully",Snackbar.LENGTH_SHORT).show();
						String discount = jsonObject.getString("discount_value");
						Log.e("discount",""+discount);

						discount_tv.setText(""+discount);

						Log.e("totalss",""+totalss);

						discount_int = Integer.parseInt(discount);
						totals = addShippingCharges-Integer.parseInt(discount);

						Log.e("subtotal",""+subtotal);

						total_tv.setText(""+totals);

						Log.e("total",""+totals);

						Session.setTotalPrice(CartActivity.this,""+totals);


						coupon_code_str = coupon_code.getText().toString();
						Log.e("coupen_code",coupon_code_str);

					}
					else {
						Snackbar.make(discount_tv,"Invalid Coupon Code",Snackbar.LENGTH_SHORT).show();
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
						Toast.makeText(CartActivity.this,"Internet error",Toast.LENGTH_SHORT).show();
						//Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
					}
				}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> parameters = new HashMap<String, String>();
				parameters.put("coupon",coupen);
				parameters.put("cart_total",total);
				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
	}

	//referal amount check
	public void callReferalMoney(){
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/scheme_amount_check.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("referalmoney",response);

				if(progressDialog!=null) {
					progressDialog.dismiss();
					//scheme_amount
				}
				try {
					JSONObject jsonObject=new JSONObject(response);
					String reply=jsonObject.getString("scheme_amount");
					Log.e("status",""+reply);

					if (reply.equals("0")){
						discount_tv_payment.setText("use my scheme amount");
						usescheme_money_ll.setVisibility(View.GONE);
						scheme_amt="0";

					}
					else {
						discount_tv_payment.setText("use my scheme amount");

						//discount_tv.setText(reply);
						//discount_int = Integer.parseInt(reply);
						scheme_amt = String.valueOf(reply);


						/*if (schemeAmtUsed==true){


						}*/
					/*	else {
							discount_tv.setText("0");
							discount_int = 0;
						}*/

						//subtotal_tv.setText(reply);
					}


					referalmoney_payment.setText(reply);




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
						Toast.makeText(CartActivity.this,"Internet error",Toast.LENGTH_SHORT).show();
						// /Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
					}
				}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> parameters = new HashMap<String, String>();
				parameters.put("member_id",Session.getUserid(CartActivity.this));
				Log.e("memberid",Session.getUserid(CartActivity.this));



				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
	}
}
