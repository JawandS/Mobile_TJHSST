package com.singhjawand.lab09;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View {
    Sprite character;

    @SuppressLint("DrawAllocation")
    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        character.animate(canvas);

        invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        character = new Sprite(getWidth(), getHeight());
    }

}
