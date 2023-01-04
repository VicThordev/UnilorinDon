
package com.folahan.unilorinapp.fragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.Question;
import com.folahan.unilorinapp.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    private TextView  txtUsername, txtEmail, txtEmailAddress, txtDepartment;
    private TextView txtName;
    private FirebaseFirestore firebase;
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_layout, container, false);
        txtName = view.findViewById(R.id.txtNameProfile);
        RoundedImageView imageView = view.findViewById(R.id.imgProfile);
        txtUsername = view.findViewById(R.id.txtUsernameProfile);
        txtEmail = view.findViewById(R.id.txtEmailProfile);
        txtEmailAddress = view.findViewById(R.id.emailAddress);
        txtDepartment = view.findViewById(R.id.txtDepartment);
        firebase = FirebaseFirestore.getInstance();

        //firebase.collection(Constants.KEY_COLLECTION_USERS)
        //        .whereEqualTo(Constants.KEY_USERNAME, "Borja")
        //        .whereEqualTo(Constants.KEY_USERNAME, "Borja")
        //        .addSnapshotListener(eventListener);
        PreferenceManager preferenceManager = new PreferenceManager(requireActivity().getApplicationContext());
        String name1 = preferenceManager.getString(Constants.KEY_USERNAME);

        byte [] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView.setImageBitmap(bitmap);

        String name = preferenceManager.getString(Constants.KEY_SURNAME);
        String lastName = preferenceManager.getString(Constants.KEY_LASTNAME);
        String username = preferenceManager.getString(Constants.KEY_USERNAME);
        txtName.setText(String.format("%s", name+" "+lastName));
        txtUsername.setText(username);
        txtEmailAddress.setText(preferenceManager.getString(Constants.KEY_EMAIL));

        return view;
    }

    private final EventListener<QuerySnapshot> eventListener = ((value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            Toast.makeText(requireActivity(), "You rock", Toast.LENGTH_SHORT).show();
        }
    });


}