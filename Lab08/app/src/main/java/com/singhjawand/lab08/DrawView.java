package com.singhjawand.lab08;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class DrawView extends View {
    private final Paint painter = new Paint();
    int right = getRight();
    int[] pos_x = {150, 300, 450, 600, 750, 900};
    int[] pos_y = {20, 20, 20, 20, 20, 20};
    int[] radius = {60, 60, 60, 60, 60, 60};
    int[] dy_vals = {0, 0, 0, 0, 0, 0};
    //    int[] dy_vals = {10, 11, 12, 13, 14, 15};
    int[][] color_values = {{255, 50, 100}, {200, 50, 200}, {100, 0, 100}, {0, 50, 100}, {200, 10, 100}, {50, 255, 50}};
    int acceleration = 1;
    double gravity = 1;
    Random rand = new Random();

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        float bounce = (float) Math.abs(rand.nextFloat() + 0.05);

        for (int counter = 0; counter < 6; counter++) {
            // changes the color of the painter
            int[] colors = color_values[counter];
            painter.setARGB(250, colors[0], colors[1], colors[2]);
            int x = pos_x[counter];
            int y = pos_y[counter];
            int dy = dy_vals[counter];
            int rad = radius[counter];
            canvas.drawCircle(x, y, rad, painter);
            pos_y[counter] = y + dy;
            if (y > getHeight() + 60) {
                dy_vals[counter] = (int) (dy * 0.8 * -1);
                pos_y[counter] = getBottom();
            } else if (y < 0) {
                if (dy < 0)
                    dy_vals[counter] = dy * -1;
                pos_y[counter] = getTop();
            } else {
//                if (dy_vals[counter] < 1 && pos_y[counter] > 500)
//                    dy_vals[counter] = 0;
//                else
                dy_vals[counter] = (int) (dy + gravity);
            }
        }

//        if (dy_vals[0] > 5 && pos_y[0] > getBottom() - 75)
        invalidate();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        acceleration += 1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int counter = 0; counter < 6; counter++) {
                pos_y[counter] -= 25;
                dy_vals[counter] -= 25;
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            gravity++;
        }

        return true;
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