package com.singhjawand.balls;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    View drawView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        textView=findViewById(R.id.counter);
        // DrawView
        drawView = findViewById(R.id.drawView);
//        drawView.textView = textView;
    }
}