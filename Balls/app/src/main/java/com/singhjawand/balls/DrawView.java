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

public class DrawView extends View{
    Random rand = new Random();
    Paint painter = new Paint();
    Circle circle = new Circle();
    Circle circle_two = new Circle();

    @SuppressLint("DrawAllocation")
    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        painter.setARGB(255, 100, 200, 255);
        circle.drawCircle(canvas);
        circle.offset();
        circle.checkBounds(canvas);

        circle_two.drawCircle(canvas);
        circle_two.offset();
        circle_two.checkBounds(canvas);

        invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            circle.changeMotion();
            circle_two.changeMotion();
        }
        return super.onTouchEvent(event);
    }

}

//    final MediaPlayer mp = MediaPlayer.create(getContext().getApplicationContext(), R.raw.stop);
//        mp.start();