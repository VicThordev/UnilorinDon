package com.folahan.unilorinapp.Activity.Questions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.folahan.unilorinapp.Adapter.QuestionAdapter;
import com.folahan.unilorinapp.Model.Question;
import com.folahan.unilorinapp.Model.QuestionList;
import com.folahan.unilorinapp.PostQuestionDatabase.QuestionViewModel;
import com.folahan.unilorinapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuestionPage extends AppCompatActivity {
    private ArrayList<QuestionList> mQuestionList;
    private FloatingActionButton fab;
    private QuestionAdapter adapter;
    private RecyclerView mRecyclerView;
    private static int Add_Post_Request = 1;
    private static int Edit_Note_Request = 2;
    private QuestionViewModel questionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page);
        mQuestionList = new ArrayList<>();

        fab = findViewById(R.id.floatOpen);
        mRecyclerView = findViewById(R.id.recycler_view);
        fab.setOnClickListener(view -> {
            Intent data = new Intent(this, PostQuestion.class);
            startActivityForResult(data, Add_Post_Request);
        });

        adapter = new QuestionAdapter();

        //Creating the RecyclerView Layout Manager
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

        questionViewModel = new ViewModelProvider(this)
                .get(QuestionViewModel.class);
        questionViewModel.getAllQuestion().observe(this, questionLists -> {
            adapter.submitList(questionLists);
            mQuestionList = new ArrayList<>(questionLists);
        });

        adapter.setOnItemClickListener(new QuestionAdapter.onItemClickListener() {
            @Override
            public void onItemClick(QuestionList question) {
                Intent data = new Intent(QuestionPage.this, CommentActivity.class);
                //data.putExtra(CommentActivity.EXTRA_COMMENT, mQuestionList);

                startActivityForResult(data, Edit_Note_Request);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Add_Post_Request && resultCode ==RESULT_OK) {
            assert data != null;

            QuestionList list = (QuestionList) data.getSerializableExtra(PostQuestion.EXTRA_POST);
            Toast.makeText(this, "Posted", Toast.LENGTH_SHORT).show();
            mQuestionList.add(list);
            questionViewModel.insert(list);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}