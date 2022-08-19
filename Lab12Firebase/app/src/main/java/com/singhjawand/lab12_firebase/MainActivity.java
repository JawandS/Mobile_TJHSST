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
    int token_len = 8;

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

        if (!global_token.equals("") && userInput != null) {
            System.out.println("Entering test with: " + global_token);
            enterText(global_token);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!global_token.equals("") && userInput != null) {
            System.out.println("Entering test with: " + global_token);
            enterText(global_token);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!global_token.equals("") && userInput != null) {
            System.out.println("Entering test with: " + global_token);
            enterText(global_token);
        }
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
                            setContentView(R.layout.settings);
                            userInput = findViewById(R.id.userInput);
                            user_id = email.replace(".", "").toLowerCase();
                            generateTokens(email);
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
                            setContentView(R.layout.settings);
                            user_id = email.replace(".", "").toLowerCase();
                            findTokens(email);
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

    public String createToken(int token_len) {
        // ToDo - add tokens to running list and ensure no two token are the same

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(token_len);

        for (int i = 0; i < token_len; i++) {
            // generate a random number between 0 to AlphaNumericString variable length
            int index = (int) (AlphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public void generateTokens(String user_id) {
        setContentView(R.layout.settings);
        LinearLayout workspaces = findViewById(R.id.workspaces);

        user_id = user_id.replace(".", "");
        user_id = user_id.toLowerCase();

        myRef = database.getReference(user_id);

        String new_token = createToken(token_len);
        myRef.setValue(new_token);

        Button child = new Button(getApplicationContext());
        child.setText(new_token);
        // Set on click listener for button
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                userInput = findViewById(R.id.userInput);

                global_token = new_token;
            }
        });
        workspaces.addView(child);
    }

    public void findTokens(String user_id) {
        setContentView(R.layout.settings);

        LinearLayout workspaces = findViewById(R.id.workspaces);
        LinearLayout copy_tokens = findViewById(R.id.tokens);

        user_id = user_id.replace(".", "");
        user_id = user_id.toLowerCase();
        myRef = database.getReference(user_id);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                tokens_array = dataSnapshot.getValue(String.class).split(" ");
                Set<String> tokens_used = new HashSet<>();

                for (String token : tokens_array) {
                    if (tokens_used.contains(token))
                        continue;
                    else
                        tokens_used.add(token);

                    Button workspace_child = new Button(getApplicationContext());
                    workspace_child.setText(token);
                    // Set on click listener for button
                    workspace_child.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Change view to main (workspace)
                            setContentView(R.layout.activity_main);
                            // find the edit text in the workspace
                            userInput = findViewById(R.id.userInput);

                            // find and update the edit text with text
                            findText(token);
                            // set the global_token - which workspace is active
                            global_token = token;
                        }
                    });
                    workspaces.addView(workspace_child);

                    Button copyTokens_child = new Button(getApplicationContext());
                    copyTokens_child.setText(token);
                    // Set on click listener for button
                    copyTokens_child.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Button view = (Button) v;
                            String token_value = view.getText().toString();

                            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Token", token_value);
                            clipboard.setPrimaryClip(clip);
                        }
                    });
                    copy_tokens.addView(copyTokens_child);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void enterText(String id) {
        System.out.println("id: " + id);

//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child(id).push().setValue(userInput.getText().toString());

        myRef = database.getReference(id);
        myRef.setValue(userInput.getText().toString());
    }


    @SuppressLint("SetTextI18n")
    public void findText(String id) {
        System.out.println("id: " + id);
        myRef = database.getReference(id);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                if (value != null)
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

    public void addToken(View view) {
        String new_token = createToken(token_len);

        myRef = database.getReference(user_id);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                myRef.setValue(value + " " + new_token);

                System.out.println("Firebase test: Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                System.out.println("Failed to read value." + databaseError.toException());
            }
        });

        setContentView(R.layout.activity_main);
        userInput = findViewById(R.id.userInput);

        global_token = new_token;
    }

    public void addNewToken(View view) {
        // Add new token from user input

        EditText newToken = findViewById(R.id.new_token);
        String new_token = newToken.getText().toString();

        myRef = database.getReference(user_id);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                myRef.setValue(value + " " + new_token);

                System.out.println("Firebase test: Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                System.out.println("Failed to read value." + databaseError.toException());
            }
        });

        setContentView(R.layout.activity_main);
        userInput = findViewById(R.id.userInput);

        global_token = new_token;
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