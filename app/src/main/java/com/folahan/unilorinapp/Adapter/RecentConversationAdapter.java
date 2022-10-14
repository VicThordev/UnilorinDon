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

import com.folahan.unilorinapp.Listeners.ConversionsListener;
import com.folahan.unilorinapp.Model.ChatMessage;
import com.folahan.unilorinapp.Model.User;
import com.folahan.unilorinapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class RecentConversationAdapter extends RecyclerView.Adapter<RecentConversationAdapter.ConversionViewHolder> {

    private final List<ChatMessage> messages;
    private final ConversionsListener listener;

    public RecentConversationAdapter(List<ChatMessage> chatMessages,
                                     ConversionsListener conversionsListener) {
        this.messages = chatMessages;
        this.listener = conversionsListener;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_container_recent_conversion, parent
                , false);
        return new ConversionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setData(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder {
        private TextView mMessage, mDateTime;
        private RoundedImageView imageView;

        ConversionViewHolder(View view) {
            super(view);
            mMessage = view.findViewById(R.id.txtMessageReceived);
            mDateTime = view.findViewById(R.id.textDateTimeReceived);
            imageView = view.findViewById(R.id.imageProfileRecieved);
        }

        void setData(ChatMessage message) {
            mMessage.setText(message.getMessage());
            mDateTime.setText(message.getConversionName());
            imageView.setImageBitmap(getConversationImage(message.getConversionImage()));
            itemView.setOnClickListener(view1 -> {
                User user = new User();
                user.setId(message.conversionId);
                user.setUsername(message.conversionName);
                user.setImage(message.conversionImage);
                listener.onConversionClicked(user);
            });
        }
    }

    private Bitmap getConversationImage(String encodedImage) {
        byte [] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
