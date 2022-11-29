package com.folahan.unilorinapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.User;
import com.folahan.unilorinapp.databinding.ActivityIntoChatBinding;

public class IntoChatActivity extends AppCompatActivity {

    private ActivityIntoChatBinding chatBinding;
    private User user;
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_USERNAME = "user_name";
    public static final String EXTRA_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatBinding = ActivityIntoChatBinding.inflate(getLayoutInflater());
        setContentView(chatBinding.getRoot());

        onClick();
        chatBinding.buttonChat.setOnClickListener(view -> {
            Intent data = new Intent(getApplicationContext(), ChatActivity.class);
            data.putExtra(Constants.KEY_USER, user);
            startActivity(data);
            finish();
        });
    }

    private void onClick() {
        Intent data = new Intent();
        user = (User) getIntent().getSerializableExtra(Constants.KEY_USER);
        chatBinding.name.setText(user.getUsername());
        chatBinding.Username.setText(user.getEmail());
        data.putExtra(EXTRA_NAME, user.getSurname());
        data.putExtra(EXTRA_USERNAME, user.getUsername());
        data.putExtra(EXTRA_EMAIL, user.getEmail());
        setResult(RESULT_OK, data);
    }
}