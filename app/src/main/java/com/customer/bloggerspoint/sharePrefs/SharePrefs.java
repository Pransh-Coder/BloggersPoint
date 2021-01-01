package com.customer.bloggerspoint.sharePrefs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefs {

    private Context context;

    public SharePrefs(Context context) {        //constructor
        this.context = context;
    }

    private SharedPreferences getUserPreference() {
        return context.getSharedPreferences("user", Activity.MODE_PRIVATE);
    }

    public void removeAllSP()               // mainly used for loging out (clearing all the data)
    {
        getUserPreference().edit().clear().commit();

    }
    // For creating session
    public Boolean isLoggedIn(){
        return getUserPreference().getBoolean("loggedin", false);
    }

    public void setLoggedIn(boolean b){
        getUserPreference().edit().putBoolean("loggedin",b).apply();
    }

    public String getFirName(){
        return getUserPreference().getString("first_name","");
    }
    public void setFirName(String firstName){
        getUserPreference().edit().putString("first_name",firstName).apply();
    }
    public String getLasName(){
        return getUserPreference().getString("last_name","");
    }
    public void setLasName(String lastName){
        getUserPreference().edit().putString("last_name",lastName).apply();
    }
}
