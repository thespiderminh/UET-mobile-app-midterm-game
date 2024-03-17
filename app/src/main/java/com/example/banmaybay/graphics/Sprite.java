package com.example.banmaybay.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import static com.example.banmaybay.graphics.SpriteSheet.*;

public class Sprite {
    private SpriteSheet spriteSheet;
    private final Rect rect;
    private int x;
    private int y;
    public Sprite(SpriteSheet spriteSheet, int x, int y) {
        this.spriteSheet = spriteSheet;
        this.x = x;
        this.y = y;
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

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
