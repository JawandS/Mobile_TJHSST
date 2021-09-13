package com.singhjawand.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    EditText name;
    EditText email;
    EditText phone;
    TextView display;
    TextView display2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("values", Context.MODE_PRIVATE);
        editor = prefs.edit();

        name = findViewById(R.id.nameText);
        email = findViewById(R.id.emailText);
        phone = findViewById(R.id.phoneText);
        display = findViewById(R.id.display);
        display2 = findViewById(R.id.display2);
    }

    @SuppressLint("SetTextI18n")
    public void getEmail(View view) {
        String key = String.valueOf(name.getText());
        display.setText(prefs.getString(key, "Not Found"));
        display2.setText(prefs.getString(String.valueOf(key.hashCode()), "###"));
    }

    @SuppressLint("SetTextI18n")
    public void setEmail(View view) {
        String key = String.valueOf(name.getText());
        editor.putString(key, String.valueOf(email.getText()));
        editor.putString(String.valueOf(key.hashCode()), String.valueOf(phone.getText()));
        editor.apply();

        display.setText("Success: " + email.getText());
        display2.setText("Success: " + phone.getText());
    }
}