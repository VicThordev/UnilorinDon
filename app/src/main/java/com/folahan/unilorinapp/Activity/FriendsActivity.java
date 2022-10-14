package com.folahan.unilorinapp.Activity;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.folahan.unilorinapp.Adapter.UsersAdapter;
import com.folahan.unilorinapp.Listeners.UserListener;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.User;

import com.folahan.unilorinapp.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends BaseActivity implements UserListener {

    private TextView mTxtError;
    private ProgressBar mBar;
    private PreferenceManager preferenceManager;
    private RecyclerView mRecyclerView;
    private List<User> userT;
    private UsersAdapter usersAdapter;
    private AppCompatImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        preferenceManager = new PreferenceManager(getApplicationContext());
        mTxtError = findViewById(R.id.textErrorMessage);
        mRecyclerView = findViewById(R.id.usersRecyclerView);
        mBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageBack);
        userT = new ArrayList<>();
        usersAdapter = new UsersAdapter(userT, this);
        getUsers();
        setListeners();
    }

    private void setListeners() {
        imageView.setOnClickListener(v ->
                onBackPressed());
    }

    private void getUsers() {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    String currentUserId = preferenceManager.getString(
                            Constants.KEY_USER_ID
                    );
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot :
                        task.getResult()) {
                            if (currentUserId.equals(queryDocumentSnapshot.getId())) {
                                continue;
                            }
                            User user = new User();
                            user.username = queryDocumentSnapshot.getString(Constants.KEY_SURNAME);
                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            user.setId(queryDocumentSnapshot.getId());
                            users.add(user);
                        }

                        if (users.size() > 0) {
                            UsersAdapter usersAdapter = new UsersAdapter(users, this);
                            mRecyclerView.setAdapter(usersAdapter);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            showErrorMessage();
                        }
                    } else {
                        showErrorMessage();
                    }
                });
    }

    private void showErrorMessage() {
        mTxtError.setText(String.format("%s", "No user available"));
        mTxtError.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            mBar.setVisibility(View.VISIBLE);
        } else {
            mBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(User user) {
        Intent data = new Intent(getApplicationContext(), ChatActivity.class);
        data.putExtra(Constants.KEY_USER, user);
        startActivity(data);
        finish();
    }
}