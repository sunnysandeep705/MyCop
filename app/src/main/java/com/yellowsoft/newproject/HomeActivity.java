package com.yellowsoft.newproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

	ListView lv_one,lv_two;
	TextView page_title_one,page_title_two,btn_edit,logout,usr_name;
	TextView membercode;
	private static int SPLASH_TIME_OUT = 3000;

	TextView contactus,tv_about,tv_returns,tv_scheme,tv_terms,tv_faq;

	String playstorelink;
	private GoogleSignInClient mGoogleSignInClient;
	private FirebaseAuth mAuth;

	LinearLayout menu_btn,home_btn,home,track_btn,track,shop_btn,shop,scheme_btn,scheme,account_btn,account;
	LinearLayout ll_userdetails,orders_ll_toolbar;

	private DrawerLayout mDrawerLayout;
	LinearLayout ll_login_,ll_details_,login_btn,signup_btn;;
	TabsAdapter tabsAdapter;
	CustomViewPager mViewPager;

	ImageView home_img,track_img,shop_img,scheme_img;
	ImageView account_img,fbbtn,gmbtn,twbtn,btn_cart;
	ImageView youtube_btn;

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);


		lv_one = (ListView)findViewById(R.id.lv_menu);
		ll_login_ = (LinearLayout)findViewById(R.id.login_ll_);
		ll_details_ = (LinearLayout)findViewById(R.id.details_ll);
		logout = (TextView)findViewById(R.id.tv_logout);
		usr_name = (TextView)findViewById(R.id.usr_name);

		membercode = (TextView)findViewById(R.id.membercode_tv);

		if (Session.getMemberCode(HomeActivity.this).equals("")){
			membercode.setVisibility(View.GONE);
		}
		else{
			membercode.setText(Session.getMemberCode(HomeActivity.this));
			membercode.setVisibility(View.VISIBLE);
		}

		try {
			 playstorelink = ApplicationController.getInstance().settings.getString("playstore");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		membercode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawer(GravityCompat.START);
				Log.e("paystorelink",""+playstorelink);
				/*Join me on My Cop App and register in their Purchase Advance Scheme to get your Referral Code. Enter my code (<Referral Code>) before making the payment. You can earn Rs. 1000/- for every successful referral.*/
				String shareBody = "Hi, I am "+Session.getUserName(HomeActivity.this)+", join me on My Cop App and register in their Purchase Advance Scheme to get your Referral Code. Enter my code ("+ Session.getMemberCode(HomeActivity.this)+") before making the payment. You can earn Rs. 1000/- for every successful referral. "+playstorelink;
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
				sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share with");
				startActivity(sharingIntent);
			}
		});


		//menu two
		contactus = (TextView)findViewById(R.id.tv_contactus);
		tv_about = (TextView)findViewById(R.id.tv_about);
		tv_returns = (TextView)findViewById(R.id.tv_returns);
		tv_scheme = (TextView)findViewById(R.id.tv_scheme);
		tv_terms = (TextView)findViewById(R.id.tv_terms);
		tv_faq = (TextView)findViewById(R.id.tv_faq);

		/*

		menu two onclick

		*/

		contactus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,ContactusActivity.class);
				startActivity(intent);
				mDrawerLayout.closeDrawer(GravityCompat.START);
			}
		});

		tv_about.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawer(GravityCompat.START);
				Intent intent = new Intent(HomeActivity.this,ContentActivity.class);
				intent.putExtra("title","About us");
				try {
					intent.putExtra("content",ApplicationController.getInstance().settings.getString("about"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				startActivity(intent);


			/*	try {
					showAlert(ApplicationController.getInstance().settings.getString("about"),"Scheme Details");
				} catch (JSONException e) {
					e.printStackTrace();
				}*/
			}
		});


		tv_returns.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawer(GravityCompat.START);

				Intent intent = new Intent(HomeActivity.this,ContentActivity.class);
				intent.putExtra("title","Returns & Referrals");
				try {
					intent.putExtra("content",ApplicationController.getInstance().settings.getString("returns"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				startActivity(intent);

			}
		});


		tv_scheme.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawer(GravityCompat.START);
				/*resetAllColors();
				changebg(scheme_btn,scheme_img);
				mViewPager.setCurrentItem(3);
				page_title_two.setVisibility(View.VISIBLE);
				page_title_one.setText("REFERRAL");
				mDrawerLayout.closeDrawer(GravityCompat.START);
				page_title_two.setText("SCHEME");


*/
				Intent intent = new Intent(HomeActivity.this,ContentActivity.class);
				intent.putExtra("title","Scheme Details");
				try {
					intent.putExtra("content",ApplicationController.getInstance().settings.getString("content_scheme_details"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				startActivity(intent);
				/*try {
					showAlert(ApplicationController.getInstance().settings.getString("scheme_details"),"Scheme Details");
				} catch (JSONException e) {
					e.printStackTrace();
				}*/
				//show alert

			}
		});


		tv_terms.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawer(GravityCompat.START);
				Intent intent = new Intent(HomeActivity.this,ContentActivity.class);
				intent.putExtra("title","Terms & Conditions");
				try {
					intent.putExtra("content",ApplicationController.getInstance().settings.getString("terms"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				startActivity(intent);
				/*resetAllColors();
				changebg(scheme_btn,scheme_img);
				mViewPager.setCurrentItem(3);
				page_title_two.setVisibility(View.VISIBLE);
				mDrawerLayout.closeDrawer(GravityCompat.START);
				page_title_one.setText("REFERRAL");
				page_title_two.setText("SCHEME");*/
				/*try {
					showAlert(ApplicationController.getInstance().settings.getString("terms"),"Terms & Conditions");
				} catch (JSONException e) {
					e.printStackTrace();
				}*/
			}
		});


		tv_faq.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawer(GravityCompat.START);
				Intent intent = new Intent(HomeActivity.this,ContentActivity.class);
				intent.putExtra("title","FAQ's");
				try {
					intent.putExtra("content",ApplicationController.getInstance().settings.getString("faq"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				startActivity(intent);
			/*	resetAllColors();
				changebg(scheme_btn,scheme_img);
				mViewPager.setCurrentItem(3);
				page_title_two.setVisibility(View.VISIBLE);
				mDrawerLayout.closeDrawer(GravityCompat.START);
				page_title_one.setText("REFERRAL");
				page_title_two.setText("SCHEME");*/
				/*try {
					showAlert(ApplicationController.getInstance().settings.getString("faq"),"FAQ's");
				} catch (JSONException e) {
					e.printStackTrace();
				}*/
			}
		});

		/*

		menu two onclick finished

		*/


		if(Session.getUserid(HomeActivity.this).equals("0")) {
			showLoginLayout();
		Log.e("memberid","0");

		}
		else {

			hideLoginLayout();

		}



		String name = Session.getUserName(HomeActivity.this);
		usr_name.setText(name);
		Log.e("name",""+name);


		// [START initialize_auth]
		mAuth = FirebaseAuth.getInstance();
		// [END initialize_auth]

		//social links
		fbbtn = (ImageView)findViewById(R.id.fb_btn);
		gmbtn = (ImageView)findViewById(R.id.gm_btn);
		twbtn = (ImageView)findViewById(R.id.tw_btn);
		youtube_btn = (ImageView)findViewById(R.id.youtube_img_btn);

		fbbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String fb= ApplicationController.getInstance().settings.getString("facebook");
					startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(fb)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				mDrawerLayout.closeDrawer(GravityCompat.START);

			}
		});

		gmbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					String fb= ApplicationController.getInstance().settings.getString("google");
					startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(fb)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				mDrawerLayout.closeDrawer(GravityCompat.START);

				//startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.googleplus.com")));
				//google,twitter,youtube
			}
		});

		twbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					String fb= ApplicationController.getInstance().settings.getString("twitter");
					startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(fb)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				mDrawerLayout.closeDrawer(GravityCompat.START);

				//startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.twitter.com")));
			}
		});

		youtube_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					String fb= ApplicationController.getInstance().settings.getString("youtube");
					startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(fb)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				mDrawerLayout.closeDrawer(GravityCompat.START);
				//startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.youtube.com")));
			}
		});

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		setupActionBar();
		setupHeader();
		tabsAdapter = new TabsAdapter(getSupportFragmentManager(),this);
		mViewPager = (CustomViewPager) findViewById(R.id.home_viewpager);
		mViewPager.setOffscreenPageLimit(0);

		mViewPager.setAdapter(tabsAdapter);


		home_img = (ImageView)findViewById(R.id.home_img);
		track_img = (ImageView)findViewById(R.id.track_img);
		shop_img = (ImageView)findViewById(R.id.shop_img);
		scheme_img = (ImageView)findViewById(R.id.scheme_img);
		account_img = (ImageView)findViewById(R.id.account_img);

		home_btn = (LinearLayout)findViewById(R.id.ll_home);
		home = (LinearLayout)findViewById(R.id.home);
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				resetAllColors();
				changebg(home_btn,home_img);
				mViewPager.setCurrentItem(0);
				page_title_two.setVisibility(View.GONE);
				page_title_one.setText("HOME");
				orders_ll_toolbar.setVisibility(View.INVISIBLE);


			}
		});

		if (mViewPager.getCurrentItem()==0){
			changebg(home_btn,home_img);
			page_title_one.setText("HOME");
			page_title_two.setVisibility(View.GONE);
			orders_ll_toolbar.setVisibility(View.INVISIBLE);
		}

		track_btn = (LinearLayout)findViewById(R.id.ll_track);
		track = (LinearLayout)findViewById(R.id.track);
		track.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				resetAllColors();
				changebg(track_btn,track_img);
				mViewPager.setCurrentItem(1);

				page_title_two.setVisibility(View.VISIBLE);
				page_title_one.setText("VEHICLE");
				page_title_two.setText("TRACKING");
				orders_ll_toolbar.setVisibility(View.INVISIBLE);
			}
		});

		shop_btn = (LinearLayout)findViewById(R.id.ll_shop);
		shop = (LinearLayout)findViewById(R.id.shop);
		shop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				resetAllColors();
				changebg(shop_btn,shop_img);
				mViewPager.setCurrentItem(2);
				page_title_two.setVisibility(View.VISIBLE);
				page_title_one.setText("BUY GPS");
				page_title_two.setText("TRACKER");

				orders_ll_toolbar.setVisibility(View.VISIBLE);
			}
		});

		scheme_btn = (LinearLayout)findViewById(R.id.ll_scheme);
		scheme = (LinearLayout)findViewById(R.id.scheme);
		scheme.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				orders_ll_toolbar.setVisibility(View.INVISIBLE);

				String memberid= Session.getUserid(HomeActivity.this);
				Log.e("membercode",""+Session.getUserid(HomeActivity.this));
				if (memberid.equals("0")){

					//((HomeActivity)getActivity()).schemeSelected();
					mViewPager.setCurrentItem(3);
					page_title_two.setVisibility(View.VISIBLE);
					page_title_one.setText("REFERRAL");

					page_title_two.setText("SCHEME");

					resetAllColors();
					changebg(scheme_btn,scheme_img);
				}
				else {
					if (Session.getMemberCode(HomeActivity.this).equals("")){
						Log.e("memberCode",""+Session.getMemberCode(HomeActivity.this));
						//((HomeActivity)getActivity()).schemeSelected();
						mViewPager.setCurrentItem(3);
						page_title_two.setVisibility(View.VISIBLE);
						page_title_one.setText("REFERRAL");
						page_title_two.setText("SCHEME");

						resetAllColors();
						changebg(scheme_btn,scheme_img);
					} else {

						Log.e("membercode",""+Session.getMemberCode(HomeActivity.this));
						Intent intent = new Intent(HomeActivity.this,MyreferalsActivity.class);

						startActivity(intent);
						//getActivity().finish();
					}
				}

			}
		});

		account_btn = (LinearLayout)findViewById(R.id.ll_account);
		account = (LinearLayout)findViewById(R.id.account);
		account.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				orders_ll_toolbar.setVisibility(View.INVISIBLE);

				if(Session.getUserid(HomeActivity.this).equals("0")){

					Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
					startActivity(intent);


				}else {
					resetAllColors();
					changebg(account_btn, account_img);
					mViewPager.setCurrentItem(4);
					page_title_two.setVisibility(View.VISIBLE);
					page_title_one.setText("MY");
					page_title_two.setText("ACCOUNT");

				}



			}
		});




		//LOGOUT

		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//showLoginLayout();
				finish();
				startActivity(getIntent());
				signout();
			}
		});



		//LOGIN
		login_btn = (LinearLayout)findViewById(R.id.ll_login);
		login_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
				startActivity(intent);

				finish();
				//hideLoginLayout();
			}
		});
		signup_btn = (LinearLayout)findViewById(R.id.ll_signup);
		signup_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(HomeActivity.this,SignupActivity.class);
				startActivity(intent);

				finish();
			//	hideLoginLayout();
			}
		});

		ll_login_ = (LinearLayout)findViewById(R.id.login_ll_);
		ll_details_ = (LinearLayout)findViewById(R.id.details_ll);
		//lv_two = (ListView)findViewById(R.id.lv_menutwo);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_menu);

		ArrayList<MenuItem> menuItems = new ArrayList<>();
		if(Session.getMemberCode(HomeActivity.this).equals("")) {
			menuItems.add(new MenuItem("My Orders", "", R.drawable.myorders));
			menuItems.add(new MenuItem("My Profile", "", R.drawable.myprofile));
		}
		else {
			menuItems.add(new MenuItem("My Orders", "", R.drawable.myorders));
			menuItems.add(new MenuItem("My Profile", "", R.drawable.myprofile));
			menuItems.add(new MenuItem("My Referrals", "", R.drawable.myreferals));
			menuItems.add(new MenuItem("My Earnings", "", R.drawable.myernings));
			menuItems.add(new MenuItem("My Noitifications","",R.drawable.notification));


		}

		MenuAdapter menuAdapter = new MenuAdapter(this,menuItems);
		lv_one.setAdapter(menuAdapter);

		//menu one onclick
		lv_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position==0){
					mDrawerLayout.closeDrawer(GravityCompat.START);

					Intent intent = new Intent(HomeActivity.this,MyOrdersActivity.class);
					startActivity(intent);
				}
				else if (position==1){
					mDrawerLayout.closeDrawer(GravityCompat.START);
					Intent intent = new Intent(HomeActivity.this,MyProfileActivity.class);
					startActivity(intent);
				}
				else if (position==2){mDrawerLayout.closeDrawer(GravityCompat.START);
					Intent intent = new Intent(HomeActivity.this,MyreferalsActivity.class);
					startActivity(intent);
				}
				else if (position==3)
				{mDrawerLayout.closeDrawer(GravityCompat.START);
					Intent intent = new Intent(HomeActivity.this,MyearningsActivity.class);
					startActivity(intent);
				}
				else {mDrawerLayout.closeDrawer(GravityCompat.START);
					Intent intent = new Intent(HomeActivity.this,NotificationsActivity.class);
					startActivity(intent);

				}
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
		View v = inflater.inflate(R.layout.action_bar_title,null);


		orders_ll_toolbar = (LinearLayout)v.findViewById(R.id.orders_ll_toolbar);
		orders_ll_toolbar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,MyOrdersActivity.class);
				startActivity(intent);
			}
		});
		//cart button on click
		btn_cart = (ImageView)v.findViewById(R.id.btn_cart);
		/*btn_cart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,MyOrdersActivity.class);
				startActivity(intent);
			}
		});*/


		page_title_one = (TextView) v.findViewById(R.id.page_title_one);
		page_title_two  = (TextView)v.findViewById(R.id.page_title_two);


		menu_btn = (LinearLayout) v.findViewById(R.id.btn_menu_container);
		/*shop_img = (ImageView)v.findViewById(R.id.btn_shop);*/
		menu_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDrawerLayout.openDrawer(GravityCompat.START);
			}
		});

		/*back_btn = (LinearLayout) v.findViewById(R.id.btn_back_container);
		back_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
*/
		/*btn_edit = (TextView) v.findViewById(R.id.tv_edit);
		btn_edit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(HomeActivity.this,Payments.class);//delete Payments and add ActivitySearch
				startActivity(intent);
			}

		});
*/
		getSupportActionBar().setCustomView(v, layoutParams);
		Toolbar parent = (Toolbar) v.getParent();

		parent.setContentInsetsAbsolute(0, 0);


	}
	private void setupHeader(){
		page_title_two.setVisibility(View.GONE);
		//shop_img.setVisibility(View.VISIBLE);
		//back_btn.setVisibility(View.GONE);
		//btn_edit.setVisibility(View.VISIBLE);
		//btn_edit.setText("Search");
		//page_title.setText("Home");

	}
	public void showLoginLayout(){
		ll_login_.setVisibility(View.VISIBLE);
		logout.setVisibility(View.GONE);
		ll_details_.setVisibility(View.GONE);
	}
	public void hideLoginLayout(){
		ll_login_.setVisibility(View.GONE);
		logout.setVisibility(View.VISIBLE);
		ll_details_.setVisibility(View.VISIBLE);
	}
	public  void changebg(LinearLayout l,ImageView i){
		l.setBackgroundResource(R.drawable.rounded_corners_white);
		i.setColorFilter(home_img.getContext().getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

	}
	public void resetAllColors(){
		home_btn.setBackgroundColor(Color.parseColor("#00000000"));
		home_img.setColorFilter(home_img.getContext().getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

		track_btn.setBackgroundColor(Color.parseColor("#00000000"));
		track_img.setColorFilter(home_img.getContext().getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

		shop_btn.setBackgroundColor(Color.parseColor("#00000000"));
		shop_img.setColorFilter(home_img.getContext().getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

		scheme_btn.setBackgroundColor(Color.parseColor("#00000000"));
		scheme_img.setColorFilter(home_img.getContext().getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

		account_btn.setBackgroundColor(Color.parseColor("#00000000"));
		account_img.setColorFilter(home_img.getContext().getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

	}


	public void vehicleTrackingSelected(){
		mViewPager.setCurrentItem(1);
		page_title_two.setVisibility(View.VISIBLE);
		resetAllColors();
		changebg(track_btn,track_img);
		page_title_one.setText("VEHICLE");
		page_title_two.setText("TRACKING");
		orders_ll_toolbar.setVisibility(View.INVISIBLE);
	}
	public void buyGPStracker(){
		resetAllColors();
		changebg(shop_btn,shop_img);
		mViewPager.setCurrentItem(2);
		page_title_two.setVisibility(View.VISIBLE);
		page_title_one.setText("BUY GPS");
		page_title_two.setText("TRACKER");
		orders_ll_toolbar.setVisibility(View.VISIBLE);
	}

	public void schemeSelected(){
		resetAllColors();
		changebg(scheme_btn,scheme_img);
		mViewPager.setCurrentItem(3);
		page_title_two.setVisibility(View.VISIBLE);
		page_title_one.setText("REFERRAL");
		page_title_two.setText("SCHEME");
		orders_ll_toolbar.setVisibility(View.INVISIBLE);
	}
	public void accountfrg(){
		resetAllColors();
		changebg(account_btn,account_img);
		mViewPager.setCurrentItem(4);
		page_title_two.setVisibility(View.VISIBLE);
		page_title_one.setText("MY");
		page_title_two.setText("ACCOUNT");
		orders_ll_toolbar.setVisibility(View.INVISIBLE);
	}



	//google signout
	public void signout(){
		Session.setUserid(HomeActivity.this,"0","MYCOP USER");
		Session.setMemberCode(HomeActivity.this,"0");

	}


int resume_count = 0;
	@Override
	public void onResume(){
		super.onResume();
		// put your code here...
		if(resume_count>0){

			if(mViewPager.getCurrentItem()==3)
			//home.performClick();

			if(Session.getUserid(HomeActivity.this).equals("0")) {
				showLoginLayout();
				Log.e("memberid","0");

			}
			else {

				hideLoginLayout();

			}


		}

		resume_count++;


	}


/*	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.e("buygpsclicke","act");

		if(requestCode==888){

			if(resultCode==RESULT_OK){

				buyGPStracker();
				//shop_btn.performClick();
				Log.e("buygpsclicke","frag");

				try {

					shop_btn.performClick();
				}catch (Exception ex){
					ex.printStackTrace();
				}

			}
		}

	}*/

	public void showAlert(final String message,final String title){
		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
		alertDialog.setMessage(Html.fromHtml(message));
		alertDialog.setTitle(title);
		alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.setCancelable(true);
			}
		});
		alertDialog.show();
	}

}
