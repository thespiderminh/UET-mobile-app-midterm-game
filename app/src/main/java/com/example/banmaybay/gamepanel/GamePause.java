package com.example.banmaybay.gamepanel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.banmaybay.R;

public class GamePause {
    private static final float size = 200;
    private Context context;
    private Bitmap bitmap;
    private Rect rect;

    public GamePause(Context context) {
        this.context = context;
        this.bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.pause_button);
        this.rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, rect, new Rect((int) (canvas.getWidth() - size), 0, canvas.getWidth(), (int) size), null);
    }

    public int getSize() {
        return (int) size;
    }
}
