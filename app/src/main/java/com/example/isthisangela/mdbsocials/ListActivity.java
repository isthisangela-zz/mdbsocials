package com.example.isthisangela.mdbsocials;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    Utils utils;
    ArrayList<Utils.Event> events;
    final ListAdapter adapter = new ListAdapter(this, events);
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/events");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView recyclerAdapter = (RecyclerView) findViewById(R.id.recycle);
        recyclerAdapter.setLayoutManager(new LinearLayoutManager(this));
        utils = new Utils();
        ListAdapter adapter = new ListAdapter(getApplicationContext(), utils.getEvents());
        recyclerAdapter.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewEventActivity.class);
                startActivity(intent);
            }
        });
    }
}
