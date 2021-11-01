package com.singhjawand.lab09;

import android.graphics.Canvas;

import java.util.Random;

public class Sprite {

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

    public void animate(Canvas canvas, Paint painter){

    }

}
