package com.singhjawand.balls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class DrawView extends View {
    int count = 0;
    Paint painter = new Paint();
    Circles balls = new Circles(6);

    @SuppressLint("DrawAllocation")
    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        painter.setARGB(255, 100, 200, 255);
        balls.cycle(canvas, count);

        count++;
        invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            balls.changeColors();
            balls.detectTap((int) event.getX(), (int) event.getY(), getContext());
        }
        return super.onTouchEvent(event);
    }

}

//    final MediaPlayer mp = MediaPlayer.create(getContext().getApplicationContext(), R.raw.stop);
//        mp.start();

//            circle.changeMotion();
//            circle_two.changeMotion();
