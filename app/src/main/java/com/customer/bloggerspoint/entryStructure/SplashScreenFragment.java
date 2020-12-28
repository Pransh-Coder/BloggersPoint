package com.customer.bloggerspoint.entryStructure;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.customer.bloggerspoint.R;
import com.customer.bloggerspoint.sharePrefs.SharePrefs;


public class SplashScreenFragment extends Fragment {

    SharePrefs sharePrefs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharePrefs = new SharePrefs(getContext());
        getActivity().findViewById(R.id.logout).setVisibility(View.GONE);
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (sharePrefs.isLoggedIn())
                {
                    if (isAdded()) {
                        Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_dashboardFragment);
                    }
                }
                else {
                    Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_signupFragment);
                }
            }
        }.start();// afterDelay will be executed after (secs*1000) milliseconds.
    }
}