package com.example.banmaybay.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.banmaybay.R;

public class BackGround {
    private Context context;
    private Rect rect;
    private Bitmap bitmap;
    public BackGround(Context context) {
        this.context = context;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        this.rect = new Rect(0, 0, 600, 1024);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, rect, new Rect(0, 0, canvas.getWidth(), canvas.getHeight()), null);
    }
}
