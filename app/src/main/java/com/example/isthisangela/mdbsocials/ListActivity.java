package com.example.isthisangela.mdbsocials;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    final ListAdapter adapter = new ListAdapter(this, messages);
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/events");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }
}
