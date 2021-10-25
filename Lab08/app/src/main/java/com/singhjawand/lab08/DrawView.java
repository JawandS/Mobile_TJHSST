package com.singhjawand.lab08;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Random;

public class DrawView extends View {
    private final Paint painter = new Paint();
    int right = getRight();
    Circle[] balls = new Circle[6];
    int generate = 0;

    int[] pos_x = {150, 300, 450, 600, 750, 900};
    int[] pos_y = {20, 20, 20, 20, 20, 20};
    int[] radius = {60, 60, 60, 60, 60, 60};
    int[] dy_vals = {0, 0, 0, 0, 0, 0};
    int[] dy_values = {10, 11, 12, 13, 14, 15};
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

        if (generate == 0) {
            for (int i = 0; i < balls.length; i++) {
                balls[i] = new Circle(pos_x[i], pos_y[i], radius[i]);
                
            }
            painter.setARGB(255, 212, 170, 30);
            generate = 1;
        } else if (generate >= 2) {
            for (int i = 0; i < pos_x.length; i++) {
                radius[i] = generate * 2;
            }
        }

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
                dy_vals[counter] = (int) (dy * 0.9 * -1);
                // change to 0.8 to have ball less distance every time
                pos_y[counter] = getBottom();
            } else if (y < 0) {
                if (dy < 0)
                    dy_vals[counter] = dy * -1;
                pos_y[counter] = getTop();
            } else {
                dy_vals[counter] = (int) (dy + gravity);
            }
        }

        invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            for (int counter = 0; counter < 6; counter++) {
                pos_y[counter] -= rand.nextInt(50) - 10;
                dy_vals[counter] += rand.nextInt(30) - 5;

                if (contains(x, y, pos_x[counter], pos_y[counter], radius[counter])) {
                    pos_y[counter] -= rand.nextInt(75) - 10;
                    dy_vals[counter] += rand.nextInt(30) - 5;
                    radius[counter] += 15;
                    color_values[counter][0] = 255;
                    color_values[counter][1] = 255;
                    color_values[counter][2] = 255;
                }
            }
        }

        return true;
    }

    public boolean contains(int x, int y, int pos_x, int pos_y, int radius) {
        radius += 20;
        int left = pos_x - radius;
        int right = pos_x + radius;
        int bottom = pos_y + radius;
        int top = pos_y - radius;
        return x >= left && x < right && y >= top && y < bottom;
    }

}

class Circle {
    public double left, top, right, bottom;
    public int centerX, centerY;

    public Circle() {
        left = top = right = bottom = 0;
    }

    public Circle(double left, double top, double right, double bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public Circle(int x, int y, int rad) {
        left = x - rad;
        right = x + rad;
        bottom = y + rad;
        top = y - rad;
    }

    public double centerX() {
        return (left + right) * 0.5;
    }

    public double centerY() {
        return (top + bottom) * 0.5;
    }

    public double getWidth() {
        return right - left;
    }

    public double getHeight() {
        return bottom - top;
    }

    @NonNull
    public String toString() {
        return "RectF(" + left + ", " + top + ", " + right + ", " + bottom + ")";
    }

    public void set(float left, float top, float right, float bottom) {

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void setCenterX(int x) {
        centerX = x;
    }

    public void setCenterY(int y) {
        centerY = y;
    }

    public void set(Circle src) {
        this.left = src.left;
        this.top = src.top;
        this.right = src.right;
        this.bottom = src.bottom;
    }

    public void setEmpty() {
        left = 0;
        top = 0;
        right = 0;
        bottom = 0;
    }

    public void offset(double dx, double dy) {

        left += dx;
        top += dy;
        right += dx;
        bottom += dy;
    }

    public void offsetTo(double newLeft, double newTop) {

        right += newLeft - left;
        bottom += newTop - top;
        left = newLeft;
        top = newTop;
    }

    public boolean isEmpty() {
        return left >= right || top >= bottom;
    }

    public boolean intersects(float left, float top, float right, float bottom) {
        if (isEmpty())
            return false;
        return this.right > left && this.left < right && this.top < bottom && this.bottom > top;

    }

    public boolean intersects(Circle r) {
        if (r == null || isEmpty())
            return false;
        return this.right > r.left && this.left < r.right && this.top < r.bottom && this.bottom > r.top;
    }

    public boolean contains(double x, double y) {

        return !isEmpty() && x >= left && x < right && y >= top && y < bottom;
    }

    public boolean contains(double left, double top, double right, double bottom) {

        return !isEmpty() && this.left <= left && this.top <= top && this.right >= right && this.bottom >= bottom;
    }

    public boolean contains(Circle r) {
        return !isEmpty() && left <= r.left && top <= r.top && right >= r.right && bottom >= r.bottom;
    }


}
