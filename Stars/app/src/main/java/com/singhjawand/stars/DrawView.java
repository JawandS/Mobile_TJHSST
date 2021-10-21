package com.singhjawand.stars;

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

public class DrawView extends View {
    private final Paint painter = new Paint();
    int[] pos_x = new int[50];
    int[] pos_y = new int[50];
    int[] radius = new int[50];
    int dy = -5;
    int[][] color_values = new int[50][3];
    Random rand = new Random();
    int generate = 0;
    int counter = 0;
    int animate = 1;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (generate == 0) {
            for (int i = 0; i < pos_x.length; i++) {
                pos_x[i] = rand.nextInt(getWidth() - 25) + 25;
                pos_y[i] = rand.nextInt(getHeight() - 25) + 25;
                radius[i] = rand.nextInt(5) + 2;
                color_values[i][0] = rand.nextInt(180) + 75;
                color_values[i][1] = rand.nextInt(75);
                color_values[i][2] = rand.nextInt(75);
//                color_values[i][0] = 100;
//                color_values[i][1] = 50;
//                color_values[i][2] = 255;
                // changes the color of the painter to white
                painter.setARGB(255, 255, 255, 255);
            }
            generate = 1;
        } else if (generate >= 2) {
            for (int i = 0; i < pos_x.length; i++) {
                radius[i] = generate * 2;
            }
        }

        for (int counter = 0; counter < pos_x.length; counter++) {

            int x = pos_x[counter];
            int y = pos_y[counter];
            int rad = radius[counter];

            if (generate >= 2)
                // changes the color of the painter to random color
                painter.setARGB(255, color_values[counter][0], color_values[counter][1], color_values[counter][2]);
            canvas.drawCircle(x, y, rad, painter);

            pos_y[counter] = y + dy;
            if (y < 20) {
                pos_y[counter] = getHeight() - 10;
            }
        }

        counter++;
        if (counter % 100 == 0)
            dy--;
        if (counter >= 400) {
            if (counter % 100 == 0)
                generate -= 5;
            else if (counter % 5 == 0)
                generate++;
        }

        if(animate > 0)
            invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            animate *= -1;
            final MediaPlayer mp = MediaPlayer.create(getContext().getApplicationContext(), R.raw.stop);
            mp.start();
        }
        if(animate == 1) {
            invalidate();
        }
        return super.onTouchEvent(event);
    }
}
