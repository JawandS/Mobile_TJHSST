package com.singhjawand.organizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    DatabaseReference myRef;
    FirebaseDatabase database;

    String user_id;
    String global_token = "";
    UserData userData;

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
        myRef = database.getReference(global_token);

        if (!global_token.equals("")) {
            System.out.println("Entering test with: " + global_token);
            // ToDo: replace default_user with actual user
            if (userData == null) {
                myRef.setValue(new UserData());
                System.out.println("Error: no user data");
            } else {
                myRef.setValue(userData);
            }
        } else {
            System.out.println("Saving data failed");
        }
    }

    public void loadData() {
        if (!global_token.equals("")) {
            myRef = database.getReference(global_token);

            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    userData = dataSnapshot.getValue(UserData.class);
                    System.out.println("Loaded user data: " + userData.getUser_id());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    System.out.println("Getting data failed");
                }
            });
        }
    }


    private void updateUI(FirebaseUser user) {
        // Prints to console authentication test
        if (user != null) {
            System.out.println("Authentication Test:  Email: " + user.getEmail() + "; Status: " +
                    user.isEmailVerified() + "; ID: " + user.getUid());
            // Sets view to main
            setContentView(R.layout.activity_main);
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

                            user_id = email.replace(".", "").toLowerCase();
                            global_token = user_id;
                            loadData();
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

}