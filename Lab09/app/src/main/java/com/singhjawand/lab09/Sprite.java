package com.singhjawand.lab09;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import java.util.Random;

class Sprite extends RectF {
    private int dX, dY, color;
    private int canvasWidth, canvasHeight;
//    Random rand = new Random();

    public Sprite(Canvas canvas) {
        this(1, 2, Color.RED);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
//        this(1, 2, Color.argb(255, rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
    }

    public Sprite(int dX, int dY, int color) {
        this(1, 1, 11, 11, dX, dY, color);
    }

    public Sprite(float left, float top, float right, float bottom) {
        this(left, top, right, bottom, 1, 2, Color.RED);
    }

    public Sprite(float left, float top, float right, float bottom, int dX, int dY, int color) {
        super(left, top, right, bottom);
        this.dX = dX;
        this.dY = dY;
        this.color = color;
    }

    public void update() {
        offset(dX, dY);//moves dX to the right and dY downwards
        checkBounds();
    }

    public void checkBounds() {
        int width = (int) width();
        int height = (int) height();

        if (right > canvasWidth) {
            dX *= -1;
            right = canvasWidth + dX;
            left = right - width;
        } else if (left < 0) {
            dX *= -1;
            left = 0;
            right = left + width;
        }
        // fix to check bounds
        if (bottom > canvasHeight) {
            dy *= -1;
            y = canvas.getHeight();
            y += dy;
        } else if (y < 0) {
            dy *= -1;
            y = 0;
            y += dy;
        }
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);//sets its color
        canvas.drawCircle(centerX(), centerY(), width() / 2, paint);//draws circle
    }

    public int getdX() {
        return dX;
    }

    public void setdX(int dX) {
        this.dX = dX;
    }

    public int getdY() {
        return dY;
    }

    public void setdY(int dY) {
        this.dY = dY;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}