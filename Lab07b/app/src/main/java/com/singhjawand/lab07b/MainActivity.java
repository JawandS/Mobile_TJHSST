package com.singhjawand.lab07b;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    int id = 0;
    LinearLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = findViewById(R.id.mainview);
    }

    public void replace_fragment(View view) {
        EditText edit = findViewById(R.id.id_num);
        int temp_id = Integer.parseInt(String.valueOf(edit.getText()));

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(temp_id, new ReplaceFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }

    public void add_fragment(View view) {
        id++;
        FrameLayout temp = new FrameLayout(this);
        main.addView(temp);
        temp.setId(id);

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment

        ft.add(id, new BasicFragment(), String.valueOf(id));
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }

    public void delete_fragment(View view){
        EditText edit = findViewById(R.id.id_num);
        int temp_id = Integer.parseInt(String.valueOf(edit.getText()));

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(temp_id)));
        ft.commit();
    }

    @SuppressLint("ResourceAsColor")
    public void change_background(View view){
        ColorDrawable color = (ColorDrawable) view.getBackground();
        int colorId = color.getColor();

        if(colorId == R.color.white)
            view.setBackground(new ColorDrawable(R.color.black));
        else
            view.setBackground(new ColorDrawable(R.color.white));
    }
}