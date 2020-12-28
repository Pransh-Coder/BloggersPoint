package com.customer.bloggerspoint.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.customer.bloggerspoint.pojo.BlogsPojo;

import java.util.ArrayList;

public class BlogsViewModel extends ViewModel {

    MutableLiveData<ArrayList<BlogsPojo>> blogsLiveData;

    public MutableLiveData<ArrayList<BlogsPojo>> getBlogsLiveData(){

        if (blogsLiveData==null){
            blogsLiveData = new MutableLiveData<ArrayList<BlogsPojo>>();
        }

        return blogsLiveData;
    }
}
