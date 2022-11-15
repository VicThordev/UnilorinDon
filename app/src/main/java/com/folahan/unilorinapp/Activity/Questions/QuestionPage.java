package com.folahan.unilorinapp.Activity.Questions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.folahan.unilorinapp.Adapter.QuestionAdapter;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.QuestionList;
import com.folahan.unilorinapp.Model.User;
import com.folahan.unilorinapp.R;
import com.folahan.unilorinapp.databinding.ActivityQuestionPageBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class QuestionPage extends AppCompatActivity {
    private ArrayList<QuestionList> mQuestionList;
    private FloatingActionButton fab;
    private User user;
    private QuestionAdapter adapter;
    private RecyclerView mRecyclerView;
    private ActivityQuestionPageBinding binding;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore database;
    private static int Add_Post_Request = 1;
    private QuestionList list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mQuestionList = new ArrayList<>();

        fab = findViewById(R.id.floatOpen);
        mRecyclerView = findViewById(R.id.recycler_view);
        fab.setOnClickListener(view -> {
            Intent data = new Intent(this, PostQuestion.class);
            startActivityForResult(data, Add_Post_Request);
        });

        list = new QuestionList();



        preferenceManager = new PreferenceManager(getApplicationContext());

        user = new User();

        //Creating the RecyclerView Layout Manager
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

        database = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Add_Post_Request && resultCode ==RESULT_OK) {
            assert data != null;
            QuestionList list1 = (QuestionList) data.getSerializableExtra(PostQuestion.EXTRA_POST);
            mQuestionList.add(list1);
            //database.collection(Constants.KEY_COLLECTION_POST).add(list1);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}