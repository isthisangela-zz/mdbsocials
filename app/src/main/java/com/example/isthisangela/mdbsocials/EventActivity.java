package com.example.isthisangela.mdbsocials;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    private ImageView pic;
    private TextView name;
    private TextView email;
    private TextView interested;
    private Button interestedButton;
    private boolean interestedBoolean;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        pic = findViewById(R.id.pic);
        email = findViewById(R.id.email);
        interested = findViewById(R.id.interested);
        interestedButton = findViewById(R.id.interestedbutton);

        interestedBoolean = false;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/socials/" + getIntent().getStringExtra("id"));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                interested.setText(dataSnapshot.child("interested").getValue(String.class));
                Utils.setBitmap(getApplicationContext(), pic, R.id.pic, getIntent().getStringExtra("id"));
                description.setText(dataSnapshot.child("description").getValue(String.class));
                email.setText(dataSnapshot.child("email").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        }
        interestedButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!interestedBoolean) {
                    interested.setText(Integer.parseInt(interested.getText().toString()) + 1);
                } else {
                    interested.setText(Integer.parseInt(interested.getText().toString()) - 1);
                }
                interestedBoolean = !interestedBoolean;
            }
        });
    }
}
