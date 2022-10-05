package com.folahan.unilorinapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.MainActivity;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    private PreferenceManager preferenceManager;
    private EditText edtSurname, edtFirstName, edtUsername, edtMobile, edtEmail, edtPassword,
    edtConfirmPassword;

    private Boolean checkTrue;

    private TextView signIn, txtSurname, txtFirstname, txtUsername, txtMobile, txtEmail, txtPassword,
    txtConfirmPassword;

    private String message1, message2, message3, message4, message5, message6, message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtSurname = findViewById(R.id.txtSurname);
        edtFirstName = findViewById(R.id.txtFirstName);
        edtUsername = findViewById(R.id.Username);
        edtMobile = findViewById(R.id.txtMobile);
        edtEmail = findViewById(R.id.txtEmail);
        edtPassword = findViewById(R.id.txtPassword);
        edtConfirmPassword = findViewById(R.id.confirmPassword);

        txtSurname = findViewById(R.id.txtSurnameText);
        txtFirstname = findViewById(R.id.txtFirstNameText);
        txtUsername = findViewById(R.id.txtUsernameText);
        txtMobile = findViewById(R.id.txtMobileText);
        txtEmail = findViewById(R.id.txtEmailText);
        txtPassword = findViewById(R.id.txtPasswordText);
        txtConfirmPassword = findViewById(R.id.txtConfirmPasswordText);

        preferenceManager = new PreferenceManager(getApplicationContext());

        signIn = findViewById(R.id.signInstead);

        signIn.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void addDataToFirestore() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();

        user.put(Constants.KEY_SURNAME, edtSurname.getText().toString());
        user.put(Constants.KEY_LASTNAME, edtFirstName.getText().toString());
        user.put(Constants.KEY_USERNAME, edtUsername.getText().toString());
        user.put(Constants.KEY_EMAIL, edtEmail.getText().toString());
        user.put(Constants.KEY_PASSWORD, edtPassword.getText().toString());
        user.put(Constants.KEY_MOBILE, edtMobile.getText().toString());

        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,
                            true);
                    preferenceManager.putString(Constants.KEY_USER_ID,
                            documentReference.getId());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(exception -> {
                    Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public Boolean register(View view) {
        checkTrue = false;
        if (!checkTrue) {
            message = edtSurname.getText().toString();
            if (message.trim().isEmpty()) {
                txtSurname.setVisibility(View.VISIBLE);
                return true;
            } else {
                txtSurname.setVisibility(View.GONE);
                checkTrue=true;
            }

            message1 = edtFirstName.getText().toString();
            if (message1.trim().isEmpty()) {
                txtFirstname.setVisibility(View.VISIBLE);
                return true;
            } else {
                txtFirstname.setVisibility(View.GONE);
                checkTrue=true;
            }

            message2 = edtUsername.getText().toString();
            if (message2.trim().isEmpty()) {
                txtUsername.setVisibility(View.VISIBLE);
                return true;
            } else {
                txtUsername.setVisibility(View.GONE);
                checkTrue=true;
            }

            message3 = edtMobile.getText().toString();
            if (message3.trim().isEmpty()) {
                txtMobile.setVisibility(View.VISIBLE);
                return true;
            } else {
                txtMobile.setVisibility(View.GONE);
                checkTrue=true;
            }

            message4 = edtEmail.getText().toString();
            if (message4.trim().isEmpty()) {
                txtEmail.setVisibility(View.VISIBLE);
                return true;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(message4)
            .matches()) {
                Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
            }
            else {
                txtEmail.setVisibility(View.GONE);
                checkTrue=true;
            }

            message5 = edtPassword.getText().toString();
            if (message5.trim().isEmpty()) {
                txtPassword.setVisibility(View.VISIBLE);
                return true;
            } else {
                txtPassword.setVisibility(View.GONE);
                checkTrue=true;
            }

            message6 = edtConfirmPassword.getText().toString();
            if (message6.trim().isEmpty()) {
                txtConfirmPassword.setVisibility(View.VISIBLE);
                return true;
            } else {
                txtConfirmPassword.setVisibility(View.GONE);
                checkTrue=true;
            }
            checkTrue = true;
        }

        if (message5.equals(message6)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();

        }
        addDataToFirestore();
        return false;
    }

}