package com.folahan.unilorinapp.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.MainActivity;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.R;
import com.folahan.unilorinapp.fragmentActivity.AccountFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    private PreferenceManager preferenceManager;
    private EditText edtSurname, edtFirstName, edtUsername, edtMobile, edtEmail, edtPassword,
    edtConfirmPassword;
    private RelativeLayout layout;

    private Boolean checkTrue;

    private TextView signIn, mSignUp, txtSurname, txtFirstname, txtUsername, txtMobile, txtEmail, txtPassword,
    txtConfirmPassword;

    private String message1, message2, message3, message4, encodedImage, message5, message6, message;
    private RoundedImageView img;
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

        layout = findViewById(R.id.rlProfile);

        img = findViewById(R.id.imgSignUp);


        txtSurname = findViewById(R.id.txtSurnameText);
        txtFirstname = findViewById(R.id.txtFirstNameText);
        txtUsername = findViewById(R.id.txtUsernameText);
        txtMobile = findViewById(R.id.txtMobileText);
        txtEmail = findViewById(R.id.txtEmailText);
        txtPassword = findViewById(R.id.txtPasswordText);
        txtConfirmPassword = findViewById(R.id.txtConfirmPasswordText);

        mSignUp = findViewById(R.id.txtSignUp);
        preferenceManager = new PreferenceManager(getApplicationContext());

        signIn = findViewById(R.id.signInstead);

        layout.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images
            .Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });

        signIn.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        AccountFragment mFragment = new AccountFragment();
        Bundle bundle = new Bundle();

        String name = "Tobi";
        bundle.putString("KEY_USERNAME", name);
        bundle.putString(Constants.KEY_EMAIL, edtEmail.getText().toString());
        mFragment.setArguments(bundle);
        transaction.show(mFragment).commit();

    }

    private void addDataToFirestore() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();

        user.put(Constants.KEY_SURNAME, edtSurname.getText().toString());
        user.put(Constants.KEY_LASTNAME, edtFirstName.getText().toString());
        user.put(Constants.KEY_USERNAME, edtUsername.getText().toString());
        user.put(Constants.KEY_EMAIL, edtEmail.getText().toString());
        user.put(Constants.KEY_IMAGE, encodedImage);
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

            if (encodedImage == null) {
                Toast.makeText(this, "Select Profile Image", Toast.LENGTH_SHORT).show();
                return false;
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

    private String encodeImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth/bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth,
                previewHeight, false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte [] bytes = stream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    assert result.getData() != null;
                    Uri imageUri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        img.setImageBitmap(bitmap);
                        mSignUp.setVisibility(View.GONE);
                        encodedImage = encodeImage(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

}