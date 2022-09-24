package com.folahan.unilorinapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.folahan.unilorinapp.Model.QuestionList;
import com.folahan.unilorinapp.R;

public class QuestionAdapter extends ListAdapter<QuestionList, QuestionAdapter.QuestionHolder> {

    private onItemClickListener listener;

    protected QuestionAdapter(@NonNull DiffUtil.ItemCallback<QuestionList> diffCallback) {
        super(diffCallback);
    }

    protected QuestionAdapter(@NonNull AsyncDifferConfig<QuestionList> config) {
        super(config);
    }

    private static final DiffUtil.ItemCallback<QuestionList> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<QuestionList>() {
                @Override
                public boolean areItemsTheSame(@NonNull QuestionList oldItem, @NonNull QuestionList newItem) {
                    return oldItem == newItem;
                }

                @Override
                public boolean areContentsTheSame(@NonNull QuestionList oldItem, @NonNull QuestionList newItem) {
                    return oldItem.getName().equals(newItem.getName()) && oldItem.getId().equals(newItem.getId()) &&
                            oldItem.getQuestion().equals(newItem.getQuestion()) && oldItem.getComment() == (newItem.getComment());
                }
            };


    public QuestionAdapter () {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public QuestionAdapter.QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_question, parent, false);
        return new QuestionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.QuestionHolder holder, int position) {
        QuestionList question = getItem(position);
        holder.mName.setText(question.getName());
        holder.mUniqueId.setText(question.getId());
        holder.mQuestion.setText(question.getQuestion());
        //holder.mComment.setText(question.getComment());
        //holder.mLike.setText(question.getLike());
    }

    public class QuestionHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mUniqueId;
        private TextView mQuestion;
        private EditText mAnswer;
        private TextView mLike;
        private TextView mComment;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mUniqueId = itemView.findViewById(R.id.uniqueId);
            mQuestion = itemView.findViewById(R.id.questionBox);
            mAnswer = itemView.findViewById(R.id.edtAnswer);
            mLike = itemView.findViewById(R.id.likeNo);
            mComment = itemView.findViewById(R.id.commentNo);
        }
    }
    public interface onItemClickListener {
        void onItemClick(QuestionList question);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

}
