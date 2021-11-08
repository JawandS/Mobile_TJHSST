package com.singhjawand.lab09;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DrawView extends SurfaceView {
    ArrayList<Sprite> sprites = new ArrayList<>();
    Paint paint = new Paint();
    final int NUMBER = 3;

    // ANIMATION VARIABLES
    SurfaceHolder surface;
    ArrayList<Explosion> explosions = new ArrayList<>();
    Bitmap explosionBMP = BitmapFactory.decodeResource(getResources(), R.drawable.explosion);
    Canvas canvas;
    boolean isRunning = true;
    int frames = 0;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (sprites.isEmpty()) {
            for (int i = 0; i < NUMBER; i++) {
                sprites.add(generateSprite());
                sprites.get(i).setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bluejeans));
            }

            for (Explosion e : explosions) {//draw each explosion
                e.draw(canvas);
            }
        }

        paint.setColor(Color.BLACK);
        //paint background gray
        canvas.drawRect(getLeft(), 0, getRight(), getBottom(), paint);
        paint.setColor(Color.RED);

        for (Sprite sprite : sprites) {
            // sprite updates itself
            sprite.update();
            // sprite checks the boundaries of the screen
            sprite.checkBounds(getWidth(), getHeight());
            // sprite draws itself
            sprite.draw(canvas);
            for (Sprite collision : sprites)
                if (collision != sprite && RectF.intersects(sprite, collision)) {
                    collision.reverse();
                    sprite.reverse();
                }
            // UPDATE EXPLOSIONS
            for (int i = explosions.size() - 1; i >= 0; i--) {
                explosions.get(i).update();
            }
        }
        //redraws screen, invokes onDraw()
        invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < sprites.size(); i++)
                if (sprites.get(i).contains(event.getX(), event.getY())) {
                    // ADD EXPLOSION
                    new Explosion(sprites.get(i), explosions, explosionBMP);

                    sprites.set(i, generateSprite());
                    sprites.get(i).setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bluejeans));
                }
        }
        return true;
    }

    private Sprite generateSprite() {
        float x = (float) (Math.random() * (getWidth() - .1 * getWidth()));
        float y = (float) (Math.random() * (getHeight() - .1 * getHeight()));
        int dX = (int) (Math.random() * 11 - 5);
        int dY = (int) (Math.random() * 11 - 5);
        return new Sprite(x, y, x + .1f * getWidth(), y + .1f * getWidth(), dX, dY, Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
    }

}