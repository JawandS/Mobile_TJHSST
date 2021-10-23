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

public class DrawView {
    private final Paint painter = new Paint();

    @SuppressLint("DrawAllocation")
    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(10, 10, 10, painter);


    }

    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

}

//    final MediaPlayer mp = MediaPlayer.create(getContext().getApplicationContext(), R.raw.stop);
//        mp.start();