package com.singhjawand.organizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.LinkedHashSet;

// https://www.youtube.com/watch?v=lpFDFK44pX8

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    FirebaseDatabase database;

    String user_id;
    String global_token = "";
    UserData userData;

    LinearLayout notes;
    EditText newNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Write a message to the database
        database = FirebaseDatabase.getInstance("https://schoolorganiz-default-rtdb.firebaseio.com/");
    }


    @Override
    protected void onPause() {
        super.onPause();

        // Check that a workspace is active, and edit text isn't null, the save edit text
        saveData();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Check that a workspace is active, and edit text isn't null, the save edit text
        saveData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Check that a workspace is active, and edit text isn't null, the save edit text
        saveData();
    }

    public void saveData() {
        DatabaseReference userRef = myRef.child(global_token);

        if (!global_token.equals("")) {
            System.out.println("Entering test with: " + global_token);
            // ToDo: replace default_user with actual user
            if (userData == null) {
//                userRef.setValue(new UserData());
                System.out.println("Error: no user data");
            } else {
                if (newNote != null)
                    userData.addNote(global_token, newNote.getText().toString());
                userRef.setValue(userData);
            }
        } else {
            System.out.println("Saving data failed");
        }
    }

    public boolean loadData() {
        if (!global_token.equals("")) {
            DatabaseReference userRef = myRef.child(global_token);

            final UserData[] tempUserData = new UserData[1];

            // Read from the database


            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    tempUserData[0] = dataSnapshot.getValue(UserData.class);
//                    System.out.println("Loaded user data: " + userData.getUser_id());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Failed to read value
                    System.out.println("Getting data failed");
                }
            });


            userData = tempUserData[0];
            if (userData == null)
                System.out.println("User Data is null");
//            ArrayList<String> allNotes = userData.getNotes();
//            LinkedHashSet<String> uniqueNotes = new LinkedHashSet<>(allNotes);
//
//            for (String note: uniqueNotes){
//                TextView view = new TextView(this);
//                view.setText(note);
//                notes.addView(view);
//            }

            return true;
        } else {
            System.out.println("No global token");
            return false;
        }
    }


    private void updateUI(FirebaseUser user, Boolean new_user) {
        // Prints to console authentication test
        if (user != null) {
            System.out.println("Authentication Test:  Email: " + user.getEmail() + "; Status: " +
                    user.isEmailVerified() + "; ID: " + user.getUid());
            // Sets view to notepad
            setContentView(R.layout.notepad);
            notes = findViewById(R.id.notes);

            // If the user is retuning load and display previous notes
            if (!new_user) {
                // Loads the data from the database
                System.out.println("Loading data: " + loadData());
                if (userData != null) {
                    ArrayList<String> user_notes = userData.getNotes();
                    for (String note : user_notes) {
                        TextView tempNote = new TextView(getApplicationContext());
                        tempNote.setText(note);
                        notes.addView(tempNote);
                    }
                }
            } else {
                // If the user isn't new create a new user
                userData = new UserData(global_token);
            }

            // Add a new edit text
            newNote = new EditText(getApplicationContext());
            newNote.setTextColor(Color.argb(255, 255, 255, 255));
            notes.addView(newNote);

        } else {
            System.out.println("Authentication Test:  No verified user");
        }
    }

    public void signUp(View view) {
        // Signs user up with new email and password
        // ToDo - have a confirm password edit text
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

                            user_id = email.replace(".", "").toLowerCase();
                            global_token = user_id;

                            updateUI(user, true);
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("Authentication test:  createUserWithEmail:failure" + task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null, true);
                        }
                    }
                });
    }

    public void signIn(View view) {
        // Sign in with previously established account
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

                            user_id = email.replace(".", "").toLowerCase();
                            global_token = user_id;

                            updateUI(user, false);
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("Authen test:  signInWithEmail:failure" + task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null, false);
                        }
                    }
                });
    }

}