package com.singhjawand.balls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Random;

public class Circle {
    public int dx, dy;
    public int x, y;
    public int radius;
    Random rand = new Random();
    public int r = rand.nextInt(255);
    public int g = rand.nextInt(255);
    public int b = rand.nextInt(255);

    public Circle() {
        this.dx = rand.nextInt(12) + 2;
        this.dy = rand.nextInt(12) + 2;

//        this.radius = rand.nextInt(150) + 25;
        this.radius = 60;
        this.x = rand.nextInt(300) + 50;
        this.y = rand.nextInt(1000) + 50;
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

    public void drawCircle(Canvas canvas) {
        Paint painter = new Paint();
        painter.setARGB(255, r, g, b);
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


    public void offset() {
        x += dx;
        y += dy;
    }

    public void checkBounds(Canvas canvas) {
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

    public boolean collision(Circle c) {
        boolean toRet = false;
        if (x > c.x - c.radius + 5 && x < c.x + c.radius - 5) {
            dx *= -1;
            x += dx;
            c.dx *= -1;
            c.x += c.dx;
            toRet = true;
        }
        if (y > c.y - c.radius + 5 && y < c.y + c.radius - 5) {
            dy *= -1;
            y += dy;
            c.dy *= -1;
            c.y += c.dy;
            toRet = true;
        }
        return toRet;
    }


}

class Circles {
    Random rand = new Random();
    ArrayList<Circle> circles = new ArrayList<>();
    int number = 0;
    int max;
    Drawable explosion;

    public Circles(int number) {
        for (int x = 0; x < number; x++)
            circles.add(new Circle());
        max = number;
    }

    @SuppressLint("SetTextI18n")
    public void cycle(Canvas canvas, int count) {
        ArrayList<Circle> toAdd = new ArrayList<>();
        for (Circle circle : circles) {
            circle.drawCircle(canvas);
            circle.offset();
            circle.checkBounds(canvas);
            if (count % 8 == 0)
                for (int i = 1; i < circles.size(); i++)
                    if (circle.collision(circles.get(i)))
                        toAdd.add(circle);
        }

        for (Circle add : toAdd)
            if (circles.size() < 7)
                circles.add(new Circle(add.x + rand.nextInt(15) - 7, add.y + rand.nextInt(15) - 7, add.radius));

        Paint painter = new Paint();
        painter.setTextSize(100);
        painter.setARGB(255, 255, 255, 255);
        canvas.drawText(number + "", (int) (canvas.getWidth() / 2.0), 200, painter);

    }

    public void changeColors() {
        for (Circle circle : circles) {
            circle.r = rand.nextInt(255);
            circle.g = rand.nextInt(255);
            circle.b = rand.nextInt(255);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void explode(Canvas canvas, Circle toEx) {
        Rect bounds = new Rect(0, 0, explosion.getMinimumWidth() / 5, explosion.getMinimumHeight() / 4);
        explosion.setBounds(bounds);
        explosion.draw(canvas);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("UseCompatLoadingForDrawables")
    public void detectTap(int posx, int posy, Context context, Canvas canvas) {
        ArrayList<Circle> toRem = new ArrayList<>();
        for (Circle c : circles) {
            if (posx > c.x - c.radius - 30 && posx < c.x + c.radius + 30 && posy > c.y - c.radius - 30 && posy < c.y + c.radius + 30) {
                //  random sounds, sound: Connor, sound1: oof, sound2: siren, sound3: error
                // excluded: R.raw.sound, , R.raw.sound2
                int[] sounds = {R.raw.sound1, R.raw.sound3};
                // windows error sound
                final MediaPlayer mp = MediaPlayer.create(context, sounds[rand.nextInt(sounds.length)]);
                mp.start();
                toRem.add(c);
            }
        }
        for (Circle rem : toRem) {
            explosion = context.getResources().getDrawable(R.drawable.explosion);
            explode(canvas, rem);
            number += 1;
            circles.remove(rem);
        }

        if(circles.isEmpty()){
            max++;
            for(int i = 0; i < max; i++)
                circles.add(new Circle());
            number /= 2;
        }

    }

}

