package com.example.isthisangela.mdbsocials;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by isthisangela on 3/3/18.
 */

public class Utils {
    private DatabaseReference ref;
    private ArrayList<Event> events;

    public Utils() {

        events = new ArrayList<Event>();
        ref = FirebaseDatabase.getInstance().getReference("/socials");

        ref.orderByChild("date").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                events.add(new Event(
                        dataSnapshot.getKey(),
                        dataSnapshot.child("name").getValue(String.class),
                        dataSnapshot.child("date").getValue(Date.class),
                        dataSnapshot.child("description").getValue(String.class),
                        dataSnapshot.child("pic").getValue(String.class),
                        dataSnapshot.child("email").getValue(String.class),
                        (int) dataSnapshot.child("interested").getValue(Integer.class)));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public class Event {
        private String id;
        private String name;
        private Date date;
        private String description;
        private String pic;
        private String email;
        private int interested = 0;

        public Event(String id, String name, Date date, String description, String pic, String email, int interested) {
            this.id = id;
            this.name = name;
            this.date = date;
            this.description = description;
            this.pic = pic;
            this.email = email;
            this.interested = interested;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Date getDate() {
            return date;
        }

        public String getDescription() {
            return description;
        }

        public String getPic() {
            return pic;
        }

        public String getEmail() {
            return email;
        }

        public int getInterested() {
            return interested;
        }
    }

    public static void setBitmap(final Context context, final View view, final int imageViewId, String id) {
        class DownloadFilesTask extends AsyncTask<String, Void, Bitmap> {
            protected Bitmap doInBackground(String... strings) {
                try {return Glide.
                        with(context).
                        load(strings[0]).
                        asBitmap().
                        into(200, 200).
                        get();}
                catch (Exception e) {return null;}
                return null;
            }

            protected void onProgressUpdate(Void... progress) {}

            protected void onPostExecute(Bitmap result) {
                ((ImageView)view.findViewById(imageViewId)).setImageBitmap(result);
            }
        }

        FirebaseStorage.getInstance().getReferenceFromUrl("gs://mdbsocials-22fbc.appspot.com").child(id + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                new DownloadFilesTask().execute(uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("dl pic", "it failed");
            }
        });
    }
}