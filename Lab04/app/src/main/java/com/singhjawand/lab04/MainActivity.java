package com.singhjawand.lab04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    int[] layouts = {R.layout.layout, R.layout.layout2, R.layout.layout3, R.layout.layout4, R.layout.layout5, R.layout.layout6, R.layout.layout7};
    //int time = 0;
    Timer timer = new Timer();
    int duration = 0;
    TimerTask tt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tt = new TimerTask() {
            @Override

            public void run() {
                duration++;
                System.out.println("DURATIONJS123 " + duration);

                execute();
            }
        };
    }

public void execute(){
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            try {
                if (duration == 18) layout7();
                else if (duration == 15) layout6();
                else if (duration == 12) layout5();
                else if (duration == 9) layout4();
                else if (duration == 6) layout3();
                else if (duration == 3) layout2();
                else if (duration < 3) layout1();
                else if (duration == 21) duration = 0;
            }catch (Exception e){
                System.out.println("DURATION " + e);
            }
        }
    });
}

    @Override
    protected void onResume() {
        super.onResume();

        layout1();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        layout7();
    }

    @Override
    protected void onPause() {
        super.onPause();

        layout3();
    }

    public void layout1(View view) {
        setContentView(R.layout.layout);

    }

    public void layout2(View view) {
        // setContentView(R.layout.layout2);
        duration = 0;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(tt, 0, 1000);
    }

    public void layout3(View view) {
        setContentView(R.layout.layout3);
    }

    public void layout4(View view) {
        setContentView(R.layout.layout4);
    }

    public void layout5(View view) {
        setContentView(R.layout.layout5);
    }

    public void layout6(View view) {
        setContentView(R.layout.layout6);
    }

    public void layout7(View view) {
        setContentView(R.layout.layout7);

    }

    public void layout1() {
        setContentView(R.layout.layout);
    }

    public void layout2() {
        setContentView(R.layout.layout2);
    }

    public void layout3() {
        setContentView(R.layout.layout3);
    }

    public void layout4() {
        setContentView(R.layout.layout4);
    }

    public void layout5() {
        setContentView(R.layout.layout5);
    }

    public void layout6() {
        setContentView(R.layout.layout6);
    }

    public void layout7() {
        setContentView(R.layout.layout7);
    }
//    public void timing(){
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        duration++;
//                        if (duration >= 5) layout1();
//                        if (duration >= 10) layout2();
//                        if (duration >= 15) layout3();
//                        if (duration >= 20) layout4();
//                    }
//                });
//            }
//        });
//    }

}