package com.example.banmaybay.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import static com.example.banmaybay.graphics.SpriteSheet.*;

public class Sprite {
    private SpriteSheet spriteSheet;
    private final Rect rect;
    public Sprite(SpriteSheet spriteSheet, int x, int y) {
        this.spriteSheet = spriteSheet;
        this.rect = new Rect(x*spriteSheetWidth/WIDTH_NUM,
                y*spriteSheetHeight/HEIGHT_NUM,
                (x+1)*spriteSheetWidth/WIDTH_NUM,
                (y+1)*spriteSheetHeight/HEIGHT_NUM
        );
    }

    public void draw(Canvas canvas, int x, int y, int size) {
        canvas.drawBitmap(
                spriteSheet.getBitmap(), rect, new Rect(x - size / 2,
                        y - size / 2,
                        x + size / 2,
                        y + size / 2), null
        );
    }
}
