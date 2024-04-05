package com.example.banmaybay.gameobject;

import android.graphics.Canvas;

import com.example.banmaybay.Game;
import com.example.banmaybay.gamepanel.HealthBar;
import com.example.banmaybay.graphics.Sprite;
import com.example.banmaybay.graphics.SpriteSheet;

public abstract class Plane extends GameObject {
    public static int MAX_HEALTH_POINT;
    public static int PLANE_SIZE;
    protected HealthBar healthBar;
    protected int healthPoint;
    protected Sprite sprite;
    protected SpriteSheet spriteSheet;
    public Plane(double positionX, double positionY) {
        super(positionX, positionY);
    }

    @Override
    public abstract void draw(Canvas canvas);
    public void lossHealth() {
        healthPoint--;
        healthPoint = Math.max(healthPoint, 0);
    }
}
