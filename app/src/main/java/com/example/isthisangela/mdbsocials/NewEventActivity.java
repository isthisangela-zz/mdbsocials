package com.example.isthisangela.mdbsocials;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Date;

public class NewEventActivity extends AppCompatActivity {

    DatePicker date;
    EditText name;
    EditText description;
    Button pic, finish;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("/events");
    private Uri file;
    private static FirebaseUser mUser;
    private static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        
        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        description = findViewById(R.id.date); //change
        pic = findViewById(R.id.pic);
        finish = findViewById(R.id.finish);

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(NewEventActivity.this).create();
                alertDialog.setTitle("Select Photo");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Gallery",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //launch gallery
                                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 3);
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                //button turns green
                pic.setBackgroundColor(Color.parseColor("#47b03d"));
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                final String key = ref.child("events").push().getKey();
                StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://mdbsocials-22fbc.appspot.com");
                StorageReference picsRef = storageRef.child(key + ".png");
                picsRef.putFile(file).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(NewEventActivity.this, "need an image!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String n = name.getText().toString();

                        //date
                        int day = date.getDayOfMonth();
                        int month = date.getMonth() + 1;
                        int year = date.getYear();
                        Date date = new Date(year, month, day);

                        String d = description.getText().toString();
                        String e = mAuth.getCurrentUser().getEmail();
                        Intent intent = new Intent(getApplicationContext(), ListActivity.class);

                        ref.child("events").child(key).child("name").setValue(n);
                        ref.child("events").child(key).child("date").setValue(d);
                        ref.child("events").child(key).child("description").setValue(description);
                        ref.child("events").child(key).child("email").setValue(e);
                        ref.child("events").child(key).child("interested").setValue(0);

                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            file = data.getData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
