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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

public class DrawView extends View {
    int count = 0;
    Paint painter = new Paint();
    Circles balls;

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

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        TextView textView = ((ConstraintLayout)(this.getParent())).findViewById(R.id.counter);
        balls = new Circles(6);
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

