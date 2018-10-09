package com.yellowsoft.newproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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
import java.util.Timer;
import java.util.TimerTask;

public class ShopFragment extends Fragment {
	ViewPager viewPager;
	TextView striketext, quantity, title, discount_price, originalprice_tv, description_tv;
	TabLayout indicator_tab;

	SlidingImageAdapter slidingPageAdapter;
	Integer i;

	ArrayList<SlidingImage_Data> slidingImage_data = new ArrayList<SlidingImage_Data>();
	int showdes = 1;
	RelativeLayout decrease_btn, increase_btn;

	LinearLayout itemdesc_btn, itemdesc_text, cart_btn;
	ImageView up_img, down_img;

	public static ShopFragment newInstance(int someInt) {
		ShopFragment myFragment = new ShopFragment();

		Bundle args = new Bundle();
		args.putInt("someInt", someInt);
		myFragment.setArguments(args);

		return myFragment;
	}

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_product, container, false);
		Log.e("shopfragment", "shopfragment");
		//
		CallProductdetails();

		title = (TextView) view.findViewById(R.id.mycop_title_tv);
		striketext = (TextView) view.findViewById(R.id.strike_tv);
		//striketext.setPaintFlags(striketext.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
		quantity = (TextView) view.findViewById(R.id.quantity_tv);
		discount_price = (TextView) view.findViewById(R.id.discounted_price);
		originalprice_tv = (TextView) view.findViewById(R.id.originalprice_tv);
		description_tv = (TextView) view.findViewById(R.id.description_tv);

		viewPager = (ViewPager) view.findViewById(R.id.image_slider);

		indicator_tab = (TabLayout) view.findViewById(R.id.indicator);


		increase_btn = (RelativeLayout) view.findViewById(R.id.rl_increase);
		decrease_btn = (RelativeLayout) view.findViewById(R.id.rl_decrease);
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
		final Intent intent = new Intent(getContext(), CartActivity.class);

		cart_btn = (LinearLayout) view.findViewById(R.id.addtocart_ll_btn);
		cart_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//
				// Toast.makeText(getContext(),"go to cart",Toast.LENGTH_LONG).show();

				String q, p;
				q = quantity.getText().toString();
				intent.putExtra("quantity", q);

				intent.putExtra("discountedPrice", discount_price.toString());
				startActivity(intent);
				//getActivity().finish();

			}
		});

		itemdesc_btn = (LinearLayout) view.findViewById(R.id.itemdesc_ll_btn);
		itemdesc_text = (LinearLayout) view.findViewById(R.id.itemdesc_ll);


	/*	slidingImage_data.add(new SlidingImage_Data(R.drawable.product1));
		slidingImage_data.add(new SlidingImage_Data(R.drawable.product2));
		slidingImage_data.add(new SlidingImage_Data(R.drawable.product3));*/

		slidingPageAdapter = new SlidingImageAdapter(getActivity(), slidingImage_data);

		viewPager.setAdapter(slidingPageAdapter);

		indicator_tab.setupWithViewPager(viewPager, true);
/*
		final Handler handler = new Handler();

		final 	Runnable update = new Runnable() {
			public void run() {
				if (viewPager.getCurrentItem() < slidingImage_data.size() - 1) {
					viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
				}
				else {
					viewPager.setCurrentItem(0);
				}

			}
		};


		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				handler.post(update);
			}
		}, 3000, 5000);*/

		//Snackbar.make(quantity,"shop fragment selected",Snackbar.LENGTH_SHORT).show();
		return view;
	}

	public void showDescription() {
		itemdesc_text.setVisibility(View.VISIBLE);
		up_img.setVisibility(View.GONE);
		down_img.setVisibility(View.VISIBLE);
	}

	public void closeDescription() {
		itemdesc_text.setVisibility(View.GONE);
		down_img.setVisibility(View.GONE);
		up_img.setVisibility(View.VISIBLE);
	}

	//product request
	public void CallProductdetails() {

		final ProgressDialog progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL + "api/products.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("res", response);
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				try {

					JSONArray jsonArray = new JSONArray(response);
					Log.e("jsonArray", "" + jsonArray.toString());

					String producttitle = jsonArray.getJSONObject(0).getString("title");
					Log.e("pagetitletitle", "" + producttitle);
					title.setText(producttitle);


					String price = jsonArray.getJSONObject(0).getString("price");
					Log.e("price", "" + price);
					discount_price.setText(price);
					Session.setPrice(getContext(), price);


					String discount = jsonArray.getJSONObject(0).getString("discount");
					Log.e("discount", "" + discount);
					String givenprice = discount + price;
					int i = Integer.parseInt(discount) + Integer.parseInt(price);
					originalprice_tv.setText("" + i);

					String quantitys = jsonArray.getJSONObject(0).getString("quantity");
					Log.e("quantity", "" + quantitys);
					quantity.setText(quantitys);


					String description = jsonArray.getJSONObject(0).getString("description");
					Log.e("description", "" + description);
					description_tv.setText(Html.fromHtml("<p>" + description + "</p>"));

					String images = jsonArray.getJSONObject(0).getString("images");
					Log.e("images", "" + images);

					JSONArray jsonArray1 = new JSONArray(images);
					Log.e("jsonarray1", "" + jsonArray1);
					Log.e("jsonarray1length", "" + jsonArray1.length());
					//	Log.e("imagessssss",""+jsonArray1.getJSONObject(0).getString("image"));

					if (jsonArray1.length() > 1) {
						Log.e("length", "length");
						for (int j = 0; j <= jsonArray1.length(); j++) {
							//slidingImage_data.add(new SlidingImage_Data(jsonArray.getJSONObject(j).getString("image")));
							String s = jsonArray1.getString(j);
							Log.e("s", "" + s);
							slidingImage_data.add(new SlidingImage_Data(s));
							Log.e("imagessssss", "" + jsonArray1.getString(j));

						}

					}


				} catch (JSONException e) {
					e.printStackTrace();
				}
				slidingPageAdapter.notifyDataSetChanged();
			}
		},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("error", "" + error);
						if (progressDialog != null)
							progressDialog.dismiss();
						//Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> parameters = new HashMap<String, String>();
				//parameters.put("email",u_name.getText().toString());
				//	parameters.put("password",password.getText().toString());
				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
//		slidingPageAdapter.notifyDataSetChanged();
	}
}
