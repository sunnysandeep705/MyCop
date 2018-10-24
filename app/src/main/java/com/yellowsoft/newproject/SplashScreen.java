package com.yellowsoft.newproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 7/3/18.
 */

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();
        callSettings();


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity


                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    //call settings api
    public void callSettings(){

       /* final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);*/
        String URL = Session.BASE_URL+"api/settings.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res",response);
                Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                startActivity(i);
               /* if(progressDialog!=null) {
                    progressDialog.dismiss();

                }*/
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    ApplicationController.getInstance().settings = jsonObject;
                    String s= ApplicationController.getInstance().settings.getString("shipping_charges");
                    Log.e("shippingssssss",""+s);


                    String scheme_amt=jsonObject.getString("scheme_amount");
                    Log.e("schemeAmt",""+scheme_amt);
                    String shipping_charges = jsonObject.getString("shipping_charges");
                    Log.e("shippingcharges",""+shipping_charges);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("errorOne",""+e.getMessage().toString());
                    Toast.makeText(SplashScreen.this,""+e.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("errorTwo",""+error.getMessage().toString());
                        Toast.makeText(SplashScreen.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                        startActivity(i);
                      /*  if(progressDialog!=null)
                            progressDialog.dismiss();*/
                      //  Snackbar.make(gmail_btn, error.toString(), Snackbar.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> parameters = new HashMap<String, String>();

                return parameters;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(stringRequest);

    }

}
