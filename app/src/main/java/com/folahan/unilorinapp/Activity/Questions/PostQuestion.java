package com.folahan.unilorinapp.Activity.Questions;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.folahan.unilorinapp.Activity.BaseActivity;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.QuestionList;
import com.folahan.unilorinapp.databinding.ActivityPostQuestionBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class PostQuestion extends BaseActivity {
    private PreferenceManager manager;

    private List<QuestionList> realList;
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

        manager = new PreferenceManager(getApplicationContext());

        dialog = new ProgressDialog(this);
        realList = new ArrayList<>();

        binding.btnPost.setOnClickListener(view -> {
            Intent data = new Intent();
            String post = binding.edtPost.getText().toString();

            if (post.trim().isEmpty()) {
                Toast.makeText(this, "Empty post" +
                        " cannot be created!", Toast.LENGTH_SHORT).show();
            }

            uploadData();

            setResult(RESULT_OK, data);
            finish();
        });

    }

    private void uploadData() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        String post = binding.edtPost.getText().toString();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_QUESTION_POST, post);
        user.put(Constants.KEY_POSTER_NAME, manager.getString(Constants.KEY_USERNAME));
        user.put("time stamp", new Date());

        database.collection(Constants.KEY_COLLECTION_POST)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Posted", Toast.LENGTH_SHORT).show();
                })
        .addOnSuccessListener(documentReference -> {
            Toast.makeText(this, "No way", Toast.LENGTH_SHORT).show();
        });
    }


}