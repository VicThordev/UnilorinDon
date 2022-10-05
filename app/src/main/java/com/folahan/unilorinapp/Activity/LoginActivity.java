package com.folahan.unilorinapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.MainActivity;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText emailText, passwordText;
    private TextView signIn;
    private PreferenceManager preferenceManager;
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

        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

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

    private void signIn() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, emailText.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, emailText.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null
                    && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult()
                                .getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.
                                KEY_IS_SIGNED_IN, true);
                        preferenceManager.putString(Constants
                        .KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_SURNAME,
                                documentSnapshot.getString(Constants.KEY_SURNAME));
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Unable to sign in", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}