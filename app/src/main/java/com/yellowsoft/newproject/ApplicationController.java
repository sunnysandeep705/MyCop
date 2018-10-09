package com.yellowsoft.newproject;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by sriven on 5/7/2018.
 */

public class ApplicationController extends Application {
    private RequestQueue mRequestQueue;
    public static final String TAG = "VolleyPatterns";
    JSONObject settings;

    private static ApplicationController sInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        settings = new JSONObject();
        // initialize the singleton
        sInstance = this;
    }

    /**
     * @return co.pixelmatter.meme.ApplicationController singleton instance
     */
    public static synchronized ApplicationController getInstance() {
        return sInstance;
    }
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    //
   /* public void setCharges(final String shippingcharges,final String schemeamt){
        Log.e("charges",""+shippingcharges+""+schemeamt);
    }*/




}
