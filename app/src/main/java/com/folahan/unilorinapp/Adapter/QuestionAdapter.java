package com.folahan.unilorinapp.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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
    private final QuestionListListener userListener;

    public QuestionAdapter(List<QuestionList> messages, QuestionListListener users) {
        super();
        this.messages = messages;
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
        holder.setUserData(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mComment, mName, mUsername, mQuestion, mLike;
        private RoundedImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mComment = itemView.findViewById(R.id.commentNo);
            mLike = itemView.findViewById(R.id.likeNo);
            mName = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.imgQuestion);
            mUsername = itemView.findViewById(R.id.uniqueId);
            mQuestion = itemView.findViewById(R.id.questionBox);
        }

        void setUserData(QuestionList list) {
            mComment.setText(list.getComment());
            mName.setText(list.getName());
            mUsername.setText(list.getUsername());
            imageView.setImageBitmap(getUserImage(list.getImage()));
            itemView.setOnClickListener(view ->
                    userListener.onQuestionClicked(list));
            mQuestion.setText(list.getQuestion());
            mLike.setText(list.getLike());
        }
    }
    private Bitmap getUserImage(String encodedImage) {
        byte [] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
