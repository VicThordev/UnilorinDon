
package com.folahan.unilorinapp.fragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.Activity.IntoChatActivity;
import com.folahan.unilorinapp.Activity.SignInActivity;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.User;
import com.folahan.unilorinapp.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    private TextView  txtUsername, txtEmail;
    private RoundedImageView imageView;
    private SharedPreferences.Editor editor;
    private View view;
    private PreferenceManager preferenceManager;
    private TextView txtName;
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.profile_layout, container, false);
        txtName = view.findViewById(R.id.txtNameProfile);
        imageView = view.findViewById(R.id.imgProfile);
        txtUsername = view.findViewById(R.id.txtUsernameProfile);
        txtEmail = view.findViewById(R.id.txtEmailProfile);
        preferenceManager =  new PreferenceManager(requireActivity().getApplicationContext());
        byte [] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView.setImageBitmap(bitmap);

        String name = preferenceManager.getString(Constants.KEY_SURNAME);
        String username = preferenceManager.getString(Constants.KEY_USERNAME);
        txtName.setText(name);
        txtUsername.setText(username);

        return view;
    }


}