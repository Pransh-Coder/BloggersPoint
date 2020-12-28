package com.customer.bloggerspoint.networkingStructure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.customer.bloggerspoint.homeStructure.DashboardFragment;
import com.customer.bloggerspoint.pojo.BlogsPojo;
import com.customer.bloggerspoint.sharePrefs.SharePrefs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NetworkingCalls {

    private Context context;
    Activity activity;
    BaseUrl baseUrl = new BaseUrl();
    RequestQueue requestQueue;
    SharePrefs sharePrefs;
    NetworkingInterface networkingInterface;

    public NetworkingCalls(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        requestQueue = Volley.newRequestQueue(context);
    }

    public NetworkingCalls(Context context, Activity activity, NetworkingInterface networkingInterface) {
        this.context = context;
        this.activity = activity;
        this.networkingInterface = networkingInterface;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getBlogs(){

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait!!");
        dialog.setTitle("Getting Blogs..");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl.getGET_BLOGS(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("onResponseGetBlogs", "" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
