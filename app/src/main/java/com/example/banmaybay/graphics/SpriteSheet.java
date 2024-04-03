package com.example.banmaybay.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.banmaybay.R;

public class SpriteSheet {
    private Bitmap bitmap;
    public static int WIDTH_NUM = 10;
    public static int HEIGHT_NUM = 10;
    public static int spriteSheetWidth;
    public static int spriteSheetHeight;

    public SpriteSheet(Context context, String color) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        Log.d("SpriteSheet.java", "constructor\n" + color);
        switch (color) {
            case "Red":
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet_red);
                break;
            case "Blue":
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet_blue);
                break;
            case "Green":
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet_green);
                break;
            case "Yellow":
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet_yellow);
                break;
            case "Orange":
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet_orange);
                break;
            case "White":
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet_white);
                break;
        }

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
