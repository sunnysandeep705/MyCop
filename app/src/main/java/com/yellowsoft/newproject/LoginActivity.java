package com.yellowsoft.newproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;

import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;




public class LoginActivity extends AppCompatActivity {
	TextView page_title,btn_edit,logout,signup_btn,tv_forget;
	LinearLayout menu_btn,back_btn,submit_btn;
	private GoogleSignInClient mGoogleSignInClient;
	private static final int RC_SIGN_IN = 9001;
	private static final int FB_SIGN_IN = 9002;
	LoginButton loginButton;
	CallbackManager mCallbackManager;
	private FirebaseAuth.AuthStateListener mAuthListener;
	EditText u_name,password;

	RelativeLayout gmail_btn,fb_btn;
	// [START declare_auth]
	private FirebaseAuth mAuth;

	private static final String TAG = "GoogleActivity";

	private static final String EMAIL = "email";
	ImageView back;

	@Override
	public void onBackPressed() {
		super.onBackPressed();

//		Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
//		startActivity(intent);
		finish();
	}

	@Override
	protected void onStart() {
		super.onStart();
		FirebaseUser currentUser = mAuth.getCurrentUser();
		if (currentUser!=null) {
			Log.e("currentUser", "" + currentUser.getDisplayName());
			mAuth.signOut();
		}
		else {
			Log.e("currentUser","= null");
		}
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		u_name = (EditText) findViewById(R.id.et_usrnmane);


		password = (EditText)findViewById(R.id.et_password);
		loginButton = (LoginButton)findViewById(R.id.login_button);
		gmail_btn = (RelativeLayout)findViewById(R.id.rr_gm);
		fb_btn = (RelativeLayout)findViewById(R.id.rr_fb);
		submit_btn = (LinearLayout)findViewById(R.id.ll_submit_login);
		signup_btn = (TextView) findViewById(R.id.tv_signup_login);

		tv_forget = (TextView)findViewById(R.id.tv_forget);
		tv_forget.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//forgetPassword();
				Intent intent  = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
				startActivity(intent);
			}
		});

		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
		if (BuildConfig.DEBUG) {
			FacebookSdk.setIsDebugEnabled(true);
			FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		}
		// [START initialize_auth]
		mAuth = FirebaseAuth.getInstance();
		// [END initialize_auth]

		//generating keyhash

		/*try {
			PackageInfo info = getPackageManager().getPackageInfo(
					getPackageName(),
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		}
		catch (PackageManager.NameNotFoundException e) {

		}
		catch (NoSuchAlgorithmException e) {

		}*/

		final FirebaseUser currentUser = mAuth.getCurrentUser();
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestIdToken(getString(R.string.default_web_client_id))
				.requestEmail()
				.build();

		//facebook login
		mCallbackManager = CallbackManager.Factory.create();

		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

		//GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);



		//facebook button

		fb_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//if (currentUser!=null){

					mAuth.signOut();
					LoginManager.getInstance().logOut();
					//Toast.makeText(LoginActivity.this,"signedout : user signed out",Toast.LENGTH_LONG).show();
					Log.e("signedout","user signed out");
					LoginManager
							.getInstance()
							.logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile")
							);
					LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
						@Override
						public void onSuccess(LoginResult loginResult) {

							handleFacebookAccessToken(loginResult.getAccessToken());
							//	saveFacebookLoginData("facebook", loginResult.getAccessToken());
							Log.e("facebook:onSuccess", "" + loginResult);
							// Facebook Email address
							GraphRequest request = GraphRequest.newMeRequest(
									loginResult.getAccessToken(),
									new GraphRequest.GraphJSONObjectCallback() {
										@Override
										public void onCompleted(
												JSONObject object,
												GraphResponse response) {
											Log.e("LoginActivityResponse ", response.toString());

											try {
												String Name = object.getString("name");

												String FEmail = object.getString("email");
												String id = object.getString("id");
												Log.e("Email = ", " " + FEmail+" "+Name);

												SignInWithFacebook(Name,".",FEmail,id);


											} catch (JSONException e) {
												e.printStackTrace();
											}
										}
									});
							Bundle parameters = new Bundle();
							parameters.putString("fields", "id,name,email,gender, birthday");
							request.setParameters(parameters);
							request.executeAsync();


						}

						@Override
						public void onCancel() {
							Log.e("fbloginfailed", "facebook:onCancel");
						}

						@Override
						public void onError(FacebookException error) {
							Log.e("facebookerror", "facebook:onError", error);
						}
					});


			}
		});

		mAuthListener = new FirebaseAuth.AuthStateListener(){


			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

				FirebaseUser user = firebaseAuth.getCurrentUser();

				if (user!=null){

					Toast.makeText(LoginActivity.this,""+user.getDisplayName(),Toast.LENGTH_LONG).show();
				}else {
					Toast.makeText(LoginActivity.this,"something went wrong",Toast.LENGTH_LONG).show();
				}


			}
		};

		//social logins
		gmail_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
				if (account!=null){
					mAuth.signOut();
					mGoogleSignInClient.signOut();
				}


				//Toast.makeText(LoginActivity.this,""+account.getDisplayName(),Toast.LENGTH_LONG).show();
				Intent signInIntent = mGoogleSignInClient.getSignInIntent();
				startActivityForResult(signInIntent, RC_SIGN_IN);
			}
		});
		mGoogleSignInClient = GoogleSignIn.getClient(this, gso);




		submit_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (u_name.getText().toString().equals("")){
					Snackbar.make(gmail_btn,"please enter email",Snackbar.LENGTH_SHORT).show();
				}
				else if (password.getText().toString().length()<6){
					Snackbar.make(gmail_btn,"password must be minimum 6 characters",Snackbar.LENGTH_SHORT).show();
				}
				else{
					CallLoginService();
				}
				//finish();
			}
		});

		//signup button


		signup_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
				startActivity(intent);
				finish();
			}
		});

		Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
		setSupportActionBar(toolbar);
		setupActionBar();
		setupHeader();



	}


	//On activtiy result
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		 if (requestCode == RC_SIGN_IN){
			Log.e("gmselected","gmselected");
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
			try {
				// Google Sign In was successful, authenticate with Firebase
				GoogleSignInAccount account = task.getResult(ApiException.class);
				firebaseAuthWithGoogle(account);
			} catch (ApiException e) {
				// Google Sign In failed, update UI appropriately
				Log.e(TAG, "Google sign in failed", e);

			}
		}
		else {
			 mCallbackManager.onActivityResult(requestCode, resultCode, data);
			 Log.e("fbrequestcode","fbreqcode");
		}
	}

	//Firebase auth facebook
	private void handleFacebookAccessToken(final AccessToken token) {
		Log.e("facebooklogin", "handleFacebookAccessToken:" + token);
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

		mAuth.signInWithCredential(credential)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
					//	Log.e("credential",""+credential.getSignInMethod());
						if (task.isSuccessful()) {
							progressDialog.dismiss();
							Log.e("facebooklogin", "signInWithCredential", task.getException());
							Toast.makeText(LoginActivity.this, "Success",
									Toast.LENGTH_SHORT).show();
							FirebaseUser user = mAuth.getCurrentUser();
							Log.e("fbuser",""+user.getDisplayName()+""+user.getEmail()+""+user.getPhoneNumber());


						} else {
							progressDialog.dismiss();
							Log.e("taskerror",""+task.getException().getMessage().toString());

							//Toast.makeText(LoginActivity.this, "Authentication error :" +task.getException().toString(), Toast.LENGTH_SHORT).show();
							Snackbar.make(gmail_btn, "Facebook email already exits", Snackbar.LENGTH_SHORT).show();
						}


					}
				});
	}


	//Firebase Auth google
	String email,fname,lname,phone,type,userid;
	private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {

		Log.e(TAG, "firebaseAuthWithGoogle:" + acct.getId());
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		// [START_EXCLUDE silent]
		//showProgressDialog();
		// [END_EXCLUDE]

		final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
		mAuth.signInWithCredential(credential)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							progressDialog.dismiss();
							Log.e("credential",""+credential.getSignInMethod());
							// Sign in success, update UI with the signed-in user's information
							Log.e(TAG, "signInWithCredential:success");
							FirebaseUser user = mAuth.getCurrentUser();
							Log.e("user",""+user);
							 email = acct.getEmail();
							 String s = acct.getDisplayName();
							lname = acct.getFamilyName();
							fname = acct.getDisplayName();
							userid = acct.getId();
							Log.e("names","s="+s+",s1 = "+lname+", s2 = "+fname+" , userid = "+userid);
							 fname  = acct.getDisplayName();

							SignInWithGmail(fname,lname,email,userid);

							//Picasso.get().load(acct.getPhotoUrl()).into(profile);

						} else {
							// If sign in fails, display a message to the user.
							Log.e(TAG, "signInWithCredential:failure", task.getException());
							Snackbar.make(gmail_btn, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
							//updateUI(null);
						//	mStatusTextView.setText("uthentication Failed");
						}

						// [START_EXCLUDE]
						//	hideProgressDialog();
						// [END_EXCLUDE]
					}
				});
	}


	//set action bar
	private void setupActionBar() {

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
				Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
				startActivity(intent);
				finish();
			}
		});

		menu_btn = (LinearLayout) v.findViewById(R.id.btn_menu_container);

		getSupportActionBar().setCustomView(v, layoutParams);
		Toolbar parent = (Toolbar) v.getParent();

		parent.setContentInsetsAbsolute(0, 0);


	}
	private void setupHeader(){
		page_title.setText("HOME");
	}


	//login service
	public void CallLoginService(){

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/login.php";

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

						String memberid = jsonObject.getString("member_id");
						String name = jsonObject.getString("name");
						String membercode = jsonObject.getString("member_code");

						Session.setMemberCode(LoginActivity.this,""+membercode);
						Session.setUserid(LoginActivity.this,""+memberid,""+name);

						Log.e("membercode",""+membercode);

						if(!getIntent().hasExtra("sendResult")) {

							Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
							startActivity(intent);
							Log.e("memberid", "" + memberid);
							finish();

						}else{

							setResult(RESULT_OK);
							finish();

						}

					}

					else {

						String msg = jsonObject.getString("message");
						Log.e("message",""+msg);
						Snackbar.make(gmail_btn, msg, Snackbar.LENGTH_SHORT).show();
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
						Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
					}
				}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> parameters = new HashMap<String, String>();
				parameters.put("email",u_name.getText().toString());
				parameters.put("password",password.getText().toString());
				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
	}

	public void signout(){
		mAuth.signOut();
		// Google sign out
		mGoogleSignInClient.signOut().addOnCompleteListener(this,
				new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						//updateUI(null);
						//mStatusTextView.setText("User logedout");
					}
				});
	}


	//Signin with Gmail
	public void SignInWithGmail(final String fname,final String lname, final String email, final String userid){

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/login-api.php";

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

						String memberid = jsonObject.getString("member_id");
						String name = jsonObject.getString("name");
						String membercode = jsonObject.getString("member_code");

						Session.setUserid(LoginActivity.this,""+memberid,""+name);
						Session.setMemberCode(LoginActivity.this,""+membercode);

						Log.e("membercode",""+membercode);

						if(!getIntent().hasExtra("sendResult")) {

							Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
							startActivity(intent);
							Log.e("memberid", "" + memberid);
							finish();

						}else{

							setResult(RESULT_OK);
							finish();

						}

					}

					else {

						String msg = jsonObject.getString("message");
						Log.e("message",""+msg);
						Snackbar.make(gmail_btn, msg, Snackbar.LENGTH_SHORT).show();
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
						Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
					}
				}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> parameters = new HashMap<String, String>();
				parameters.put("fname",fname);
				parameters.put("lname",".");
				parameters.put("email",email);
				parameters.put("phone","phone");
				parameters.put("type","gmail");
				parameters.put("user_id",userid);
				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);

	}


	//Signin with facebook
	public void SignInWithFacebook(final String fname,final String lname, final String email, final String userid){

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please Wait....");
		progressDialog.show();
		progressDialog.setCancelable(false);
		String URL = Session.BASE_URL+"api/login-api.php";

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

						String memberid = jsonObject.getString("member_id");
						String name = jsonObject.getString("name");

						String membercode = jsonObject.getString("member_code");
						Session.setMemberCode(LoginActivity.this,""+membercode);
						Log.e("membercode",""+membercode);

						Session.setUserid(LoginActivity.this,""+memberid,""+name);
						if(!getIntent().hasExtra("sendResult")) {

							Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
							startActivity(intent);
							Log.e("memberid", "" + memberid);
							finish();

						}else{

							setResult(RESULT_OK);
							finish();

						}

					}

					else {

						String msg = jsonObject.getString("message");
						Log.e("message",""+msg);
						Snackbar.make(gmail_btn, msg, Snackbar.LENGTH_SHORT).show();
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
						Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
					}
				}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> parameters = new HashMap<String, String>();
				parameters.put("fname",fname);
				parameters.put("lname",lname);
				parameters.put("email",email);
				parameters.put("phone","phone");
				parameters.put("type","facebook");
				parameters.put("user_id",userid);
				return parameters;
			}
		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);

	}
}

