package com.singhjawand.lab05;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public int onCreate = 0;
    public int onStart = 0;
    public int onResume = 0;
    public int onPause = 0;
    public int onStop = 0;
    public int onRestart = 0;
    public int onDestroy = 0;

    TextView localTextOnCreate;
    TextView localTextOnStart;
    TextView localTextOnResume;
    TextView localTextOnPause;
    TextView localTextOnStop;
    TextView localTextOnRestart;
    TextView localTextOnDestroy;

    TextView globalOnCreate;
    TextView globalOnStart;
    TextView globalOnResume;
    TextView globalOnPause;
    TextView globalOnStop;
    TextView globalOnRestart;
    TextView globalOnDestroy;

    public void setInitialValues(){
        onCreate = prefs.getInt("onCreate", 0);
        onStart = prefs.getInt("onStart", 0);
        onResume = prefs.getInt("onResume", 0);
        onPause = prefs.getInt("onPause", 0);
        onStop = prefs.getInt("onStop", 0);
        onRestart = prefs.getInt("onRestart", 0);
        onDestroy = prefs.getInt("onDestroy", 0);
    }

    @SuppressLint("SetTextI18n")
    public void saveValues(){
        editor.putInt("onCreate", onCreate);
        editor.putInt("onStart", onStart);
        editor.putInt("onResume", onResume);
        editor.putInt("onPause", onPause);
        editor.putInt("onStop", onStop);
        editor.putInt("onRestart", onRestart);
        editor.putInt("onDestroy", onDestroy);
        editor.apply();

        globalOnCreate.setText("onCreate: " + onCreate);
        globalOnStart.setText("onStart: " + onStart);
        globalOnResume.setText("onResume: " + onResume);
        globalOnPause.setText("onPause: " + onPause);
        globalOnStop.setText("onStop: " + onStop);
        globalOnRestart.setText("onRestart: " + onRestart);
        globalOnDestroy.setText("onDestroy: " + onDestroy);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("values", Context.MODE_PRIVATE);
        editor = prefs.edit();

        globalOnCreate = findViewById(R.id.gonCreate);
        globalOnStart = findViewById(R.id.gonStart);
        globalOnResume = findViewById(R.id.gonResume);
        globalOnPause = findViewById(R.id.gonPause);
        globalOnStop = findViewById(R.id.gonStop);
        globalOnRestart = findViewById(R.id.gonRestart);
        globalOnDestroy = findViewById(R.id.gonDestroy);

        localTextOnCreate = findViewById(R.id.lonCreate);
        localTextOnStart = findViewById(R.id.lonStart);
        localTextOnResume = findViewById(R.id.lonResume);
        localTextOnPause = findViewById(R.id.lonPause);
        localTextOnStop = findViewById(R.id.lonStop);
        localTextOnRestart = findViewById(R.id.lonRestart);
        localTextOnDestroy = findViewById(R.id.lonDestroy);

        setInitialValues();
        onCreate++;
        saveValues();

        System.out.println("Values of onCreate: " + onCreate);
        System.out.println("Values of onStart: " + onStart);
        System.out.println("Values of onResume: " + onResume);
        System.out.println("Values of onPause: " + onPause);
        System.out.println("Values of onStop: " + onStop);
        System.out.println("Values of onRestart: " + onRestart);
        System.out.println("Values of onDestroy: " + onDestroy);

        String str = (String) localTextOnCreate.getText();
        int temp = Integer.parseInt(String.valueOf(str.charAt(str.length() - 1))) + 1;
        localTextOnCreate.setText("onCreate: " + temp);
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();

        onStart += 1;
        saveValues();

        String str = (String) localTextOnStart.getText();
        int temp = Integer.parseInt(String.valueOf(str.charAt(str.length() - 1))) + 1;
        localTextOnStart.setText("onStart: " + temp);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        onResume += 1;
        saveValues();

        String str = (String) localTextOnResume.getText();
        int temp = Integer.parseInt(String.valueOf(str.charAt(str.length() - 1))) + 1;
        localTextOnResume.setText("onResume: " + temp);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPause() {
        super.onPause();

        onPause += 1;
        saveValues();

        String str = (String) localTextOnPause.getText();
        int temp = Integer.parseInt(String.valueOf(str.charAt(str.length() - 1))) + 1;
        localTextOnPause.setText("onPause: " + temp);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStop() {
        super.onStop();

        onStop += 1;
        saveValues();

        String str = (String) localTextOnStop.getText();
        int temp = Integer.parseInt(String.valueOf(str.charAt(str.length() - 1))) + 1;
        localTextOnStart.setText("onStop: " + temp);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onRestart() {
        super.onRestart();

        onRestart += 1;
        saveValues();

        String str = (String) localTextOnRestart.getText();
        int temp = Integer.parseInt(String.valueOf(str.charAt(str.length() - 1))) + 1;
        localTextOnRestart.setText("onRestart: " + temp);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onDestroy() {
        super.onDestroy();

        onDestroy += 1;
        saveValues();

        String str = (String) localTextOnDestroy.getText();
        int temp = Integer.parseInt(String.valueOf(str.charAt(str.length() - 1))) + 1;
        localTextOnStart.setText("onDestroy: " + temp);
    }

    public void resetData(View view) {
        onCreate = 0;
        onStart = 0;
        onResume = 0;
        onPause = 0;
        onStop = 0;
        onRestart = 0;
        onDestroy = 0;

        editor.putInt("onCreate", 0);
        editor.putInt("onStart", 0);
        editor.putInt("onResume", 0);
        editor.putInt("onPause", 0);
        editor.putInt("onStop", 0);
        editor.putInt("onRestart", 0);
        editor.putInt("onDestroy", 0);

        saveValues();
    }
}