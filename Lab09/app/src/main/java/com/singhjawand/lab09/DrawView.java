package com.singhjawand.lab09;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DrawView extends View {
    ArrayList<Sprite> sprites = new ArrayList<>();
    Paint paint = new Paint();
    final int NUMBER = 3;
    int clicks = 0;

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
                sprites.get(i).setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bluejeans));
            }
        }

        paint.setColor(Color.argb(255, clicks * 3, clicks * 3, clicks * 3));
        //paint background black
        canvas.drawRect(getLeft(), 0, getRight(), getBottom(), paint);


        for (Sprite sprite : sprites) {
            // sprite updates itself
            sprite.update(canvas);
            // sprite checks the boundaries of the screen
            sprite.checkBounds(getWidth(), getHeight());
            // sprite draws itself
            sprite.draw(canvas);
            for (Sprite collision : sprites)
                if (collision != sprite && RectF.intersects(sprite, collision)) {
                    collision.reverse();
                    sprite.reverse();
                }
        }

        Paint painter = new Paint();
        painter.setTextSize(100);
        painter.setARGB(255, 255, 255, 255);
//        int percent_acc = (int) (((hits / total_clicks) / 100.0) * 10000);
        canvas.drawText(clicks + "", (int) (getWidth() / 2.0) - 20, 200, painter);


        //redraws screen, invokes onDraw()
        invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < sprites.size(); i++)
                if (sprites.get(i).contains(event.getX(), event.getY())) {
                    sprites.set(i, generateSprite());
                    sprites.get(i).setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bluejeans));
                    clicks++;

                    final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.connor);
                    mp.start();
                }
        }
        return true;
    }

    public void reset(){
        for(int i = 0; i < sprites.size(); i++){
            sprites.set(i, generateSprite());
            sprites.get(i).setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bluejeans));
        }
    }

    private Sprite generateSprite() {
        float x = (float) (Math.random() * (getWidth() - .1 * getWidth()));
        float y = (float) (Math.random() * (getHeight() - .1 * getHeight()));
        int dX = (int) (Math.random() * 3 + (clicks / 3)) + 1;
        int dY = (int) (Math.random() * 3 + (clicks / 3)) + 1;
//        double size =  0.2 - (clicks / 100.0); makes the size smaller as more clicks
        float size = 0.15f;
        return new Sprite(x, y, (float) (x + size * getWidth()), (float) (y + size * getWidth()), dX, dY, Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
    }

}