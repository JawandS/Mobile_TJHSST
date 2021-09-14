package com.singhjawand.lab03;

import static com.google.android.material.snackbar.Snackbar.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.Snackbar;
import java.util.Random;


/*
  Ideas:
    - Change slider to timer
        - When timer ends game pauses
        - Whoever has higher score winds
    - OR: change slider to modes
        - each mode has a differnt effect (eg. one players taps value is higher)
    - Flip top text upside down
    - Indclue a second random button upside down
 */

/*




 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    SeekBar slider;
    View layout;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }



    @Override
    protected void onResume() {
        super.onResume();
        top.setText(String.valueOf(prefs.getString(String.valueOf(top.getId()), "1")));
        bottom.setText(String.valueOf(prefs.getString(String.valueOf(bottom.getId()), "1")));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        int val = Integer.parseInt(( (TextView) view ).getText().toString()) + 1;
        ((TextView) view).setText(Integer.toString(val));

        editor.putString(String.valueOf(view.getId()), String.valueOf(val));
        editor.apply();
    }

    public void changeNums(View view) {
        top.setText(String.valueOf(Integer.parseInt((String) top.getText()) + (int) ((Math.random() * (50)) - 15) ));
        bottom.setText(String.valueOf(Integer.parseInt((String) bottom.getText()) + (int) ((Math.random() * (50)) - 15) ));
    }
}