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
    int right = getRight();
    int[] pos_x = {100, 300, 500, 600, 750, 900, right - 150};
    int[] pos_y = {10, 10, 10, 10, 10, 10, 10};
    int[] radius = {80, 30, 90, 45, 25, 120, 100};
    int[] dy_vals = {15, 4, 5, 3, 2, 25, 20};
//    Color[] color_list = {new Color(255, 50, 100)}

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // changes the color of the painter
        painter.setARGB(250, 255, 50, 100);

        for (int counter = 0; counter < 6; counter++) {
            int x = pos_x[counter];
            int y = pos_y[counter];
            int dy = dy_vals[counter];
            int rad = radius[counter];
            canvas.drawCircle(x, y, rad, painter);
            pos_y[counter] = y + dy;
            if (y < 0 || y > getHeight()) {
                dy_vals[counter] = dy * -1;
                pos_y[counter] = y + dy_vals[counter];
            }
        }


        invalidate();
    }

}

//        canvas.drawRect(x-10, y-175, x+10, y-100, new Paint());
//        canvas.drawArc(y-50, y+50, x-10, 10, 20, false, new Paint());
//        canvas.drawCircle(500, 100, 100F, new Paint());

//        painter.setARGB(250, 255,255,100);
//                for(float x = 25; x < getWidth() - 75; x += 10) {
//        canvas.drawCircle(x, -1 * (y + x), 75F, painter);
//        }
//
//        for(float x = getWidth() - 75; x > 25; x -= 10) {
//        canvas.drawCircle(x, y - x, 75F, painter);
//        }

//    public int getDy() {
//        return dy;
//    }
//
//    public void setDy(int dy) {
//        this.dy = dy;
//    }