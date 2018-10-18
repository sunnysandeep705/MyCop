package com.yellowsoft.newproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
	private ProgressInterface progressInterface;
	HomeAdapter homeAdapter;
	ListView home_list;
	ArrayList<MenuItem> menuItems = new ArrayList<>();
	RelativeLayout vechile_tracking,buygps,scheme,myaccount;
	/*@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try {
			progressInterface = (ProgressInterface) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString() + e.getMessage());
		}
	}*/


	public static HomeFragment newInstance(int someInt) {
		HomeFragment myFragment = new HomeFragment();

		Bundle args = new Bundle();
		args.putInt("someInt", someInt);
		myFragment.setArguments(args);

		return myFragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);

		Log.e("homefragment","homefragment");
		home_list = (ListView)view.findViewById(R.id.home_listview);



		vechile_tracking = (RelativeLayout)view.findViewById(R.id.rl_vehicletracking);
		vechile_tracking.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//.makeText(getContext(),"Vehicle Tracking selected",Toast.LENGTH_LONG).show();

				((HomeActivity)getActivity()).vehicleTrackingSelected();

			}
		});
		buygps = (RelativeLayout)view.findViewById(R.id.rl_buygps);
		buygps.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((HomeActivity)getActivity()).buyGPStracker();
			}
		});

		scheme =(RelativeLayout)view.findViewById(R.id.rl_referalscheme);
		scheme.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String memberid = Session.getUserid(getActivity());
				String membercode = Session.getMemberCode(getActivity());
				if (memberid.equals("0")){

					((HomeActivity)getActivity()).schemeSelected();
				}
				else {
					if (Session.getMemberCode(getContext()).equals("")){
						((HomeActivity)getActivity()).schemeSelected();
					}
					else {
						Intent intent = new Intent(getActivity(),MyreferalsActivity.class);
						startActivity(intent);
						getActivity().finish();
					}
				}
			}
		});

		myaccount = (RelativeLayout)view.findViewById(R.id.rl_myaccount);
		myaccount.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String memberid = Session.getUserid(getActivity());
				if (memberid.equals("0")) {

					Intent intent = new Intent(getActivity(), LoginActivity.class);
					startActivity(intent);
				} else {
					((HomeActivity) getActivity()).accountfrg();
				}
			}
		});



		/*menuItems = new ArrayList<MenuItem>();
		menuItems.add(new MenuItem("Vechile","Traking",R.drawable.vehicletracking));
		menuItems.add(new MenuItem("Buy GPS","Traker",R.drawable.buygps));
		menuItems.add(new MenuItem("Referal","Scheme",R.drawable.referal));
		menuItems.add(new MenuItem("MY","Account",R.drawable.myaccount));

		homeAdapter = new HomeAdapter(getActivity(),menuItems);
		home_list.setAdapter(homeAdapter);*/



		return view;

	}


}
