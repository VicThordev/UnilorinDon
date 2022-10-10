package com.folahan.unilorinapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.folahan.unilorinapp.Adapter.RecentConversationAdapter;
import com.folahan.unilorinapp.Model.ChatMessage;
import com.folahan.unilorinapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RecentChatActivity extends AppCompatActivity {
    private List<ChatMessage> conversations;
    private RecentConversationAdapter conversationAdapter;
    private FirebaseFirestore database;
    private RecyclerView mRecyclerView;
    private ProgressBar mBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chat);
        mRecyclerView = findViewById(R.id.conversationRecyclerView);
        mBar = findViewById(R.id.progressBar);
    }

    private void init() {
        conversations = new ArrayList<>();
        conversationAdapter = new RecentConversationAdapter(conversations);
        mRecyclerView.setAdapter(conversationAdapter);
        database = FirebaseFirestore.getInstance();
    }
}