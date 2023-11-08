package com.example.usermanagement;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Console;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<UserModel> userList;


    public CustomAdapter(Context context, ArrayList<UserModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        UserModel user = userList.get(position);
        // Bind data to the TextViews in your ViewHolder
        holder.idTextView.setText(String.valueOf(user.getId()));
        holder.nameTextView.setText(user.getName());
        holder.ageTextView.setText(String.valueOf(user.getAge()));
        holder.emailTextView.setText(user.getEmail());

        byte[] avatarData = user.getAvatar();
        if (avatarData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(avatarData, 0, avatarData.length);
            holder.avatarView.setImageBitmap(bitmap);
        } else {
            // Set a default image if the avatar data is null
            holder.avatarView.setImageResource(R.drawable.ic_person);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView idTextView, nameTextView, ageTextView, emailTextView;

        ImageView avatarView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idTextView = itemView.findViewById(R.id.id_tv);
            nameTextView = itemView.findViewById(R.id.name_tv);
            ageTextView = itemView.findViewById(R.id.age_tv);
            emailTextView = itemView.findViewById(R.id.email_tv);
            avatarView = itemView.findViewById(R.id.avatar_view);
        }
    }
}
