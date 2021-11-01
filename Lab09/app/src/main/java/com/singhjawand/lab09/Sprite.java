package com.singhjawand.lab09;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import java.util.Random;

public class Sprite {
    Drawable image;
    Random random = new Random();
    int dx, dy;
    int x, y;
    int w, h;

    public Sprite(int width, int height){
        dx = random.nextInt(10) - 5;
        dy = random.nextInt(10) - 5;

        w = 25;
        h = 50;

        x = random.nextInt(width - w) + 5;
        x = random.nextInt(height - h) + 5;

    }

    public void checkBounds(Canvas canvas){
        if (x > canvas.getWidth()) {
            dx *= -1;
            x = canvas.getWidth();
            x += dx;
        } else if (x < 0) {
            dx *= -1;
            x = 0;
            x += dx;
        }
        if (y > canvas.getHeight()) {
            dy *= -1;
            y = canvas.getHeight();
            y += dy;
        } else if (y < 0) {
            dy *= -1;
            y = 0;
            y += dy;
        }
    }

    public void animate(Canvas canvas){
        checkBounds(canvas);
        x += x;
        y += dy;

        // TO DO: DRAW IMAGE USING BITMAP
    }

}
