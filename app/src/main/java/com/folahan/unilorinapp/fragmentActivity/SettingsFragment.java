package com.folahan.unilorinapp.fragmentActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.folahan.unilorinapp.R;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =
         inflater.inflate(R.layout.fragment_settings, container, false);
        requireActivity().setTitle("Settings");
        return view;
    }
}