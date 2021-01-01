package com.customer.bloggerspoint.homeStructure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.customer.bloggerspoint.R;
import com.customer.bloggerspoint.adapter.RecyclerAdapterBlog;
import com.customer.bloggerspoint.networkingStructure.NetworkingCalls;
import com.customer.bloggerspoint.pojo.BlogsPojo;
import com.customer.bloggerspoint.sharePrefs.SharePrefs;
import com.customer.bloggerspoint.viewModel.BlogsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {

    FloatingActionButton addBlog;
    SharePrefs sharePrefs;
    View view1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public static BlogsViewModel blogsViewModel;
    SwipeRefreshLayout swipeRefreshLayout;
    NetworkingCalls networkingCalls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addBlog = view.findViewById(R.id.addBlog);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        sharePrefs = new SharePrefs(getContext());
        networkingCalls = new NetworkingCalls(getContext(),getActivity());
        view1 = view;

        blogsViewModel = new ViewModelProvider(this).get(BlogsViewModel.class);

        networkingCalls.getBlogs();

        Observer<ArrayList<BlogsPojo>> arrayListObserver = new Observer<ArrayList<BlogsPojo>>() {
            @Override
            public void onChanged(ArrayList<BlogsPojo> blogsViewModels) {

                RecyclerAdapterBlog recyclerAdapterBlog = new RecyclerAdapterBlog(getContext(),blogsViewModels,getActivity());
                recyclerView.setAdapter(recyclerAdapterBlog);
            }
        };

        blogsViewModel.getBlogsLiveData().observe(getViewLifecycleOwner(),arrayListObserver);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkingCalls.getBlogs();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        addBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_dashboardFragment_to_postBlogFragment);
            }
        });

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
                                Navigation.findNavController(view1).navigate(R.id.action_dashboardFragment_to_loginFragment);
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