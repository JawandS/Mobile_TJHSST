package com.singhjawand.lab12_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText userInput;
    DatabaseReference myRef;
    FirebaseDatabase database;
    String[] tokens_array;
    String user_id;
    String global_token = "";
    UserData default_user = new UserData();

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
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
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
        // global token - active workspace
        // userInput - editText in activity_main (V0)
        if (!global_token.equals("")) {
            System.out.println("Entering test with: " + global_token);
            // ToDo: replace default_user with actual user
            saveUserData(global_token, default_user);
        } else {
            System.out.println("Saving data failed");
        }
    }

    private void updateUI(FirebaseUser user) {
        // Prints to console authentication test
        if (user != null) {
            System.out.println("Authentication Test:  Email: " + user.getEmail() + "; Status: " +
                    user.isEmailVerified() + "; ID: " + user.getUid());
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
                            updateUI(user);

                            // Change view to tokens screen
                            setContentView(R.layout.settings);

                            user_id = email.replace(".", "").toLowerCase();
                            global_token = user_id;
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
                            updateUI(user);
                            setContentView(R.layout.settings);
                            user_id = email.replace(".", "").toLowerCase();
                            global_token = user_id;
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


    @SuppressLint("SetTextI18n")
    public void findText(String id) {
        // Find the text for a particular is
        myRef = database.getReference(id);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                if (value != null) {
                    System.out.println("Read from database: " + value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Unable to retrieve data
                Toast.makeText(getApplicationContext(), "Unable to Retrieve Text",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveUserData(String tkn, UserData datum){
        myRef = database.getReference(tkn);
//        myRef.setValue(datum);
        myRef.setValue("test");
    }

}

