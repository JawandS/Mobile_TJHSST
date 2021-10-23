package com.singhjawand.balls;

import androidx.annotation.NonNull;

public class Circle {
    public int left, top, right, bottom;
    public int centerX, centerY;

    public Circle() {
        left = top = right = bottom = 0;
    }

    public Circle(Circle circle) {
        if (circle == null) {
            left = top = right = bottom = 0;
        } else {
            left = circle.left;
            top = circle.top;
            right = circle.right;
            bottom = circle.bottom;
        }
    }

    public Circle(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public Circle(int x, int y, int rad) {
        this.left = x - rad;
        this.right = x + rad;
        this.bottom = y + rad;
        this.top = y - rad;
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

    public void set(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void set(int x, int y, int rad) {
        this.left = x - rad;
        this.right = x + rad;
        this.bottom = y + rad;
        this.top = y - rad;
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

    public void offsetTo(int newLeft, int newTop) {

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

