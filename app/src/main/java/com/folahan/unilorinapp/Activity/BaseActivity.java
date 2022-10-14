package com.folahan.unilorinapp.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BaseActivity extends AppCompatActivity {

    private DocumentReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        reference = database.collection(Constants.KEY_COLLECTION_USERS)
                .document(manager.getString(Constants.KEY_USER_ID));
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.update(Constants.KEY_AVAILABILITY, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reference.update(Constants.KEY_AVAILABILITY, 1);
    }
}
