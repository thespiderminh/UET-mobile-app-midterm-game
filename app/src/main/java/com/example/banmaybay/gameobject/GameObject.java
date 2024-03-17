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

    public static boolean isColliding(Enemy enemy, Aircraft aircraft) {
        if ((enemy.positionX + (double) Enemy.ENEMY_SIZE /2 * 95/100) < (aircraft.positionX - (double) Aircraft.AIRCRAFT_SIZE /2 * 88/100)) {
            return false;
        }
        if ((enemy.positionX - (double) Enemy.ENEMY_SIZE /2 * 95/100) > (aircraft.positionX + (double) Aircraft.AIRCRAFT_SIZE /2 * 88/100)) {
            return false;
        }
        if ((enemy.positionY + (double) Enemy.ENEMY_SIZE /2) < (aircraft.positionY - (double) Aircraft.AIRCRAFT_SIZE /2 * 59/100)) {
            return false;
        }
        if ((enemy.positionY - (double) Enemy.ENEMY_SIZE /2 * 69/100) > (aircraft.positionY + (double) Aircraft.AIRCRAFT_SIZE /2 * 56/100)) {
            return false;
        }
        return true;
    }

    public abstract void draw(Canvas canvas);
}
