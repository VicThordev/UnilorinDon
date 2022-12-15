package com.folahan.unilorinapp.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView mComment, mName, mUsername, mQuestion, mLike;
    private RoundedImageView imageView;
    private View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(view ->
                mClickListener.onItemClick(view, getAdapterPosition()));

        itemView.setOnLongClickListener(view -> {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
        });
    }

    private ViewHolder.ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }
}
