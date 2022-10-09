package com.folahan.unilorinapp.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.folahan.unilorinapp.Listeners.UserListener;
import com.folahan.unilorinapp.Model.User;
import com.folahan.unilorinapp.R;
import com.folahan.unilorinapp.databinding.ItemContainerUserBinding;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private final List<User> users;
    private final UserListener userListener;

    public UsersAdapter(List<User> users, UserListener userListener) {
        super();
        this.users = users;
        this.userListener = userListener;
    }



    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_container_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView mInfo;
        private TextView mCategory;
        private RoundedImageView mImage;
        private UserListener listener;


        UserViewHolder(View view) {
            super(view);

            mInfo = view.findViewById(R.id.txtNameUser);
            mCategory = view.findViewById(R.id.txtNameUserName);
            mImage = view.findViewById(R.id.imageUser);
        }

        void setUserData(User user) {
            mInfo.setText(user.getUsername());
            mCategory.setText(user.getEmail());
            mImage.setImageBitmap(getUserImage(user.getImage()));
            itemView.setOnClickListener(view -> {
                userListener.onUserClicked(user);
            });

            //listener.onUserClicked(user);
        }
    }

    private Bitmap getUserImage(String encodedImage) {
        byte [] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


}
