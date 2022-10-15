package com.folahan.unilorinapp.Activity.Questions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.folahan.unilorinapp.Adapter.QuestionAdapter;
import com.folahan.unilorinapp.Listeners.QuestionListListener;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.QuestionList;
import com.folahan.unilorinapp.Model.User;
import com.folahan.unilorinapp.PostQuestionDatabase.QuestionViewModel;
import com.folahan.unilorinapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

public class QuestionPage extends AppCompatActivity {
    private ArrayList<QuestionList> mQuestionList;
    private FloatingActionButton fab;
    private User user;
    private QuestionAdapter adapter;
    private RecyclerView mRecyclerView;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore database;
    private String conversionId = null;
    private static int Add_Post_Request = 1;
    private static int Edit_Note_Request = 2;
    private QuestionList list;
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

        list = new QuestionList();

        loadReceiveDetails();

        preferenceManager = new PreferenceManager(getApplicationContext());

        adapter = new QuestionAdapter(mQuestionList, getBitmapFromEncodedString(user.image),
                preferenceManager.getString(Constants.KEY_USER_ID), (QuestionListListener) this);

        //Creating the RecyclerView Layout Manager
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

        /*questionViewModel = new ViewModelProvider(this)
                .get(QuestionViewModel.class);
        questionViewModel.getAllQuestion().observe(this, questionLists -> {
            adapter.submitList(questionLists);
            mQuestionList = new ArrayList<>(questionLists);
        });*/

        database = FirebaseFirestore.getInstance();

        listenMessage();
        /*adapter.setOnItemClickListener(question -> {
            Intent data = new Intent(QuestionPage.this, CommentActivity.class);
            //data.putExtra(CommentActivity.EXTRA_COMMENT, mQuestionList);

            startActivityForResult(data, Edit_Note_Request);
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Add_Post_Request && resultCode ==RESULT_OK) {
            assert data != null;

            //QuestionList list = (QuestionList) data.getSerializableExtra(PostQuestion.EXTRA_POST);
            Toast.makeText(this, "Posted", Toast.LENGTH_SHORT).show();
            //mQuestionList.add(list);
            //questionViewModel.insert(list);
            sendMessage();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        byte [] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void sendMessage()  {
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_POSTER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        message.put(Constants.KEY_RECEIVER_ID, user.id);
        message.put(Constants.KEY_MESSAGE, list.getQuestion());
        message.put(Constants.KEY_TIMESTAMP, new Date());
        database.collection(Constants.KEY_COLLECTION_QUESTION).add(message);
        if (conversionId != null) {
            updateConversion(list.getQuestion());
        } else {
            HashMap<String, Object> conversion = new HashMap<>();
            conversion.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
            conversion.put(Constants.KEY_SENDER_NAME, preferenceManager.getString(Constants.KEY_USERNAME));
            conversion.put(Constants.KEY_SENDER_IMAGE, preferenceManager.getString(Constants.KEY_IMAGE));
            conversion.put(Constants.KEY_QUESTION, preferenceManager.getString(Constants.KEY_QUESTION_BOX));
            conversion.put(Constants.KEY_COMMENTS, preferenceManager.getString(Constants.KEY_COMMENTS_BOX));
            conversion.put(Constants.KEY_LIKES, preferenceManager.getString(Constants.KEY_LIKES_BOX));
            //conversion.put(Constants.KEY_RECEIVER_ID, receiverUser.id);
            //conversion.put(Constants.KEY_RECEIVER_NAME, receiverUser.getUsername());
            //conversion.put(Constants.KEY_IMAGE, receiverUser.getImage());
            conversion.put(Constants.KEY_LAST_MESSAGE, list.getQuestion());
            conversion.put(Constants.KEY_TIMESTAMP, new Date());
            addConversion(conversion);
        }
        //mEdtChat.setText(null);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            int count = mQuestionList.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    QuestionList chatMessage = new QuestionList();
                    chatMessage.setName(documentChange.getDocument().getString(Constants.KEY_SENDER_ID));
                    chatMessage.setId(documentChange.getDocument().getString(Constants.KEY_USERNAME));
                    chatMessage.setQuestion(documentChange.getDocument().getString(Constants.KEY_MESSAGE));
                    chatMessage.setDateTime(getReadableDateTime(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP)));
                    //chatMessage.setDateObject(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                    mQuestionList.add(chatMessage);
                }
            }
            Collections.sort(mQuestionList, Comparator.comparing(obj -> obj.getId()));
            if (count == 0) {
                adapter.notifyDataSetChanged();
            } else {
                adapter.notifyItemRangeInserted(mQuestionList.size(), mQuestionList.size());
                mRecyclerView.smoothScrollToPosition(mQuestionList.size()-1);
            }
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        //mBar.setVisibility(View.GONE);
        if (conversionId == null) {
            checkForConversion();
        }
    };

    private String getReadableDateTime(Date date) {
        return new SimpleDateFormat(
                "dd MMMM, yyyy - hh:mm a", Locale.getDefault())
                .format(date);
    }

    private void listenMessage() {
        database.collection(Constants.KEY_COLLECTION_QUESTION)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .whereEqualTo(Constants.KEY_RECEIVER_ID, user.id)
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_QUESTION)
                .whereEqualTo(Constants.KEY_SENDER_ID, user.id)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }

    private void loadReceiveDetails() {
        user = (User) getIntent().getSerializableExtra(Constants.KEY_USER);
//        user.getUsername();
    }

    private void checkForConversion() {
        if (mQuestionList.size() != 0) {
            checkForConversionRemotely(
                    preferenceManager.getString(Constants.KEY_USER_ID),
                    user.id
            );
            checkForConversionRemotely(
                    user.id,
                    preferenceManager.getString(Constants.KEY_USER_ID)
            );
        }
    }

    private void checkForConversionRemotely(String senderId, String receiverId) {
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_SENDER_ID, senderId)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, receiverId)
                .get()
                .addOnCompleteListener(conversionOnCompleteListener);
    }

    private final OnCompleteListener<QuerySnapshot> conversionOnCompleteListener =
            task -> {
                if (task.isSuccessful() && task.getResult() != null
                        && task.getResult().getDocuments().size() > 0) {
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    conversionId = documentSnapshot.getId();
                }
            };

    private void addConversion(HashMap<String, Object> conversion) {
        database.collection(Constants.KEY_COLLECTION_QUESTION)
                .add(conversion)
                .addOnSuccessListener(documentReference ->
                        conversionId = documentReference.getId());
    }

    private void updateConversion(String message) {
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_QUESTION)
                        .document(conversionId);
        documentReference.update(
                Constants.KEY_LAST_QUESTION, message,
                Constants.KEY_TIMESTAMP, new Date()
        );
    }
}