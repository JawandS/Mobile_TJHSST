package com.singhjawand.lab12_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText userInput;
    DatabaseReference myRef;
    FirebaseDatabase database;
    String id = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Write a message to the database
        database = FirebaseDatabase.getInstance("https://lab-12---firebase-default-rtdb.firebaseio.com/");

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onPause() {
        super.onPause();

        enterText();
    }

    @Override
    protected void onStop() {
        super.onStop();

        enterText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        enterText();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            System.out.println("Authentication Test:  Email: " + user.getEmail() + "; Status: " +
                    user.isEmailVerified() + "; ID: " + user.getUid());
        } else {
            System.out.println("Authentication Test:  No verified user");
        }
    }


    public void signUp(View view) {
        TextView newEmail = findViewById(R.id.new_email);
        TextView newPass = findViewById(R.id.new_password);
        String email = newEmail.getText().toString();
        String pass = newPass.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("Authentication test:  createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            setContentView(R.layout.activity_main);
                            userInput = findViewById(R.id.userInput);
                            id = email;
                            findText();
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("Authentication test:  createUserWithEmail:failure" + task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void signIn(View view) {
        TextView oldEmail = findViewById(R.id.old_email);
        TextView oldPass = findViewById(R.id.old_password);
        String email = oldEmail.getText().toString();
        String pass = oldPass.getText().toString();

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("Authen test:  signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            setContentView(R.layout.activity_main);
                            userInput = findViewById(R.id.userInput);
                            id = email;
                            findText();
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("Authen test:  signInWithEmail:failure" + task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void enterText() {
        id = id.replace(".", "");
        System.out.println("id: " + id);
        myRef = database.getReference(id);
        myRef.setValue(userInput.getText().toString());

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                System.out.println("Firebase test: Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                System.out.println("Failed to read value." + databaseError.toException());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void findText() {
        id = id.replace(".", "");
        System.out.println("id: " + id);
        myRef = database.getReference(id);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                userInput.setText(value);
//                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}


//    @Override
//    public void onDataChange(DataSnapshot dataSnapshot) {
//        // This method is called once with the initial value and again
//        // whenever data at this location is updated.
//        String value = dataSnapshot.getValue(String.class);
//        Log.d(TAG, "Value is: " + value);
//    }
//
//    @Override
//    public void onCancelled(DatabaseError error) {
//        // Failed to read value
//        Log.w(TAG, "Failed to read value.", error.toException());
//    }
//});