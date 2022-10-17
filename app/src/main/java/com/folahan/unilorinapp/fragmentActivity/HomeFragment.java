package com.folahan.unilorinapp.fragmentActivity;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.folahan.unilorinapp.R;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =
                inflater.inflate(R.layout.fragment_home, container, false);
        TextView txtEnter = view.findViewById(R.id.txtUnilorinUpdate);
        txtEnter.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }
}