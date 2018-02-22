package com.example.isthisangela.mdbsocials;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Date;

public class NewEventActivity extends AppCompatActivity {

    DatePicker date;
    EditText title;
    EditText description;
    Button pic, finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        date = findViewById(R.id.date);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        pic = findViewById(R.id.pic);
        finish = findViewById(R.id.finish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //title & description
                String t = title.getText().toString();
                String d = description.getText().toString();
                //date
                int day = date.getDayOfMonth();
                int month = date.getMonth() + 1;
                int year = date.getYear();
                Date date = new Date(year, month, day);
            }
        });
    }
}
