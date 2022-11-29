
package com.folahan.unilorinapp.fragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.Activity.SignInActivity;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.User;
import com.folahan.unilorinapp.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    private TextView  txtUsername, txtEmail;
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
        txtUsername = view.findViewById(R.id.txtUsernameProfile);
        txtEmail = view.findViewById(R.id.txtEmailProfile);

        preferenceManager =  new PreferenceManager(view.getContext().getApplicationContext());

        //txtName.setText(preferenceManager.getString(Constants.KEY_SURNAME + "pPp" + Constants.KEY_LASTNAME) + "po");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String name = requireArguments().getString("KEY_NAME");
        String username = requireArguments().getString("KEY_USERNAME");
        String email = requireArguments().getString("KEY_EMAIL");

        txtName.setText(name);
        txtUsername.setText(username);
        txtEmail.setText(email);
    }

    public void signOut() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS)
                .document(preferenceManager.getString(Constants.KEY_USER_ID));
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    preferenceManager.clear();
                    startActivity(new Intent(view.getContext().getApplicationContext(),
                            SignInActivity.class));
                })
                .addOnFailureListener(e ->
                        Toast.makeText(view.getContext().getApplicationContext(), "Unable to sign out", Toast.LENGTH_SHORT).show());
    }
}