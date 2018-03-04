package com.example.isthisangela.mdbsocials;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EventActivity extends AppCompatActivity {

    private ImageView pic;
    private TextView name;
    private TextView email;
    private TextView interested;
    private Button interestedButton;
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
    }


}
