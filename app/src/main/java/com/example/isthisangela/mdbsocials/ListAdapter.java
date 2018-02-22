package com.example.isthisangela.mdbsocials;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by isthisangela on 2/22/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CustomViewHolder> {
    private Context context;
    private ArrayList<Event> data;

    public ListAdapter(Context context, ArrayList<Event> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);
        return new CustomViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        Event e = data.get(position);
        holder.title.setText(e.getTitle());
        holder.email.setText(e.getEmail());
        holder.interested.setText(e.getInterested());

        //haven't taught this yet but essentially it runs separately from the UI
        //TODO: figure this out
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(e.pic + ".png");
//        Glide.with(context).using(new FirebaseImageLoader()).load(storageReference).into(holder.pic);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * A card displayed in the RecyclerView
     */
    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView title, email, interested;
        ImageView pic;

        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.email = (TextView) view.findViewById(R.id.email);
            this.interested = (TextView) view.findViewById(R.id.interested);
            this.pic = (ImageView) view.findViewById(R.id.pic);
        }
    }
}
