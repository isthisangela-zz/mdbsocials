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
    private ArrayList<Utils.Event> events;

    public ListAdapter(Context context, ArrayList<Utils.Event> events) {
        this.context = context;
        this.events = events;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);
        return new CustomViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        Utils.Event e = events.get(position);
        holder.name.setText(e.getName());
        holder.email.setText(e.getEmail());
        holder.interested.setText(e.getInterested());
        Utils.setBitmap(context, holder.cardView, holder.pic.getId(), e.getId());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(e.getPic() + ".png");
        Glide.with(context).using(new FirebaseImageLoader()).load(storageReference).into(holder.pic);
    }


    @Override
    public int getItemCount() {
        return events.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, email, interested;
        ImageView pic;
        CardView cardView;

        public CustomViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.name);
            this.email = view.findViewById(R.id.email);
            this.interested = view.findViewById(R.id.interested);
            this.pic = view.findViewById(R.id.pic);
            this.cardView = view.findViewById(R.id.cardView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Utils.Event e = events.get(getAdapterPosition());
            Intent intent = new Intent(context, EventActivity.class);
            intent.putExtra("id", e.getId());
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
