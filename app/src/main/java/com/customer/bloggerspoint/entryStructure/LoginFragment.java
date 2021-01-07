package com.customer.bloggerspoint.entryStructure;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.customer.bloggerspoint.R;
import com.customer.bloggerspoint.networkingStructure.NetworkingCalls;
import com.customer.bloggerspoint.networkingStructure.NetworkingInterface;


public class LoginFragment extends Fragment implements NetworkingInterface {

    LinearLayout account;
    Button loginBtn;
    EditText enterEmail,enterPassword;
    NetworkingCalls networkingCalls;
    View view1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        account = view.findViewById(R.id.account);
        loginBtn = view.findViewById(R.id.loginBtn);
        enterEmail = view.findViewById(R.id.enterEmail);
        enterPassword = view.findViewById(R.id.enterPassword);
        networkingCalls = new NetworkingCalls(getContext(),getActivity(),this);

        view1 = view;

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()){
                    networkingCalls.login(enterEmail.getText().toString(),enterPassword.getText().toString());

                }
            }
        });

        getActivity().findViewById(R.id.logout).setVisibility(View.GONE);
    }
    private boolean checkInput(){
        if (enterEmail.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please enter email", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (enterPassword.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public <T> void networkingRequestPerformed(@Nullable MethodType type, boolean status, @Nullable T error) {

        if (type==MethodType.login&&status){
            Navigation.findNavController(view1).navigate(R.id.action_loginFragment_to_dashboardFragment);

        }
        else if (type==MethodType.login&&!status){
            Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
        }
    }
}