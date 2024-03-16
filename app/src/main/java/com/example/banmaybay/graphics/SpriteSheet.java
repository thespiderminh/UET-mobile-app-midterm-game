package com.example.banmaybay.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.banmaybay.R;

public class SpriteSheet {
    private Bitmap bitmap;
    public static int WIDTH_NUM = 5;
    public static int HEIGHT_NUM = 5;
    public static int spriteSheetWidth;
    public static int spriteSheetHeight;

    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet);
        spriteSheetWidth = bitmap.getWidth();
        spriteSheetHeight = bitmap.getHeight();
    }

    public Sprite getSprite(int x, int y) {
        return new Sprite(this, x, y);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
