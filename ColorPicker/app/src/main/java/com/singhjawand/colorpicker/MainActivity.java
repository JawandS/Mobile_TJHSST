package com.singhjawand.colorpicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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

import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RangeSlider red, green, blue;
    TextView r_view, blue_view, g_view;
    LinearLayout color_storage;
    ConstraintLayout bg;
    float r, g, b;
    ArrayList<ColorObject> colors = new ArrayList<>();
    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        red = findViewById(R.id.red);
        red.setValueFrom(0);
        red.setValueTo(254);
//        red.onTouchEvent()

        green = findViewById(R.id.green);
        green.setValueFrom(0);
        green.setValueTo(254);

        blue = findViewById(R.id.blue);
        blue.setValueFrom(0);
        blue.setValueTo(254);

        color_storage = findViewById(R.id.color_storage);

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

        r = red.getValues().get(0);
        red.setBackgroundColor(Color.rgb(r, 0, 0));
        g = green.getValues().get(0);
        green.setBackgroundColor(Color.rgb(0, g, 0));
        b = blue.getValues().get(0);
        blue.setBackgroundColor(Color.rgb(0, 0, b));

//        colors.add(new ColorObject(r, g, b));
//        update();

        r_view.setText((int) r + "");
        g_view.setText((int) g + "");
        blue_view.setText((int) b + "");
System.out.println("Testing: "+r+","+g+","+b);
        color_storage.setBackgroundColor(Color.rgb(r, g, b));
    }

    @SuppressLint("SetTextI18n")
    public void randomBackground(View view) {
        randomBackground();
    }

    @SuppressLint("SetTextI18n")
    public void randomBackground(){
        ColorObject c = new ColorObject();
        colors.add(c);

        r = c.r;
        g = c.g;
        b = c.b;

        red.setValues(r);
        green.setValues(g);
        blue.setValues(b);

        red.setBackgroundColor(Color.rgb(r, 0, 0));
        green.setBackgroundColor(Color.rgb(0, g, 0));
        blue.setBackgroundColor(Color.rgb(0, 0, b));

        r_view.setText((int) r + "");
        g_view.setText((int) g + "");
        blue_view.setText((int) b + "");

        color_storage.setBackgroundColor(Color.rgb(r, g, b));
    }

    public void copy(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("RGB", (int) r + ", " + (int) g + ", " + (int) b);
        clipboard.setPrimaryClip(clip);
    }
}



class ColorObject{
    public float r, g, b;
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
        temp.setText((int) r + " " + (int) g + " " + (int) b);
        temp.setTextSize(50);
        temp.setBackgroundColor(Color.rgb(r, g, b));
    }

}