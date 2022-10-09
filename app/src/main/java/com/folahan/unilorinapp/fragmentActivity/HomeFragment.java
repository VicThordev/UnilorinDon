package com.folahan.unilorinapp.fragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.folahan.unilorinapp.Activity.FriendsActivity;
import com.folahan.unilorinapp.R;


public class HomeFragment extends Fragment {
    private RelativeLayout rl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =
                inflater.inflate(R.layout.fragment_home, container, false);


        return view;
    }
}