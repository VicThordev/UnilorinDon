package com.folahan.unilorinapp.fragmentActivity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.R;
import com.makeramen.roundedimageview.RoundedImageView;


public class HomeFragment extends Fragment {

    private RoundedImageView mView;
    private CardView mCardView;
    private PreferenceManager preferenceManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =
                inflater.inflate(R.layout.fragment_home, container, false);
        mView = view.findViewById(R.id.imgDashBoard);
        mCardView = view.findViewById(R.id.refer);
        mCardView.setOnClickListener(task ->
                Toast.makeText(requireActivity(), "Pls meet the developer for more info", Toast.LENGTH_SHORT).show());
        preferenceManager =  new PreferenceManager(requireActivity().getApplicationContext());

        byte [] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        mView.setImageBitmap(bitmap);


        TextView txtEnter = view.findViewById(R.id.txtUnilorinUpdate);
        txtEnter.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }
}