package com.folahan.unilorinapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.folahan.unilorinapp.Adapter.ChatAdapter;
import com.folahan.unilorinapp.Model.ChatMessage;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.User;
import com.folahan.unilorinapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText mEdtChat;
    private TextView mTxtChat;
    private RecyclerView mRecyclerView;
    private ProgressBar mBar;
    private AppCompatImageView mImageView, mImageInfo;
    private User receiverUser;
    private List<ChatMessage> messages;
    private ChatAdapter adapter;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mEdtChat = findViewById(R.id.inputMessage);
        mTxtChat = findViewById(R.id.txtNameChat);
        mBar = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.chatRecyclerView);
        mImageView = findViewById(R.id.imageBack);
        mImageInfo = findViewById(R.id.imageInfo);
        loadReceiveDetails();
        setListeners();
    }

    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        byte [] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void loadReceiveDetails() {
        receiverUser = (User) getIntent().getSerializableExtra(Constants.KEY_USER);
        mTxtChat.setText(receiverUser.getUsername());
    }

    private void setListeners() {
        mImageView.setOnClickListener(v -> onBackPressed());
    }
}