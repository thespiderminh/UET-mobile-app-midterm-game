package com.example.banmaybay.gameobject;

import static com.example.banmaybay.MainActivity.SCREEN_HEIGHT;

import android.graphics.Canvas;

import com.example.banmaybay.GameLoop;
import com.example.banmaybay.graphics.Sprite;
import com.example.banmaybay.graphics.SpriteSheet;

public class Bullet extends GameObject{
    public static final int BULLET_SIZE = 200;
    private static final double SPEED_PIXELS_PER_SECOND = 600.0;
    public static final double BULLET_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private Sprite sprite;
    public Bullet(double positionX, double positionY, Sprite sprite) {
        super(positionX, positionY);
        this.sprite = sprite;
    }
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, (int) positionX, (int) positionY, BULLET_SIZE);
    }

    public void update() {
        positionY -= BULLET_SPEED;
    }

    public boolean outOfScreen() {
        return positionY <= -SCREEN_HEIGHT / 10.0;
    }
}
