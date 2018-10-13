package com.yellowsoft.newproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity  implements PaymentResultListener {
	TextView quantity,page_title,cod_tv,wallet_tv,card_tv,total_tv_payment;
	TextView totalpayable_amt,referalmoney_payment;
	LinearLayout menu_btn,back_btn,submit_btn,payconfirm_ll_btn,cod_ll,card_ll,wallet_ll;
	ImageView back,checkoff_cash,checkon_cash,checkoff_card,checkon_card,checkoff_wallet,checkon_wallet,cashimg,walletimg,cardimg;

	String invoiceid;

	CheckBox checkBox;
	boolean collect_payment = true;

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		Intent intent = new Intent(PaymentActivity.this,HomeActivity.class);
		startActivity(intent);

	}
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		Toolbar toolbar = (Toolbar)findViewById(R.id.payment_toolBar);
		payconfirm_ll_btn = (LinearLayout)findViewById(R.id.payconfirm_ll_btn);

		checkBox = (CheckBox)findViewById(R.id.checkBox);

		callReferalMoney();

		referalmoney_payment = (TextView)findViewById(R.id.referalmoney_payment);


		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked==true){
					//callReferalMoney();
				}
			}
		});

		totalpayable_amt = (TextView)findViewById(R.id.totalpayable_tv_payment);

		total_tv_payment = (TextView)findViewById(R.id.total_tv_payment);

		String totalprices =String.valueOf(getIntent().getIntExtra("total_price",0));
		Log.e("totalprice",""+totalprices);
		String total = String.valueOf(getIntent().getIntExtra("total_price",0));

		total_tv_payment.setText(total);

		int i = Integer.parseInt(totalprices)-0;
		totalpayable_amt.setText(""+i);


		cod_ll = (LinearLayout)findViewById(R.id.cod_ll);
		card_ll = (LinearLayout)findViewById(R.id.card_ll);
		wallet_ll = (LinearLayout)findViewById(R.id.wallet_ll);

		cashimg = (ImageView)findViewById(R.id.cash_img);
		cod_tv = (TextView)findViewById(R.id.cod_tv);

		walletimg = (ImageView)findViewById(R.id.wallet_img);
		wallet_tv = (TextView)findViewById(R.id.wallet_tv);

		cardimg = (ImageView)findViewById(R.id.card_img);
		card_tv = (TextView)findViewById(R.id.card_tv);

		checkoff_cash = (ImageView)findViewById(R.id.checkoff_cash);
		checkon_cash = (ImageView)findViewById(R.id.checkon_cash);

		checkoff_card = (ImageView)findViewById(R.id.checkoff_card);
		checkon_card = (ImageView)findViewById(R.id.checkon_card);

		checkoff_wallet = (ImageView)findViewById(R.id.checkoff_wallet);
		checkon_wallet = (ImageView)findViewById(R.id.checkon_wallet);
		//changecolor(card_tv,cardimg);

		cod_ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				resetColors();
				check(checkoff_cash, checkon_cash,checkon_card,checkon_wallet);
				checkoff(checkoff_card,checkoff_wallet);
				changecolor(cod_tv,cashimg);
				collect_payment = false;

			}
		});

		card_ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				resetColors();
				check(checkoff_card,checkon_card,checkon_cash,checkon_wallet);
				checkoff(checkoff_cash,checkoff_wallet);
				changecolor(card_tv,cardimg);
				collect_payment = true;
			}
		});

		wallet_ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				resetColors();
				check(checkoff_wallet,checkon_wallet,checkon_card,checkon_cash);
				checkoff(checkoff_card,checkoff_cash);
				changecolor(wallet_tv,walletimg);
				collect_payment = true;
			}
		});

		/*checkon_cash.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				check(checkon_cash,checkoff_cash,checkon_wallet,checkon_card);
			}
		});

		checkon_wallet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				check(checkon_wallet,checkoff_wallet,checkon_card,checkon_cash);
			}
		});

		checkon_card.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				check(checkon_card,checkoff_card,checkon_cash,checkon_wallet);
			}
		});*/

		payconfirm_ll_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				CallAddAddressService();

