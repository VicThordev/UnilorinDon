package com.folahan.unilorinapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.folahan.unilorinapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText emailText, passwordText;
    private TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

        signIn = findViewById(R.id.signIn);

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        signIn.setOnClickListener(view -> {
            startActivity(new Intent(this, SignInActivity.class));
        });

    }
}