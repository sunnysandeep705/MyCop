package com.yellowsoft.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

import com.facebook.login.Login;

import org.json.JSONException;

public class SchemeFragment extends Fragment {

	ImageView checked_btn,unchecked_btn,scheme_btn,faq_btn,terms_btn,close_btn;
	TextView popup_title,popup_content,terms_tv;
	LinearLayout popup;
	RelativeLayout joinnow;


	@Override
	public void onStart() {
		super.onStart();
		String memberid = Session.getUserid(getActivity());
		String membercode = Session.getMemberCode(getActivity());



	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_scheme, container, false);
		Log.e("schemefragment","schemefragment");



		terms_tv = (TextView)view.findViewById(R.id.agree_terms_tv);

		checked_btn = (ImageView)view.findViewById(R.id.checked_img);
		unchecked_btn = (ImageView)view.findViewById(R.id.unchecked_img);

		scheme_btn = (ImageView)view.findViewById(R.id.scheme_img_btn);
		faq_btn = (ImageView)view.findViewById(R.id.faq_img);
		terms_btn = (ImageView)view.findViewById(R.id.terms_img);

		joinnow = (RelativeLayout)view.findViewById(R.id.joinnow_ll);
		joinnow.setBackgroundResource(R.drawable.roundedconrners_tabtransparentcolor);

		popup = (LinearLayout)view.findViewById(R.id.popup_ll);
		popup.setVisibility(View.GONE);
		close_btn = (ImageView)view.findViewById(R.id.close_img);
		popup_title = (TextView)view.findViewById(R.id.popup_title_tv);
		popup_content = (TextView)view.findViewById(R.id.popup_content_tv);

		close_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ClosePop();
			}
		});
		checked_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checked_btn.setVisibility(View.GONE);
				unchecked_btn.setVisibility(View.VISIBLE);
				joinnow.setBackgroundResource(R.drawable.roundedconrners_tabtransparentcolor);
			}
		});

		unchecked_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				unchecked_btn.setVisibility(View.GONE);
				checked_btn.setVisibility(View.VISIBLE);
				joinnow.setBackgroundResource(R.drawable.rounded_corners);
			}
		});

		joinnow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (unchecked_btn.getVisibility()==View.VISIBLE||checked_btn.getVisibility()==View.GONE){
					Snackbar.make(scheme_btn,"Please accept Terms & Conditions",Snackbar.LENGTH_SHORT).show();
				}
				else {
					//Intent intent = new Intent(getActivity(),CartActivity.class);
					String member_id = Session.getUserid(getActivity());
					if (member_id.equals("0")) {
						Intent intent = new Intent(getActivity(), LoginActivity.class);

						startActivity(intent);
					} else {

					Intent intent = new Intent(getActivity(), ReferalsActivity.class);
					//intent.putExtra("quantity","1");
					startActivity(intent);
				}
				}
			}
		});


		terms_tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String terms = ApplicationController.getInstance().settings.getString("terms");
					ShowpopupWindow("Terms",terms);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});

		scheme_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//ShowpopupWindow("Scheme details","schemedeatils");

				try {
					String scheme = ApplicationController.getInstance().settings.getString("scheme_details");
					ShowpopupWindow("Scheme details",scheme);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});

		faq_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String faq =ApplicationController.getInstance().settings.getString("faq");
					Log.e("faq",""+faq);
					ShowpopupWindow("FAQ's",faq);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});

		terms_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String terms = ApplicationController.getInstance().settings.getString("terms");
					ShowpopupWindow("Terms",terms);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});


		return view;
	}
	public static SchemeFragment newInstance(int someInt) {
		SchemeFragment myFragment = new SchemeFragment();

		Bundle args = new Bundle();
		args.putInt("someInt", someInt);
		myFragment.setArguments(args);

		return myFragment;
	}

public void ShowpopupWindow(String title,String content){
		popup_title.setText(title);
		popup.setVisibility(View.VISIBLE);
		popup_content.setText(Html.fromHtml("<p>"+content+"</p>"));
}
public void ClosePop(){
	popup.setVisibility(View.GONE);
}

}
