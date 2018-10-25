package com.yellowsoft.newproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class TrackFragment extends Fragment {
	ViewFlipper mViewflipper;
	RelativeLayout publictab,personaltab;
	LinearLayout tabone,tabtwo,tabindicator_one,tabindicator_two,submit,vehicle_details,transparent;
	TextView tv_public,tv_personal,trackingtextone,trackingtexttwo,trackingtextthree,tracksolid_url;
	TextView noresults_tv;
	ImageView playstore,appstore;

	WebView webView;

	RecyclerView track_recycler;
	ArrayList<TrackData> trackData = new ArrayList<TrackData>();
	Track_Adapter track_adapter;



	EditText vechinumber;
	public static TrackFragment newInstance(int someInt) {
		TrackFragment myFragment = new TrackFragment();

		Bundle args = new Bundle();
		args.putInt("someInt", someInt);
		myFragment.setArguments(args);

		return myFragment;
	}
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tracking, container, false);



		Log.e("trackfragment","trackfragment");
		mViewflipper = (ViewFlipper)view.findViewById(R.id.viewflipper);
		vehicle_details = (LinearLayout)view.findViewById(R.id.vehicle_details_ll);
		trackingtextone = (TextView)view.findViewById(R.id.tracking_text_one);
		trackingtexttwo = (TextView)view.findViewById(R.id.tracking_text_two);
		trackingtextthree = (TextView)view.findViewById(R.id.tracking_text_three);

		tracksolid_url = (TextView)view.findViewById(R.id.tracksolid_url_btn);
		noresults_tv = (TextView)view.findViewById(R.id.noresults_tv);

		//track recycler view
		track_recycler = (RecyclerView)view.findViewById(R.id.track_recycler);
		LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		track_recycler.setLayoutManager(linearLayoutManager);


		//track_adapter.notifyDataSetChanged();



		webView = (WebView)view.findViewById(R.id.webview);
		webView.loadUrl("http://www.tracksolid.com/mainFrame");
		webView.getSettings().setJavaScriptEnabled(true);

		vechinumber = (EditText)view.findViewById(R.id.ed_vechilenumber);

		/*vechinumber.setFilters(new InputFilter[] {new InputFilter.AllCaps()});*/


		//app links
		tracksolid_url.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://tracksolid.com/mainFrame")));
			}
		});

		playstore = (ImageView)view.findViewById(R.id.playstore_btn);
		appstore = (ImageView)view.findViewById(R.id.appstore_btn);

		playstore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/details?id=com.jimi.tuqiang.tracksolid")));
			}
		});
		appstore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://itunes.apple.com/in/app/track-solid/id1070068294?mt=8")));
			}
		});

		transparent = (LinearLayout)view.findViewById(R.id.ll_transparent);

		trackingtextone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://tracksolid.com/mainFrame")));
			}
		});

		trackingtexttwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://tracksolid.com/mainFrame")));
			}
		});

		trackingtextthree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://tracksolid.com/mainFrame")));
			}
		});


		submit = (LinearLayout)view.findViewById(R.id.ll_submit_track);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (vechinumber.getText().toString().equals("")){
					Snackbar.make(vechinumber,"Enter Vechile id or Username",Snackbar.LENGTH_SHORT).show();

				}else {
					vehicle_details.setVisibility(View.VISIBLE);
					transparent.setVisibility(View.GONE);
					callTrackingdetails();

				}

			}
		});

		tv_personal = (TextView)view.findViewById(R.id.tv_personal);
		tv_public = (TextView)view.findViewById(R.id.tv_public);

		tabindicator_one = (LinearLayout)view.findViewById(R.id.tabindicator);
		tabindicator_two = (LinearLayout)view.findViewById(R.id.tabindicator_two);

		tabone = (LinearLayout)view.findViewById(R.id.tab_one);
		tabtwo = (LinearLayout)view.findViewById(R.id.tab_two);

		final  Typeface face= Typeface.createFromAsset(getActivity().getAssets(), "fonts/gothamroundedmedium.otf");
		final  Typeface face2= Typeface.createFromAsset(getActivity().getAssets(), "fonts/gothamroundedlight.otf");

		publictab = (RelativeLayout)view.findViewById(R.id.public_tab);
		personaltab = (RelativeLayout)view.findViewById(R.id.personal_tab);

		if (mViewflipper.getDisplayedChild()==0){
			tabindicator_one.setVisibility(View.VISIBLE);
			tabindicator_two.setVisibility(View.INVISIBLE);
			tv_public.setTypeface(face);
			setShadowColor(tv_public,tv_personal);
			tv_personal.setTypeface(face2);
			tabone.setBackgroundColor(getResources().getColor(R.color.tabtransparentcolor));
			tabtwo.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
		}
		else {
			tabindicator_two.setVisibility(View.VISIBLE);
			tabindicator_one.setVisibility(View.INVISIBLE);
			tv_public.setTypeface(face2);
			tv_personal.setTypeface(face);
			setShadowColor(tv_personal,tv_public);
			tabone.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
			tabtwo.setBackgroundColor(getResources().getColor(R.color.tabtransparentcolor));
		}

		publictab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewflipper.setDisplayedChild(0);
				tabindicator_one.setVisibility(View.VISIBLE);
				tabindicator_two.setVisibility(View.INVISIBLE);
				tv_public.setTypeface(face);
				setShadowColor(tv_public,tv_personal);
				tv_personal.setTypeface(face2);
				mViewflipper.setInAnimation(getContext(),R.anim.slide_in_from_right);
				mViewflipper.setOutAnimation(getContext(),R.anim.slide_out_to_left);
				tabone.setBackgroundColor(getResources().getColor(R.color.tabtransparentcolor));
				tabtwo.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
			}
		});

		personaltab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewflipper.setDisplayedChild(1);
				tabindicator_two.setVisibility(View.VISIBLE);
				tabindicator_one.setVisibility(View.INVISIBLE);
				tv_public.setTypeface(face2);
				tv_personal.setTypeface(face);
				setShadowColor(tv_personal,tv_public);
				mViewflipper.setInAnimation(getContext(),R.anim.slide_in_from_left);
				mViewflipper.setOutAnimation(getContext(),R.anim.slide_out_to_right);
				tabone.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
				tabtwo.setBackgroundColor(getResources().getColor(R.color.tabtransparentcolor));
			}
		});




		return view;
	}
	private void setShadowColor(TextView t1, TextView t2){
		t1.setShadowLayer(1.5f,-1.0f,5.0f, Color.parseColor("#000000"));
		t2.setShadowLayer(1.5f,5.0f,5.0f, Color.parseColor("#00000000"));
	}


	//tracking details
	public void callTrackingdetails(){

		final ProgressDialog progressDialog = new ProgressDialog(getContext());
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/trackings.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("resTrack",response);
				trackData.clear();
				if(progressDialog!=null&& progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				try {

					JSONArray jsonArray = new JSONArray(response);
					Log.e("jsonArray",""+jsonArray.toString());
					if (jsonArray.length()<1){
						noresults_tv.setVisibility(View.VISIBLE);

					}
					else {
						noresults_tv.setVisibility(View.GONE);

					}





					for (int i = 0;i<jsonArray.length();i++){
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						Log.e("jsonobject",""+jsonObject);
						Log.e("jsonobjectLength",""+jsonObject.length());
						TrackData temp = new TrackData(jsonObject);
						trackData.add(temp);

					//	myOrdersData.add(temp);

					}
					track_adapter = new Track_Adapter(getContext(),trackData);
					track_recycler.setAdapter(track_adapter);

					track_adapter.notifyDataSetChanged();
				//	recycler_adapter.notifyDataSetChanged();

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

				parameters.put("trackno",vechinumber.getText().toString());
				Log.e("parameters",""+parameters+"-"+vechinumber.getText().toString());

				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
//		slidingPageAdapter.notifyDataSetChanged();
	}




}
