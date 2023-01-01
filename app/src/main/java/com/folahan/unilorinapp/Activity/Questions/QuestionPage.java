package com.folahan.unilorinapp.Activity.Questions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.folahan.unilorinapp.Activity.BaseActivity;
import com.folahan.unilorinapp.Adapter.QuestionAdapter;
import com.folahan.unilorinapp.Listeners.QuestionListListener;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.QuestionList;
import com.folahan.unilorinapp.Model.User;
import com.folahan.unilorinapp.R;
import com.folahan.unilorinapp.databinding.ActivityQuestionPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class QuestionPage extends BaseActivity implements QuestionListListener {
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
        user = new User();
        list = new QuestionList();
        adapter = new QuestionAdapter(mQuestionList,  this);

        preferenceManager = new PreferenceManager(getApplicationContext());



        //Creating the RecyclerView Layout Manager-
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

        database = FirebaseFirestore.getInstance();
        showData();
    }

    private void showData() {
        database.collection(Constants.KEY_COLLECTION_POST)
                .get()
                .addOnCompleteListener(task ->  {
                        String currentUserId = preferenceManager.getString(
                                Constants.KEY_USER_ID);
                        if (task.isSuccessful() && task.getResult() != null) {
                            List<QuestionList> list = new ArrayList<>();
                            for (QueryDocumentSnapshot queryDocumentSnapshot :
                                    task.getResult()) {
                                if (currentUserId.equals(queryDocumentSnapshot.getId())) {
                                    continue;
                                }
                                QuestionList mList = new QuestionList();
                                mList.setName(queryDocumentSnapshot.getString(Constants.KEY_SURNAME));
                                mList.setUsername(queryDocumentSnapshot.getString(Constants.KEY_USERNAME));
                                mList.setImage(queryDocumentSnapshot.getString(Constants.KEY_IMAGE));
                                mList.setQuestion(queryDocumentSnapshot.getString(Constants.KEY_QUESTION_POST));
                                mList.setLike(queryDocumentSnapshot.getString(Constants.KEY_LIKES_BOX));
                                list.add(mList);
                            }
                            if (list.size() > 0 ){
                                QuestionAdapter adapter = new QuestionAdapter(list, this);
                                mRecyclerView.setAdapter(adapter);
                                mRecyclerView.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(this, "No post available at the moment", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "No post available at the moment", Toast.LENGTH_SHORT).show();
                        }
                });

    }


    @Override
    public void onQuestionClicked(QuestionList list) {
        Intent data = new Intent(getApplicationContext(), CommentActivity.class);
        data.putExtra(Constants.KEY_USER, list);
        startActivity(data);
        finish();
    }
}