//				if(collect_payment)
//				startPayment();

				}
		});
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
		page_title.setText("Checkout");
		//btn_edit.setVisibility(View.VISIBLE);
		//btn_edit.setText("Search");
		//page_title.setText("Home");
	}

	private void check(ImageView imageView,ImageView imageView2,ImageView imageView3,ImageView imageView4){
		imageView.setVisibility(View.GONE);
		imageView2.setVisibility(View.VISIBLE);
		imageView3.setVisibility(View.GONE);
		imageView4.setVisibility(View.GONE);
	}

	private void checkoff(ImageView imageView,ImageView imageView2){
		imageView.setVisibility(View.VISIBLE);
		imageView2.setVisibility(View.VISIBLE);

	}

	private void changecolor(TextView textView , ImageView imageView){
		textView.setTextColor(getResources().getColor(R.color.colorBlack));
		imageView.setColorFilter(back.getContext().getResources().getColor(R.color.themeDarkColor), PorterDuff.Mode.SRC_ATOP);

	}

	private void resetColors(){
		cod_tv.setTextColor(getResources().getColor(R.color.colorlightGrey));
		cashimg.setColorFilter(back.getContext().getResources().getColor(R.color.colorlightGrey), PorterDuff.Mode.SRC_ATOP);

		wallet_tv.setTextColor(getResources().getColor(R.color.colorlightGrey));
		walletimg.setColorFilter(back.getContext().getResources().getColor(R.color.colorlightGrey), PorterDuff.Mode.SRC_ATOP);

		card_tv.setTextColor(getResources().getColor(R.color.colorlightGrey));
		cardimg.setColorFilter(back.getContext().getResources().getColor(R.color.colorlightGrey), PorterDuff.Mode.SRC_ATOP);

	}


	public void CallAddAddressService(){


		final ProgressDialog progressDialog = new ProgressDialog(PaymentActivity.this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/place-order.php";

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



						if(collect_payment){


							startPayment();

						}else {

							Intent intent = new Intent(PaymentActivity.this, ThankyouActivity.class);
							intent.putExtra("id", jsonObject.getString("invoice_id"));
							invoiceid = jsonObject.getString("invoice_id");
							startActivity(intent);
							finish();
						}

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
						//Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
					}
				}){
			@Override
			protected Map<String,String> getParams(){


				JSONObject jsonObject_to_send = new JSONObject();







				JSONObject address = new JSONObject();

				try {

					address.put("firstname",getIntent().getStringExtra("firstname"));
					address.put("lastname",getIntent().getStringExtra("lastname"));
					address.put("address",getIntent().getStringExtra("address"));
					address.put("city",getIntent().getStringExtra("city"));
					address.put("state",getIntent().getStringExtra("state"));
					address.put("pincode",getIntent().getStringExtra("pincode"));
					address.put("phone",getIntent().getStringExtra("phone"));
					address.put("email",getIntent().getStringExtra("email"));



				jsonObject_to_send.put("address",address);
				jsonObject_to_send.put("products",getProductasJson((ProductsData) getIntent().getSerializableExtra("product")));
				jsonObject_to_send.put("total_price",String.valueOf(getIntent().getIntExtra("total_price",0)));
				jsonObject_to_send.put("coupon_code",getIntent().getStringExtra("coupon_code"));

				if(collect_payment) {

					jsonObject_to_send.put("payment_method", "Online");
				}else{
					jsonObject_to_send.put("payment_method", "Cash");
				}

				jsonObject_to_send.put("discount_amount",String.valueOf(getIntent().getIntExtra("discount_amount",0)));
				jsonObject_to_send.put("price",String.valueOf(getIntent().getIntExtra("price",0)));
				jsonObject_to_send.put("delivery_charges",String.valueOf(getIntent().getIntExtra("delivery_charges",0)));
				jsonObject_to_send.put("member_id",Session.getUserid(PaymentActivity.this));


				} catch (JSONException e) {
					e.printStackTrace();
				}





				Map<String,String> parameters = new HashMap<String, String>();
				parameters.put("content",jsonObject_to_send.toString());

				for (Map.Entry<String,String> entry : parameters.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					// do stuff
					Log.e(key,value);
				}

				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
	}



	public  JSONArray getProductasJson(ProductsData productsData){

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("product_id",productsData.product_id);
			jsonObject.put("quantity",productsData.cartquantity);
			jsonObject.put("price",productsData.originalprice);


		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONArray temp = new JSONArray();
		temp.put(jsonObject);

		return temp;

	};




	public void startPayment() {
		/**
		 * You need to pass current activity in order to let Razorpay create CheckoutActivity
		 */
		final Activity activity = this;

		final Checkout co = new Checkout();
		Checkout.clearUserData(activity);

		try {
			JSONObject options = new JSONObject();
			options.put("name", "My Cop");
			ProductsData productsData = (ProductsData) getIntent().getSerializableExtra("product");
			options.put("description", productsData.product_title);
			options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
			options.put("currency", "INR");

			String payment = String.valueOf(getIntent().getIntExtra("total_price",0));

			double total = Double.parseDouble(payment);

			total = 100*total;

			options.put("amount", total);

			//JSONObject preFill = new JSONObject();
			//preFill.put("email", "info@mycop.in");
			//preFill.put("contact", "1234567890");

			//options.put("prefill", preFill);

			co.open(activity, options);

		} catch (Exception e) {
			Toast.makeText(PaymentActivity.this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

	@Override
	public void onPaymentSuccess(String s) {


		Log.e("payment_id",s);



		callOrderSuccess(s,invoiceid);
		//finish();


	}

	@Override
	public void onPaymentError(int i, String s) {

		Log.e("payment_id",s);
		Log.e("payment_error",String.valueOf(i));

	}
	public  void callOrderSuccess(final String paymentid,final String invoiceid){
			final ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Please Wait....");
			progressDialog.show();
			progressDialog.setCancelable(false);
			String URL = Session.BASE_URL+"api/order_success.php";

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
							Toast.makeText(PaymentActivity.this,""+reply.toString(),Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(PaymentActivity.this, ThankyouActivity.class);
							intent.putExtra("id", paymentid);
							startActivity(intent);
							finish();

						}

						else {

							String msg = jsonObject.getString("message");
							Log.e("message",""+msg);
							//Snackbar.make(gmail_btn, msg, Snackbar.LENGTH_SHORT).show();
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
							//Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
						}
					}){
				@Override
				protected Map<String,String> getParams(){
					Map<String,String> parameters = new HashMap<String, String>();
					parameters.put("invoice_id",paymentid);
					parameters.put("payment_id",invoiceid);


					return parameters;
				}
			};
			ApplicationController.getInstance().addToRequestQueue(stringRequest);

		}
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
				}
				try {
					JSONObject jsonObject=new JSONObject(response);
					String reply=jsonObject.getString("status");
					Log.e("status",""+reply);
					if(reply.equals("Success")) {
						referalmoney_payment.setText("");

					}

					else {

						String msg = jsonObject.getString("message");
						Log.e("message",""+msg);
						//Snackbar.make(gmail_btn, msg, Snackbar.LENGTH_SHORT).show();
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
						//Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
					}
				}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> parameters = new HashMap<String, String>();
				parameters.put("member_id",Session.getUserid(PaymentActivity.this));



				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
	}

}
