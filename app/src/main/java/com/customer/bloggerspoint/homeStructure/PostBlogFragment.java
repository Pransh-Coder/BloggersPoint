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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.customer.bloggerspoint.R;
import com.customer.bloggerspoint.networkingStructure.NetworkingCalls;
import com.customer.bloggerspoint.networkingStructure.NetworkingInterface;
import com.customer.bloggerspoint.sharePrefs.SharePrefs;


public class PostBlogFragment extends Fragment implements NetworkingInterface {

    EditText enterTitle,enterDescription;
    Button submit;
    View view1;
    NetworkingCalls networkingCalls;
    SharePrefs sharePrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_blog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        enterTitle = view.findViewById(R.id.enterTitle);
        enterDescription = view.findViewById(R.id.enterDescription);
        networkingCalls = new NetworkingCalls(getContext(),getActivity(),this);
        submit = view.findViewById(R.id.submit);
        sharePrefs = new SharePrefs(getContext());
        view1 = view;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()){
                    networkingCalls.addBlog(enterTitle.getText().toString(),enterDescription.getText().toString());
                }
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
                                Navigation.findNavController(view1).navigate(R.id.action_postBlogFragment_to_loginFragment);
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

    private boolean checkInput(){
        if (enterTitle.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please enter Title", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (enterDescription.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please enter description", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public <T> void networkingRequestPerformed(@Nullable MethodType type, boolean status, @Nullable T error) {

        if (type==MethodType.add_blog&&status){
            Navigation.findNavController(view1).navigate(R.id.action_postBlogFragment_to_dashboardFragment);
        }
        else if (type==MethodType.add_blog&&!status){
            Toast.makeText(getContext(), ""+error.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}