package com.singhjawand.lab08;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View {
    private final Paint painter = new Paint();
    int right = getRight();
    int[] pos_x = {100, 150, 225, 400, 600, 900};
    int[] pos_y = {10, 10, 10, 10, 10, 10};
    int[] radius = {20, 35, 50, 65, 80, 95};
    int[] dy_vals = {10, 11, 12, 13, 14, 15};
    int[][] color_values = {{255, 50, 100}, {255, 50, 200}, {255, 0, 100}, {0, 50, 100}, {0, 10, 100}, {50, 255, 50}};
    int acceleration = 1;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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
            if (y > getHeight()) {
                if (dy < 50 && dy > 0)
                    dy_vals[counter] = ((dy + acceleration) * -1);
                else if (dy > 0)
                    dy_vals[counter] = dy * -1;
                pos_y[counter] = getBottom();
            } else if (y < 0) {
                if (dy < 0)
                    dy_vals[counter] = dy * -1;
                pos_y[counter] = getTop();
            }
        }


        invalidate();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        acceleration += 1;
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