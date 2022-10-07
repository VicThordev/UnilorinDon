package com.folahan.unilorinapp.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.recyclerview.widget.RecyclerView;

public class UsersAdapter {

    class UserViewHolder extends RecyclerView.ViewHolder {
        
    }

    private Bitmap getUserImage(String encodedImage) {
        byte [] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
