package com.singhjawand.colorpicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText red, green, blue;
    TextView r_view, blue_view, g_view;
    ConstraintLayout bg;
    int r, g, b;
    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        red = findViewById(R.id.red);
        green = findViewById(R.id.green);
        blue = findViewById(R.id.blue);

        r_view = findViewById(R.id.red_text);
        g_view = findViewById(R.id.green_text);
        blue_view = findViewById(R.id.blue_text);

        bg = findViewById(R.id.background);

        rand = new Random();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            randomBackground();
        }
        return super.onTouchEvent(event);
    }

    @SuppressLint("SetTextI18n")
    public void changeBackground(View view) {
        changeBackground();
    }

    @SuppressLint("SetTextI18n")
    public void changeBackground(){
        r = Integer.parseInt(red.getText().toString());
        g = Integer.parseInt(green.getText().toString());
        b = Integer.parseInt(blue.getText().toString());

        r_view.setText(r + "");
        g_view.setText(g + "");
        blue_view.setText(b + "");

        bg.setBackgroundColor(Color.rgb(r, g, b));
    }

    @SuppressLint("SetTextI18n")
    public void randomBackground(View view) {

    }

    @SuppressLint("SetTextI18n")
    public void randomBackground(){
        r = rand.nextInt(255);
        g = rand.nextInt(255);
        b = rand.nextInt(255);

        r_view.setText(r + "");
        g_view.setText(g + "");
        blue_view.setText(b + "");

        bg.setBackgroundColor(Color.rgb(r, g, b));
    }
}