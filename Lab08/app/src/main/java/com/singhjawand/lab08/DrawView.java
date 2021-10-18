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
    private final Paint painter = new Paint();
    private int y = 0, dy = 10;
    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        // changes the color of the painter
        painter.setARGB(250, 255,255,100);
        for(float x = 25; x < getWidth() - 75; x += 10) {
            canvas.drawCircle(x, -1 * (y + x), 75F, painter);
        }

        for(float x = getWidth() - 75; x > 25; x -= 10) {
            canvas.drawCircle(x, y - x, 75F, painter);
        }

        y += dy;
        if (y < 0) dy *= -1;
        if (y > getHeight()) dy *= -1;
        invalidate();
    }



    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
}

//        canvas.drawRect(x-10, y-175, x+10, y-100, new Paint());
//        canvas.drawArc(y-50, y+50, x-10, 10, 20, false, new Paint());
//        canvas.drawCircle(500, 100, 100F, new Paint());