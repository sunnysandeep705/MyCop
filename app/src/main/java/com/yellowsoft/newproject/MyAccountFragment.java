package com.yellowsoft.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MyAccountFragment extends Fragment {

	ListView listView;
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_myaccount, container, false);
		listView = (ListView)view.findViewById(R.id.lv_myaccount);

		ArrayList<MenuItem> menuItems = new ArrayList<>();
		menuItems.add(new MenuItem("My Orders","",R.drawable.myorders));
		menuItems.add(new MenuItem("My Profile","",R.drawable.myprofile));
		menuItems.add(new MenuItem("My Referals","",R.drawable.myreferals));
		menuItems.add(new MenuItem("My Earnings","",R.drawable.myernings));


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
					startActivity(intent);
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
}
