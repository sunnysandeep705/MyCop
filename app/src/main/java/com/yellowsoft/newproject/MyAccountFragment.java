package com.yellowsoft.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class MyAccountFragment extends Fragment {

	ListView listView;
	TextView tv_my_ref_code,tv_username_myaccount;

	String playstorelink;
	LinearLayout referralcode_ll;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_myaccount, container, false);
		listView = (ListView)view.findViewById(R.id.lv_myaccount);


		try {
			playstorelink = ApplicationController.getInstance().settings.getString("playstore");
		} catch (JSONException e) {
			e.printStackTrace();
		}


		tv_my_ref_code = (TextView) view.findViewById(R.id.myreferalcode_tv);
		tv_my_ref_code.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String shareBody = "Hi, I am "+Session.getUserName(getActivity())+", join me on My Cop App and register in their Purchase Advance Scheme to get your Referral Code. Enter my code ("+ Session.getMemberCode(getActivity())+") before making the payment. You can earn Rs. 1000/- for every successful referral."+playstorelink;

				//String shareBody = "Hi, I am "+Session.getUserName(getActivity())+", use my referral code: "+""+ Session.getMemberCode(getActivity());
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
				sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share with");
				startActivity(sharingIntent);
			}
		});


		referralcode_ll = (LinearLayout)view.findViewById(R.id.referralcode_ll);

		tv_username_myaccount = (TextView)view.findViewById(R.id.tv_username_myaccount);
		String name = Session.getUserName(getContext());
		tv_username_myaccount.setText(name);


		tv_my_ref_code.setText(Session.getMemberCode(getContext()));
		Log.e("membercode",""+Session.getMemberCode(getContext()));

		ArrayList<MenuItem> menuItems = new ArrayList<>();
		if(Session.getMemberCode(getContext()).equals("")){
			menuItems.add(new MenuItem("My Orders","",R.drawable.myorders));
			menuItems.add(new MenuItem("My Profile","",R.drawable.myprofile));
			referralcode_ll.setVisibility(View.GONE);

		}else {
			menuItems.add(new MenuItem("My Orders", "", R.drawable.myorders));
			menuItems.add(new MenuItem("My Profile", "", R.drawable.myprofile));
			menuItems.add(new MenuItem("My Referals", "", R.drawable.myreferals));
			menuItems.add(new MenuItem("My Earnings", "", R.drawable.myernings));
		}

		MenuAdapter_MyAccount menuAdapter_myAccount = new MenuAdapter_MyAccount(getContext(),menuItems);
		listView.setAdapter(menuAdapter_myAccount);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position==0){
					Intent intent = new Intent(getContext(),MyOrdersActivity.class);
					startActivity(intent);
				}
				else if (position==1){
					Intent intent = new Intent(getContext(),MyProfileActivity.class);
					startActivity(intent);
				}
				else if (position==2){
					Intent intent = new Intent(getContext(),MyreferalsActivity.class);
					startActivity(intent);
				}
				else
				{
					Intent intent = new Intent(getContext(),MyearningsActivity.class);
					startActivityForResult(intent,888);
				}

			}
		});
		return view;
	}
	public static MyAccountFragment newInstance(int someInt) {
		MyAccountFragment myFragment = new MyAccountFragment();

		Bundle args = new Bundle();
		args.putInt("someInt", someInt);
		myFragment.setArguments(args);

		return myFragment;
	}


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

		Log.e("buygpsclicke","frag");

        if(requestCode==888){
            if(resultCode==RESULT_OK){
				((HomeActivity) getActivity()).buyGPStracker();
                //shop_btn.performClick();
                Log.e("buygpsclick","frag");

                try {

					((HomeActivity) getActivity()).shop_btn.performClick();
				}catch (Exception ex){
                	ex.printStackTrace();
				}

            }
        }

    }

}
