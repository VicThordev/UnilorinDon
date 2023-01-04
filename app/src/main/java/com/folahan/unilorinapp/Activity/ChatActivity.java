package com.folahan.unilorinapp.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.Adapter.ChatAdapter;
import com.folahan.unilorinapp.Model.ChatMessage;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.Model.User;
import com.folahan.unilorinapp.Network.ApiClient;
import com.folahan.unilorinapp.Network.ApiService;
import com.folahan.unilorinapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends BaseActivity {

    private EditText mEdtChat;
    private TextView mTxtChat, mTxtAvailable;
    private RecyclerView mRecyclerView;
    private ProgressBar mBar;
    private AppCompatImageView mImageView;
    private User receiverUser;
    private FrameLayout view;
    private List<ChatMessage> messages;
    private ChatAdapter adapter;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore database;
    private String conversionId = null, encodedImage;
    private Boolean isReceiverAvailable = false;
    private RoundedImageView imageView;

    String [] cameraPermissions;
    String [] storagePermissions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mEdtChat = findViewById(R.id.inputMessage);
        mTxtAvailable = findViewById(R.id.textAvailability);
        view = findViewById(R.id.layoutSend);
        mTxtChat = findViewById(R.id.txtNameChat);
        mBar = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.chatRecyclerView);
        mImageView = findViewById(R.id.imageBack);
        imageView = findViewById(R.id.insertImage);
        Objects.requireNonNull(getSupportActionBar()).hide();

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        loadReceiveDetails();
        setListeners();
        init();
        listenMessage();
    }

    private void init() {
        preferenceManager = new PreferenceManager(getApplicationContext());
        messages = new ArrayList<>();
        adapter = new ChatAdapter(
                messages,
                getBitmapFromEncodedString(receiverUser.getImage()),
                preferenceManager.getString(Constants.KEY_USER_ID)
        );
        mRecyclerView.setAdapter(adapter);
        database = FirebaseFirestore.getInstance();
    }

    private void sendMessage()  {
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        message.put(Constants.KEY_RECEIVER_ID, receiverUser.id);
        message.put(Constants.KEY_MESSAGE, mEdtChat.getText().toString());
        message.put(Constants.KEY_TIMESTAMP, new Date());
        database.collection(Constants.KEY_COLLECTION_CHAT).add(message);
        if (conversionId != null) {
            updateConversion(mEdtChat.getText().toString());
        } else {
            HashMap<String, Object> conversion = new HashMap<>();
            conversion.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
            conversion.put(Constants.KEY_SENDER_NAME, preferenceManager.getString(Constants.KEY_USERNAME));
            conversion.put(Constants.KEY_SENDER_IMAGE, preferenceManager.getString(Constants.KEY_IMAGE));
            conversion.put(Constants.KEY_RECEIVER_ID, receiverUser.id);
            conversion.put(Constants.KEY_RECEIVER_NAME, receiverUser.getUsername());
            conversion.put(Constants.KEY_IMAGE, receiverUser.getImage());
            conversion.put(Constants.KEY_LAST_MESSAGE, mEdtChat.getText().toString());
            conversion.put(Constants.KEY_TIMESTAMP, new Date());
            addConversion(conversion);
        }
        if (!isReceiverAvailable) {
            try {
                JSONArray tokens = new JSONArray();
                tokens.put(receiverUser.token);

                JSONObject data = new JSONObject();
                data.put(Constants.KEY_USER_ID, preferenceManager
                        .getString(Constants.KEY_USER_ID));
                data.put(Constants.KEY_USERNAME, preferenceManager
                        .getString(Constants.KEY_USERNAME));
                data.put(Constants.KEY_FCM_TOKEN, preferenceManager
                        .getString(Constants.KEY_FCM_TOKEN));
                data.put(Constants.KEY_MESSAGE, mEdtChat.getText().toString());

                JSONObject body = new JSONObject();
                body.put(Constants.REMOTE_MSG_DATA, data);
                body.put(Constants.REMOTE_MSG_REGISTRATION, tokens);

                sendNotification(body.toString());
            } catch (Exception e) {
                showToast(e.getMessage());
            }
        }
        mEdtChat.setText(null);
    }

    private void listenAvailabilityOfReceiver() {
        database.collection(Constants.KEY_COLLECTION_USERS).document(
                receiverUser.getId()
        ).addSnapshotListener(ChatActivity.this, (value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {
                if (value.getLong(Constants.KEY_AVAILABILITY)!= null) {
                    int availability = Objects.requireNonNull(
                            value.getLong(Constants.KEY_AVAILABILITY)
                    ).intValue();
                    isReceiverAvailable = availability == 1;
                }
                receiverUser.setToken(value.getString(Constants.KEY_FCM_TOKEN));
                if (receiverUser.getImage() == null) {
                    receiverUser.setImage(value.getString(Constants.KEY_IMAGE));
                    adapter.setReceivedProfileImage(getBitmapFromEncodedString(
                            receiverUser.getImage()
                    ));
                    adapter.notifyItemRangeChanged(0, messages.size());
                }
            }
            if (isReceiverAvailable) {
                mTxtAvailable.setVisibility(View.VISIBLE);
            } else {
                mTxtAvailable.setVisibility(View.GONE);
            }
        });
    }

    private void listenMessage() {
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .whereEqualTo(Constants.KEY_RECEIVER_ID, receiverUser.id)
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, receiverUser.id)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            int count = messages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setSenderId(documentChange.getDocument().getString(Constants.KEY_SENDER_ID));
                    chatMessage.setReceiveId(documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID));
                    chatMessage.setMessage(documentChange.getDocument().getString(Constants.KEY_MESSAGE));
                    chatMessage.setDateTime(getReadableDateTime(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP)));
                    chatMessage.setDateObject(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                    messages.add(chatMessage);
                }
            }
            messages.sort(Comparator.comparing(obj -> obj.dateObject));
            if (count == 0) {
                adapter.notifyDataSetChanged();
            } else {
                adapter.notifyItemRangeInserted(messages.size(), messages.size());
                mRecyclerView.smoothScrollToPosition(messages.size()-1);
            }
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        mBar.setVisibility(View.GONE);
        if (conversionId == null) {
            checkForConversion();
        }
    };

    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        if (encodedImage != null) {
            byte [] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }
    }

    private void loadReceiveDetails() {
        receiverUser = (User) getIntent().getSerializableExtra(Constants.KEY_USER);
        mTxtChat.setText(receiverUser.getUsername());
    }

    private void setListeners() {
        mImageView.setOnClickListener(v -> onBackPressed());
        view.setOnClickListener(view1 -> sendMessage());
    }

    @NonNull
    private String getReadableDateTime(Date date) {
        return new SimpleDateFormat(
                "dd MMMM, yyyy - hh:mm a", Locale.getDefault())
                .format(date);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void sendNotification(String messageBody) {
        ApiClient.getClient().create(ApiService.class)
                .sendMessage(
                        Constants.getRemoteMsgHeaders(),
                        messageBody
                ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            JSONObject responseJson = new JSONObject(response.body());
                            JSONArray results = responseJson.getJSONArray("results");
                            if (responseJson.getInt("failure") == 1) {
                                JSONObject error = (JSONObject) results.get(0);
                                showToast(error.getString("error"));
                                return;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    showToast("Notification successfully sent");
                } else {
                    showToast("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private void addConversion(HashMap<String, Object> conversion) {
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .add(conversion)
                .addOnSuccessListener(documentReference ->
                        conversionId = documentReference.getId());
    }

    private void updateConversion(String message) {
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .document(conversionId);
        documentReference.update(
                Constants.KEY_LAST_MESSAGE, message,
                Constants.KEY_TIMESTAMP, new Date()
        );
    }

    private void checkForConversion() {
        if (messages.size() != 0) {
            checkForConversionRemotely(
                    preferenceManager.getString(Constants.KEY_USER_ID),
                    receiverUser.id
            );
            checkForConversionRemotely(
                    receiverUser.id,
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

    @Override
    protected void onResume()  {
        super.onResume();
        listenAvailabilityOfReceiver();
    }
}