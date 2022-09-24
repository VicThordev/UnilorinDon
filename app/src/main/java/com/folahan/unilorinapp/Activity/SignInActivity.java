package com.folahan.unilorinapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.folahan.unilorinapp.R;

public class SignInActivity extends AppCompatActivity {

    private TextView signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signIn = findViewById(R.id.signInstead);

        signIn.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}