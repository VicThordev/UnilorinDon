package com.folahan.unilorinapp.Adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.folahan.unilorinapp.Model.ChatMessage;
import com.folahan.unilorinapp.R;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatMessage> messages;
    private final Bitmap receivedProfileImage;
    private final String senderId;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public ChatAdapter(List<ChatMessage> messages, Bitmap receivedProfileImage, String senderId) {
        this.messages = messages;
        this.receivedProfileImage = receivedProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_sent_messages, parent, false);
            return new SendMessageViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_received_messages, parent,
                            false);
            return new ReceivedMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            ((SendMessageViewHolder) holder).setData(messages.get(position));
        } else {
            ((ReceivedMessageViewHolder) holder).setData(messages.get(position),
                    receivedProfileImage);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSenderId().equals(senderId)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SendMessageViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private TextView mMessage, mDate;

        SendMessageViewHolder(View view) {
            super(view);
            this.view = view;
            mMessage = view.findViewById(R.id.txtMessage);
            mDate = view.findViewById(R.id.txtDateTime);
        }

        void setData(ChatMessage message) {
            mMessage.setText(message.getMessage());
            mDate.setText(message.getDateTime());
        }
    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private TextView mMessage, mDateTime;
        private RoundedImageView imageView;

        ReceivedMessageViewHolder(View view) {
            super(view);
            this.view = view;
            mMessage = view.findViewById(R.id.txtMessageReceived);
            mDateTime = view.findViewById(R.id.textDateTimeReceived);
            imageView = view.findViewById(R.id.imageProfileRecieved);
        }

        void setData(ChatMessage message, Bitmap receivedProfileImage) {
            mMessage.setText(message.getMessage());
            mDateTime.setText(message.getDateTime());
            imageView.setImageBitmap(receivedProfileImage);
        }
    }
}
