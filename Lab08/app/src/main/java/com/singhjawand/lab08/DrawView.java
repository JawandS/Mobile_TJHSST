package com.singhjawand.lab08;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View {
    private Paint painter = new Paint();
    private int y = 0, dy = 5;
    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        // changes the color of the painter
        painter.setColor(Color.CYAN);
        canvas.drawCircle(100, y, 100F, painter);
        canvas.drawCircle(500, 100, 100F, new Paint());
        y += dy;
        y %= getHeight() - 100;
        invalidate();
    }


    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
}
