
package com.folahan.unilorinapp.fragmentActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.folahan.unilorinapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_layout, container, false);
    }
}