package com.example.banmaybay.gameobject;

import android.graphics.Canvas;

import com.example.banmaybay.graphics.SpriteSheet;

/*
 * GameObject is an abstract class which is the foundation of all
 * world object in the game
 */
public abstract class GameObject {
    protected double positionX;
    protected double positionY;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public abstract void draw(Canvas canvas);
}
