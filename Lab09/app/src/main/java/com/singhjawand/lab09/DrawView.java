package com.singhjawand.lab09;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View {
    Sprite sprite = new Sprite();
    Paint paint = new Paint();

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.GRAY);
        //paint background gray
        canvas.drawRect(getLeft(), 0, getRight(), getBottom(), paint);
        paint.setColor(Color.RED);

        //sprite updates itself
        sprite.update();
        //sprite draws itself
        sprite.draw(canvas);
        //redraws screen, invokes onDraw()
        invalidate();

    }
}