package com.singhjawand.colorpicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText red, green, blue;
    TextView r_view, blue_view, g_view;
    Button color_storage;
    LinearLayout container;
    ConstraintLayout bg;
    ArrayList<ColorObject> colors = new ArrayList<>();
    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        red = findViewById(R.id.red);
        green = findViewById(R.id.green);
        blue = findViewById(R.id.blue);

        color_storage = findViewById(R.id.color_storage);

        r_view = findViewById(R.id.red_text);
        g_view = findViewById(R.id.green_text);
        blue_view = findViewById(R.id.blue_text);

        container = findViewById(R.id.container);

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

        int r = Integer.parseInt(red.getText().toString());
        int g = Integer.parseInt(green.getText().toString());
        int b = Integer.parseInt(blue.getText().toString());

        colors.add(new ColorObject(r, g, b));
        update();

        r_view.setText(r + "");
        g_view.setText(g + "");
        blue_view.setText(b + "");

        bg.setBackgroundColor(Color.rgb(r, g, b));
    }

    @SuppressLint("SetTextI18n")
    public void randomBackground(View view) {
        randomBackground();
    }

    @SuppressLint("SetTextI18n")
    public void randomBackground(){
        ColorObject c = new ColorObject();
        colors.add(c);
        update();

        int red = c.r;
        int green = c.g;
        int blue = c.b;

        r_view.setText(red + "");
        g_view.setText(green + "");
        blue_view.setText(blue + "");

        color_storage.setBackgroundColor(Color.rgb(red, green, blue));
    }

    @SuppressLint("SetTextI18n")
    public void update(){
        ColorObject color = colors.get(colors.size() - 1);
        TextView temp = new TextView(this);
        color.setView(temp);

        container.addView(temp);
    }
}

class ColorObject{
    public int r, g, b;
    Random rand = new Random();

    public ColorObject(){
        r = rand.nextInt(255);
        g = rand.nextInt(255);
        b = rand.nextInt(255);
    }

    public ColorObject(int red, int green, int blue){
        r = red;
        g = green;
        b = blue;
    }

    @SuppressLint("SetTextI18n")
    public void setView(TextView temp){
        temp.setText(r + " " + g + " " + b);
        temp.setTextSize(50);
        temp.setBackgroundColor(Color.rgb(r, g, b));
    }

}