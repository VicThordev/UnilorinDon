package com.folahan.unilorinapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.User;
import com.folahan.unilorinapp.databinding.ActivityIntoChatBinding;

import java.util.Objects;

public class IntoChatActivity extends AppCompatActivity {

    private ActivityIntoChatBinding chatBinding;
    private PreferenceManager preferenceManager;
    private User user;
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_USERNAME = "user_name";
    public static final String EXTRA_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatBinding = ActivityIntoChatBinding.inflate(getLayoutInflater());
        setContentView(chatBinding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        Objects.requireNonNull(getSupportActionBar()).hide();

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
        chatBinding.name.setText(user.getSurname());
        byte [] bytes = Base64.decode(user.getImage(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        chatBinding.imageProfile.setImageBitmap(bitmap);
        chatBinding.Username.setText(user.getUsername());
        chatBinding.faculty.setText(user.getFaculty());
        chatBinding.department.setText(user.getDepartment());
        data.putExtra(EXTRA_NAME, user.getSurname());
        data.putExtra(EXTRA_USERNAME, user.getUsername());
        data.putExtra(EXTRA_EMAIL, user.getEmail());
        setResult(RESULT_OK, data);
    }
}