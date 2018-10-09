package com.yellowsoft.newproject;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

public class TrackFragment extends Fragment {
	ViewFlipper mViewflipper;
	RelativeLayout publictab,personaltab;
	LinearLayout tabone,tabtwo,tabindicator_one,tabindicator_two,submit,vehicle_details,transparent;
	TextView tv_public,tv_personal,trackingtextone,trackingtexttwo,trackingtextthree,tracksolid_url;
	ImageView playstore,appstore;

	WebView webView;

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

		webView = (WebView)view.findViewById(R.id.webview);
		webView.loadUrl("http://www.tracksolid.com/mainFrame");
		webView.getSettings().setJavaScriptEnabled(true);

		vechinumber = (EditText)view.findViewById(R.id.ed_vechilenumber);

		vechinumber.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
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
				if (vechinumber!=null){
					vehicle_details.setVisibility(View.VISIBLE);
					transparent.setVisibility(View.GONE);
				}else {
					Snackbar.make(vechinumber,"Enter Vechile number",Snackbar.LENGTH_SHORT).show();

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
}
