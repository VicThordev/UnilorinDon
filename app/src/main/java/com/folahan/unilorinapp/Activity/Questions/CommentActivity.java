package com.folahan.unilorinapp.Activity.Questions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.Adapter.CommentAdapter;
import com.folahan.unilorinapp.CommentClassDatabase.CommentViewModel;
import com.folahan.unilorinapp.Model.ChatMessage;
import com.folahan.unilorinapp.Model.CommentClass;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.User;
import com.folahan.unilorinapp.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CommentActivity extends AppCompatActivity {

    private ArrayList<User> comment;
    public static final String EXTRA_COMMENT = "com.folahan.unilorin_don.extra_comment";
    private User user;
    public static int Add_Comment_Request = 1;
    private TextView txtName, txtComment, txtLike, txtCommentNo;
    private FirebaseFirestore database;
    private Button mBtnComment;
    private EditText mEdtComment;
    private CommentViewModel viewModel;
    private CommentAdapter adapter;
    private String conversionId = null;
    private RecyclerView mRecyclerView;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        txtName = findViewById(R.id.txtNameComment1);
        txtComment = findViewById(R.id.txtComment1);
        txtLike = findViewById(R.id.txtLikeComment);
        txtCommentNo = findViewById(R.id.txtCommentNo1);
        mEdtComment = findViewById(R.id.edtComment);
        mRecyclerView = findViewById(R.id.recycler_view_comment);
        mBtnComment = findViewById(R.id.btnComment);

        preferenceManager = new PreferenceManager(getApplicationContext());
        database = FirebaseFirestore.getInstance();

        mBtnComment.setOnClickListener(view -> {
            Intent data = new Intent();
            String message = mEdtComment.getText().toString();
            if (message.trim().isEmpty()) {
                Toast.makeText(this, "Empty comment cannot be post", Toast.LENGTH_SHORT).show();
                mBtnComment.setEnabled(false);

                data.putExtra(EXTRA_COMMENT, user);
                setResult(RESULT_OK, data);
            }
        });

        adapter = new CommentAdapter();
        comment = new ArrayList<>();

        RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Add_Comment_Request && resultCode == RESULT_OK) {
            assert data!=null;

            CommentClass comment = (CommentClass) data.getSerializableExtra(EXTRA_COMMENT);
            viewModel.insert(comment);
            Toast.makeText(this, "Comment Posted", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void sendMessage() {
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        message.put(Constants.KEY_RECEIVER_ID, user.id);
        message.put(Constants.KEY_MESSAGE, mEdtComment.getText().toString());
        message.put(Constants.KEY_TIMESTAMP, new Date());
        database.collection(Constants.KEY_COLLECTION_CHAT).add(message);
        if (conversionId != null) {
            updateConversion(mEdtComment.getText().toString());
        } else {
            HashMap<String, Object> conversion = new HashMap<>();
            conversion.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
            conversion.put(Constants.KEY_SENDER_NAME, preferenceManager.getString(Constants.KEY_USERNAME));
            conversion.put(Constants.KEY_SENDER_IMAGE, preferenceManager.getString(Constants.KEY_IMAGE));
            conversion.put(Constants.KEY_RECEIVER_ID, user.id);
            conversion.put(Constants.KEY_RECEIVER_NAME, user.getUsername());
            conversion.put(Constants.KEY_IMAGE, user.getImage());
            //conversion.put(Constants.KEY_COMMENT_NO, user.getCommentNo());
            conversion.put(Constants.KEY_LAST_COMMENT, mEdtComment.getText().toString());
            conversion.put(Constants.KEY_TIMESTAMP, new Date());
            addConversion(conversion);
        }
    }

    private void addConversion(HashMap<String, Object> conversion) {
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .add(conversion)
                .addOnSuccessListener(documentReference ->
                        conversionId = documentReference.getId());
    }

    private void updateConversion(String message) {
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_COMMENT)
                        .document(conversionId);
        documentReference.update(
                Constants.KEY_LAST_COMMENT, message,
                Constants.KEY_TIMESTAMP, new Date()
        );
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            int count = comment.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    CommentClass chatMessage = new CommentClass();
                    chatMessage.setName(documentChange.getDocument().getString(Constants.KEY_SENDER_ID));
                    //chatMessage.setReceiveId(documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID));
                    chatMessage.setComment(documentChange.getDocument().getString(Constants.KEY_COLLECTION_COMMENT));
                    chatMessage.setLike(getReadableDateTime(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP)));
                    //chatMessage.setLikeNo(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                    comment.add(user);
                }
            }
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        //mBar.setVisibility(View.GONE);

    };

    private String getReadableDateTime(Date date) {
        return new SimpleDateFormat(
                "dd MMMM, yyyy - hh:mm a", Locale.getDefault())
                .format(date);
    }
}