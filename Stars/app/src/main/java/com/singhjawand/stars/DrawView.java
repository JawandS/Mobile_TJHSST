package com.singhjawand.stars;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class DrawView extends View {
    private final Paint painter = new Paint();
    int[] pos_x = new int[50];
    int[] pos_y = new int[50];
    int[] radius = new int[50];
    int dy = -5;
    int[] color_values = {255, 255, 255};
    Random rand = new Random();
    int generate = 0;
    int counter = 0;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (generate == 0){
            for(int i = 0; i < pos_x.length; i++){
                pos_x[i] = rand.nextInt(getWidth() - 25) + 25;
                pos_y[i] = rand.nextInt(getHeight() - 25) + 25;
                radius[i] = rand.nextInt(5) + 2;
                // changes the color of the painter to white
                painter.setARGB(255, 255, 255, 255);
            }
            generate = 1;
        }else if(generate == 2){
            for(int i = 0; i < pos_x.length; i++){
                radius[i]++;
                // changes the color of the painter to red
                painter.setARGB(235, 255, 10, 10);
            }
        }

        for (int counter = 0; counter < pos_x.length; counter++) {

            int x = pos_x[counter];
            int y = pos_y[counter];
            int rad = radius[counter];

            canvas.drawCircle(x, y, rad, painter);

            pos_y[counter] = y + dy;
            if (y < 20) {
                pos_y[counter] = getHeight() - 10;
            }
        }

        counter++;
        if (counter % 100 == 0)
            dy--;
        if (counter == 800)
            generate = 2;

        invalidate();
    }


}
