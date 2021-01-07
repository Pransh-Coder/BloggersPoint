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

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
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
        sharePrefs = new SharePrefs(context);
    }

    public NetworkingCalls(Context context, Activity activity, NetworkingInterface networkingInterface) {
        this.context = context;
        this.activity = activity;
        this.networkingInterface = networkingInterface;
        requestQueue = Volley.newRequestQueue(context);
        sharePrefs = new SharePrefs(context);
    }

    public void getBlogs(){

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait!!");
        dialog.setTitle("Getting Blogs..");
        dialog.show();

        Log.e("getBlogs_URL", ""+baseUrl.getGET_BLOGS() );
        StringRequest stringRequest = new StringRequest(Request.Method.GET, /*baseUrl.getGET_BLOGS()*/"http://paytmpay001.dx.am/blogers_point/api/getBlogs.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.e("onResponseGetBlogs", "" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        Type listType = new TypeToken<List<BlogsPojo>>(){}.getType();
                        ArrayList<BlogsPojo> blogsPojoList = gson.fromJson(String.valueOf(jsonObject.getJSONArray("data")), listType);

                        DashboardFragment.blogsViewModel.getBlogsLiveData().setValue(blogsPojoList);
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    Log.e("exception",e.getMessage());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.e("onErrorResGetBlogs", ""+error);
            }
        });
        requestQueue.add(stringRequest);
    }

    public void signup(String name,String email,String password){

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait!!");
        dialog.setTitle("Creating an account..");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl.getSIGNUP(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.e("onResponseSignup",response);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if (status.equalsIgnoreCase("true")) {

                        String user_id = jsonObject.getString("UID");

                        networkingInterface.networkingRequestPerformed(NetworkingInterface.MethodType.signup,true,null);


                        Log.e("SignupData",user_id);

                        sharePrefs.setLoggedIn(true);
                        sharePrefs.setId(user_id);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("jsonExcepSignup", ""+e);
                    dialog.dismiss();
                    networkingInterface.networkingRequestPerformed(NetworkingInterface.MethodType.signup,false,e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponseSignup",error.toString());
                dialog.dismiss();
                networkingInterface.networkingRequestPerformed(NetworkingInterface.MethodType.signup,false,error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("name",name);
                map.put("email",email);
                map.put("password",password);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void login(String email,String password){

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait!!");
        dialog.setTitle("Logging In..");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl.getLOGIN(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.e("onResponseLogin",response);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if (status.equalsIgnoreCase("true")) {

                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        String user_id = jsonObject.getString("id");

                        networkingInterface.networkingRequestPerformed(NetworkingInterface.MethodType.login,true,null);

                        Log.e("loginData", ""+name+ "  "+email +"  "+password+ " id "+user_id);

                        sharePrefs.setLoggedIn(true);
                        sharePrefs.setName(name);
                        sharePrefs.setEmail(email);
                        sharePrefs.setId(user_id);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("jsonExcepLogin", ""+e);
                    dialog.dismiss();
                    networkingInterface.networkingRequestPerformed(NetworkingInterface.MethodType.login,false,e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponseLogin",error.toString());
                dialog.dismiss();
                networkingInterface.networkingRequestPerformed(NetworkingInterface.MethodType.login,false,error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("email",email);
                map.put("password",password);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void addBlog(String title,String description){

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait!!");
        dialog.setTitle("Uploading blog..");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl.getADD_BLOG(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.e("onResponseAddBlog",response);

                networkingInterface.networkingRequestPerformed(NetworkingInterface.MethodType.add_blog,true,null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponseAddBlog",error.toString());
                dialog.dismiss();
                networkingInterface.networkingRequestPerformed(NetworkingInterface.MethodType.add_blog,false,error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("title",title);
                map.put("description",description);
                map.put("userId",sharePrefs.getId());
                Log.e("user_id", ""+sharePrefs.getId());
                Log.e("values", "title: "+title+" description: "+description);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
