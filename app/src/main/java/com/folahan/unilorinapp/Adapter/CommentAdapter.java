package com.folahan.unilorinapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.folahan.unilorinapp.Model.CommentClass;
import com.folahan.unilorinapp.Model.QuestionList;
import com.folahan.unilorinapp.R;

public class CommentAdapter extends ListAdapter<CommentClass, CommentAdapter.CommentHolder> {
    protected CommentAdapter(@NonNull DiffUtil.ItemCallback<CommentClass> diffCallback) {
        super(diffCallback);
    }

    private static final DiffUtil.ItemCallback<CommentClass> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CommentClass>() {
                @Override
                public boolean areItemsTheSame(@NonNull CommentClass oldItem, @NonNull CommentClass newItem) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(@NonNull CommentClass oldItem, @NonNull CommentClass newItem) {
                    return false;
                }

            };

    public CommentAdapter () {
        super(DIFF_CALLBACK);
    }


    protected CommentAdapter(@NonNull AsyncDifferConfig<CommentClass> config) {
        super(config);
    }

    @NonNull
    @Override
    public CommentAdapter.CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_comment, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentHolder holder, int position) {
        CommentClass comment = getItem(position);
        holder.mName.setText(comment.getName());
        holder.mComment.setText(comment.getComment());
        holder.mLike.setText(comment.getLike());
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mComment;
        private TextView mLike;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.txtNameComment);
            mComment = itemView.findViewById(R.id.txtComment);
            mLike = itemView.findViewById(R.id.txtLikeComment);
        }
    }
}
