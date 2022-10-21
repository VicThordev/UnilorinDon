package com.folahan.unilorinapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.User;
import com.folahan.unilorinapp.R;
import com.folahan.unilorinapp.databinding.ActivityIntoChatBinding;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class IntoChatActivity extends AppCompatActivity {

    private TextView mName, mUsername, mFaculty, mDepartment;
    private ActivityIntoChatBinding chatBinding;
    private Button mButton;
    private PreferenceManager manager;
    private QueryDocumentSnapshot queryDocumentSnapshot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into_chat);
        mName = findViewById(R.id.name);
        mUsername = findViewById(R.id.Username);
        mFaculty = findViewById(R.id.faculty);
        mDepartment = findViewById(R.id.department);
        mButton = findViewById(R.id.button_chat);
        manager = new PreferenceManager(getApplicationContext());
        chatBinding.name.setText((manager.getString(Constants.KEY_EMAIL)));

        mButton.setOnClickListener(view -> startActivity(new Intent(this, ChatActivity.class)));
        onClick();
    }

    private void onClick() {
        User user = new User();

    }
}