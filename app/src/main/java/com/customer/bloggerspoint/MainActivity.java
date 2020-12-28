package com.customer.bloggerspoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.customer.bloggerspoint.sharePrefs.SharePrefs;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity{

    View view1;
    SharePrefs sharePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharePrefs = new SharePrefs(MainActivity.this);
    }

}