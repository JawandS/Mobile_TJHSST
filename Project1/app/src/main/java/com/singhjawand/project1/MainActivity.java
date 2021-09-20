package com.singhjawand.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    @Override
    protected void onStart() {
        super.onStart();
    try{

        Map<String, String> map = (Map<String, String>) prefs.getAll();
        List<String> keysAsArray = new ArrayList<String>(map.keySet());
        Random r = new Random();
        String key = keysAsArray.get(r.nextInt(keysAsArray.size()));
        String val = map.get(key);
        display.setText(val);
        name.setText(key);

    }catch (Exception ignored){}

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        try{
            display.setText("Email");
            display2.setText("Phone Number");
            Map<String, String> map = (Map<String, String>) prefs.getAll();
            List<String> keysAsArray = new ArrayList<String>(map.keySet());
            Random r = new Random();
            String key = keysAsArray.get(r.nextInt(keysAsArray.size()));
            String val = map.get(key);
            try{
                int temp = Integer.parseInt(key);
                display2.setText(val);
                for(String k : keysAsArray) {
                    if (k.hashCode() == temp) {
                        name.setText(k);
                        //display.setText(map.get(k));
                    }
                }

            }catch (Exception e){
                display.setText(val);
                //display2.setText(map.get(String.valueOf(key.hashCode())));
                name.setText(key);
            }
        }catch (Exception ignored){}

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