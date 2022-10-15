package com.folahan.unilorinapp.Adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.folahan.unilorinapp.Listeners.QuestionListListener;
import com.folahan.unilorinapp.Listeners.UserListener;
import com.folahan.unilorinapp.Model.QuestionList;
import com.folahan.unilorinapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private final List<QuestionList> messages;
    private final Bitmap receivedProfileImage;
    private final String senderId;
    private final QuestionListListener userListener;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public QuestionAdapter(List<QuestionList> messages, Bitmap receivedProfileImage, String senderId, QuestionListListener users) {
        super();
        this.messages = messages;
        this.receivedProfileImage = receivedProfileImage;
        this.senderId = senderId;
        this.userListener = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setUserData(messages.get(position), receivedProfileImage);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mComment, mName, mUsername;
        private RoundedImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mComment = itemView.findViewById(R.id.questionBox);
            mName = itemView.findViewById(R.id.name);
            mUsername = itemView.findViewById(R.id.uniqueId);
            imageView = itemView.findViewById(R.id.imgQuestion);
        }

        void setUserData(QuestionList list, Bitmap receivedImage) {
            mComment.setText(list.getComment());
            mName.setText(list.getName());
            mUsername.setText(list.getId());
            imageView.setImageBitmap(receivedImage);
            itemView.setOnClickListener(view -> {
                userListener.onQuestionClicked(list);
            });
        }
    }
}
