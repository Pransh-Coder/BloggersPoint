package com.customer.bloggerspoint.homeStructure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.customer.bloggerspoint.R;


public class BlogsDetailFragment extends Fragment {

    TextView tv_title,tv_description,tv_authorName;
    String title,description,authName;

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

        if (getArguments() != null) {

            title = getArguments().getString("title","");
            description = getArguments().getString("description","");
            authName = getArguments().getString("name","");

            tv_title.setText(title);
            tv_description.setText(description);
            tv_authorName.setText(authName);
        }

    }
}