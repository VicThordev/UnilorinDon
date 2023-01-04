package com.folahan.unilorinapp.Activity;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.folahan.unilorinapp.Adapter.RecentConversationAdapter;
import com.folahan.unilorinapp.Listeners.ConversionsListener;
import com.folahan.unilorinapp.Model.ChatMessage;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.User;
import com.folahan.unilorinapp.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RecentChatActivity extends BaseActivity implements ConversionsListener {
    private List<ChatMessage> conversations;
    private RecentConversationAdapter conversationAdapter;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore database;
    private RecyclerView mRecyclerView;
    private ProgressBar mBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chat);
        mRecyclerView = findViewById(R.id.conversationRecyclerView);
        preferenceManager = new PreferenceManager(getApplicationContext());
        mBar = findViewById(R.id.progressBar);
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        listenConversations();
    }

    private void listenConversations() {
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener =
            (value, error) -> {
        if (error != null) {
            return;
        } if (value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    ChatMessage message = new ChatMessage();
                    message.setSenderId(senderId);
                    message.setReceiveId(receiverId);
                    if (preferenceManager.getString(Constants.KEY_USER_ID)
                    .equals(senderId)) {
                        message.setConversionImage(documentChange.getDocument()
                        .getString(Constants.KEY_RECEIVER_IMAGE));
                        message.setConversionName(documentChange.getDocument()
                        .getString(Constants.KEY_RECEIVER_NAME));
                        message.setConversionId(documentChange.getDocument()
                        .getString(Constants.KEY_RECEIVER_ID));
                    } else {
                        message.setConversionImage(documentChange.getDocument().getString(Constants
                        .KEY_SENDER_IMAGE));
                        message.setConversionName(documentChange.getDocument()
                        .getString(Constants.KEY_SENDER_NAME));
                        message.setConversionId(documentChange.getDocument()
                        .getString(Constants.KEY_SENDER_NAME));
                    }
                    message.setMessage(documentChange.getDocument()
                    .getString(Constants.KEY_LAST_MESSAGE));
                    message.setDateObject(documentChange.getDocument()
                    .getDate(Constants.KEY_TIMESTAMP));
                    conversations.add(message);
                } else if (documentChange.getType() ==
                DocumentChange.Type.ADDED) {
                    for (int i = 0; i < conversations.size(); i++) {
                        String senderId = documentChange.getDocument()
                                .getString(Constants.KEY_SENDER_ID);
                        String receiverId = documentChange.getDocument()
                                .getString(Constants.KEY_RECEIVER_ID);
                        if (conversations.get(i).getSenderId().equals(senderId) &&
                        conversations.get(i).getReceiveId().equals(receiverId)) {
                            conversations.get(i).setMessage(documentChange.getDocument()
                            .getString(Constants.KEY_LAST_MESSAGE));
                            conversations.get(i).setDateObject(documentChange.getDocument()
                                    .getDate(Constants.KEY_TIMESTAMP));
                            break;
                        }
                    }
                }
            }
                    conversations.sort((obj1, obj2) ->
                            obj2.dateObject.compareTo(obj1.dateObject));
            conversationAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(0);
            mRecyclerView.setVisibility(View.VISIBLE);
            mBar.setVisibility(View.GONE);
                }
            };

    private void init() {
        conversations = new ArrayList<>();
        conversationAdapter = new RecentConversationAdapter(conversations, this);
        mRecyclerView.setAdapter(conversationAdapter);
        database = FirebaseFirestore.getInstance();
    }

    @Override
    public void onConversionClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
    }
}