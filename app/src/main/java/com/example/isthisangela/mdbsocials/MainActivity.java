package com.example.isthisangela.mdbsocials;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    private static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference ref;

    private Button login, signup;
    private EditText e, p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        e = findViewById(R.id.email);
        p = findViewById(R.id.password);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("wahoo", "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent i = new Intent(MainActivity.this, ListActivity.class);
                    startActivity(i);
                } else {
                    // User is signed out
                    Log.d("yehaa", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignup();
            }
        });
    }

    private void attemptLogin() {
        String email = e.getText().toString();
        String password = p.getText().toString();
        if (!email.equals("") && !password.equals("")) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("wot", "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w("sakjd", "signInWithEmail:failed", task.getException());
                                Toast.makeText(MainActivity.this, "Couldn't log in?!", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(MainActivity.this, ListActivity.class));
                            }
                        }
                    });
        }
    }

    private void attemptSignup() {
        String email = e.getText().toString();
        String password = p.getText().toString();

        if (!email.equals("") && !password.equals("")) {
            if (password.length() < 6) {
                Toast.makeText(MainActivity.this, "Password must be at least 6 characters!", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Signup failed?!", Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(MainActivity.this, ListActivity.class));
                                }
                            }
                        });
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
