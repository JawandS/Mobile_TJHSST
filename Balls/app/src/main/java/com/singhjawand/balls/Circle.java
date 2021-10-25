package com.singhjawand.balls;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import java.util.Random;

public class Circle {
    public int dx, dy;
    public int x, y;
    public int radius;
    Random rand = new Random();

    public Circle() {
        this.dx = rand.nextInt(25) + 5;
        this.dy = rand.nextInt(25) + 5;

        this.radius = rand.nextInt(150) + 25;
        this.x = rand.nextInt(100) + 50;
        this.y = rand.nextInt(500) + 100;
    }

    public Circle(int x, int y, int rad) {
        this.dx = rand.nextInt(10) - 5;
        this.dy = rand.nextInt(10) - 5;

        this.radius = rad;
        this.x = x;
        this.y = y;
    }

    public void drawCircle(Canvas canvas, int r, int g, int b) {
        Paint painter = new Paint();
        painter.setARGB(255, r, g, b);
        canvas.drawCircle(x, y, radius, painter);
    }

    public void drawCircle(Canvas canvas){
        Paint painter = new Paint();
        painter.setARGB(255, rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        canvas.drawCircle(x, y, radius, painter);
    }


    @NonNull
    public String toString() {
        return "Circle(" + x + ", " + y + ", " + radius + ")";
    }


    public void set(int x, int y, int rad) {
        this.x = x;
        this.y = y;
        this.radius = rad;
    }

    public void setCenterX(int x) {
        this.x = x;
    }

    public void setCenterY(int y) {
        this.y = y;
    }


    public void offset(int dx, int dy) {
        x += dy;
        y += dy;
    }

    public void offset() {
        x += dx;
        y += dy;
    }

    public void checkBounds(Canvas canvas) {
        if (x > canvas.getWidth() || x < 0) {
            dx *= -1;
            x += dx;
        }
        if (y > canvas.getHeight() || y < 0) {
            dy *= -1;
            y += dy;
        }
    }

    public void changeMotion(){
        if (dx < 0)
            dx -= rand.nextInt(5);
        else
            dx += rand.nextInt(5);

        if (dy < 0)
            dy -= rand.nextInt(5);
        else
            dy += rand.nextInt(5);

        radius -= 1;
    }


}

