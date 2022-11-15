package com.folahan.unilorinapp.Activity.Questions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.QuestionList;
import com.folahan.unilorinapp.R;
import com.folahan.unilorinapp.databinding.ActivityPostQuestionBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;

public class PostQuestion extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private PreferenceManager manager;

    String [] cameraPermissions;
    String [] storagePermissions;
    private FirebaseFirestore database;
    private String conversionId = null;

    Uri imageUri;

    private ActivityPostQuestionBinding binding;
    public static final String EXTRA_POST = "com.folahan.unilorinapp." +
            "EXTRA_POST";
    private QuestionList list;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseFirestore.getInstance();
        manager = new PreferenceManager(getApplicationContext());
        byte [] bytes = Base64.decode(manager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        binding.imageQuestion.setImageBitmap(bitmap);

        cameraPermissions = new String[]{Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE};
        dialog = new ProgressDialog(this);

        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        binding.btnPost.setOnClickListener(view -> {
            Intent data = new Intent();
            String post = binding.edtPost.getText().toString();

            if (post.trim().isEmpty()) {
                Toast.makeText(this, "Empty post" +
                        " cannot be created!", Toast.LENGTH_SHORT).show();
            }

            if (imageUri == null) {
                uploadData(post, "noImage");
            } else {
                uploadData(post, String.valueOf(imageUri));
            }

            setResult(RESULT_OK, data);
            finish();
        });

        binding.imageAddPost.setOnClickListener(view -> showImageDialog());
    }

    private void uploadData(String post, String noImage) {
        dialog.setMessage("Publishing post...");
        dialog.show();
        Intent intent = new Intent();
        list = new QuestionList();
        String poster = binding.edtPost.getText().toString();

        String timeStamp = String.valueOf(System.currentTimeMillis());
        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put(Constants.KEY_POSTER_NAME, manager.getString(Constants.KEY_USER_ID));
        hashMap.put(Constants.KEY_QUESTION_POST, poster);
        hashMap.put(Constants.KEY_TIMESTAMP_POST, timeStamp);
        hashMap.put(Constants.KEY_IMAGE_POST, noImage);

        database.collection(Constants.KEY_COLLECTION_POST)
                .add(hashMap)
                .addOnSuccessListener(documentReference -> {
                    conversionId = documentReference.getId();
                    dialog.dismiss();
                    Toast.makeText(this, "Post Published", Toast.LENGTH_SHORT).show();
                });
        if (!noImage.equals("noImage")) {

        }
        list.setQuestion(poster);
        list.setName(manager.getString(Constants.KEY_USER_ID));
        list.setComment(0);
        list.setDateTime(timeStamp);
        list.setId("");
        intent.putExtra(EXTRA_POST, list);
        setResult(RESULT_OK, intent);
        finish();

    }

    private void showImageDialog() {
        String [] options = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image from")
                .setItems(options, (dialogInterface, i) -> {
                    if (i == 0) {
                        if (!(checkCameraPermission())) {
                            requestCameraPermission();
                        } else {
                            pickFromCamera();
                        }
                    }
                    if (i == 1) {
                        pickFromStorage();
                    }
                })
                .create().show();
    }

    private void pickFromStorage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pick");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Descr");
        imageUri = getContentResolver().insert(MediaStore.Images
        .Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (
                PackageManager.PERMISSION_GRANTED
                );
        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermissions,
                STORAGE_REQUEST_CODE);

    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (
                PackageManager.PERMISSION_GRANTED
        );
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (
                PackageManager.PERMISSION_GRANTED
        );
        return result;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, storagePermissions,
                STORAGE_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length>0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        pickFromCamera();
                    } else {
                        Toast.makeText(this, "Camera & Storage both permissions are necessary...", Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length>0) {
                    if (grantResults.length>0) {
                        boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        if (storageAccepted) {
                            pickFromStorage();
                        } else {
                            Toast.makeText(this, "Storage permissions are necessary...", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                imageUri = data.getData();

                binding.imagePost.setImageURI(imageUri);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                binding.imagePost.setImageURI(imageUri);
            }
        }
    }
}