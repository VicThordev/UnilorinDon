package com.folahan.unilorinapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.MainActivity;
import com.folahan.unilorinapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText emailText, passwordText;
    private TextView signIn;
    private Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        btnClick = findViewById(R.id.btnLogin);

        signIn = findViewById(R.id.signIn);

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        btnClick.setOnClickListener(view -> {
            String message = emailText.getText().toString();
            String message2 = passwordText.getText().toString();
            if (message.trim().isEmpty()||message2.trim().isEmpty()) {
                Toast.makeText(this, "Pls fill the required box", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });

        signIn.setOnClickListener(view -> {
            startActivity(new Intent(this, SignInActivity.class));
        });

    }
}