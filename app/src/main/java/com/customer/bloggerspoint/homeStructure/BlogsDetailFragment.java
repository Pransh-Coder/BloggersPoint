package com.customer.bloggerspoint.homeStructure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.customer.bloggerspoint.R;
import com.customer.bloggerspoint.sharePrefs.SharePrefs;


public class BlogsDetailFragment extends Fragment {

    TextView tv_title,tv_description,tv_authorName;
    String title,description,authName;
    SharePrefs sharePrefs;
    View view1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blogs_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_title = view.findViewById(R.id.title);
        tv_description = view.findViewById(R.id.description);
        tv_authorName = view.findViewById(R.id.authorName);
        sharePrefs = new SharePrefs(getContext());
        view1 = view;

        if (getArguments() != null) {

            title = getArguments().getString("title","");
            description = getArguments().getString("description","");
            authName = getArguments().getString("name","");

            tv_title.setText(title);
            tv_description.setText(description);
            tv_authorName.setText("By: "+authName);
        }

        getActivity().findViewById(R.id.logout).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Do you wan't to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                sharePrefs.removeAllSP();
                                Navigation.findNavController(view1).navigate(R.id.action_blogsDetailFragment_to_loginFragment);
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }
}