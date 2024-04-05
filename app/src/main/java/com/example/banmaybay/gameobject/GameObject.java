package com.example.banmaybay.gameobject;

import android.graphics.Canvas;

import com.example.banmaybay.graphics.SpriteSheet;

import java.util.List;

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
        if ((enemy.positionX + (double) Enemy.PLANE_SIZE /2 * 95/100) < (aircraft.positionX - (double) Aircraft.PLANE_SIZE /2 * 88/100)) {
            return false;
        }
        if ((enemy.positionX - (double) Enemy.PLANE_SIZE /2 * 95/100) > (aircraft.positionX + (double) Aircraft.PLANE_SIZE /2 * 88/100)) {
            return false;
        }
        if ((enemy.positionY + (double) Enemy.PLANE_SIZE /2) < (aircraft.positionY - (double) Aircraft.PLANE_SIZE /2 * 59/100)) {
            return false;
        }
        if ((enemy.positionY - (double) Enemy.PLANE_SIZE /2 * 69/100) > (aircraft.positionY + (double) Aircraft.PLANE_SIZE /2 * 56/100)) {
            return false;
        }
        return true;
    }

    public static int isColliding(Enemy enemy, List<Bullet> bulletList) {
        if (enemy.isDestroyed()) {
            return -1;
        }
        for (int i = 0; i < bulletList.size(); i++) {
            if ((enemy.positionX + (double) Enemy.PLANE_SIZE /2 * 90/100) < (bulletList.get(i).positionX - (double) Aircraft.PLANE_SIZE /2 * 16/100)) {
                continue;
            }
            if ((enemy.positionX - (double) Enemy.PLANE_SIZE /2 * 90/100) > (bulletList.get(i).positionX + (double) Aircraft.PLANE_SIZE /2 * 16/100)) {
                continue;
            }
            if ((enemy.positionY + (double) Enemy.PLANE_SIZE /2 * 90/100) < (bulletList.get(i).positionY - (double) Aircraft.PLANE_SIZE /2 * 28/100)) {
                continue;
            }
            if ((enemy.positionY - (double) Enemy.PLANE_SIZE /2 * 69/100) > (bulletList.get(i).positionY + (double) Aircraft.PLANE_SIZE /2 * 32/100)) {
                continue;
            }
            return i;
        }
        return -1;
    }

    public static int isColliding(EnemyBullet enemyBullet, Aircraft aircraft) {
        if ((enemyBullet.positionX + (double) EnemyBullet.BULLET_SIZE /2 * 16/100) < (aircraft.positionX - (double) Aircraft.PLANE_SIZE /2 * 88/100)) {
            return -1;
        }
        if ((enemyBullet.positionX - (double) EnemyBullet.BULLET_SIZE /2 * 16/100) > (aircraft.positionX + (double) Aircraft.PLANE_SIZE /2 * 88/100)) {
            return -1;
        }
        if ((enemyBullet.positionY + (double) EnemyBullet.BULLET_SIZE /2 * 32/100) < (aircraft.positionY - (double) Aircraft.PLANE_SIZE /2 * 59/100)) {
            return -1;
        }
        if ((enemyBullet.positionY - (double) EnemyBullet.BULLET_SIZE /2 * 28/100) > (aircraft.positionY + (double) Aircraft.PLANE_SIZE /2 * 56/100)) {
            return -1;
        }
        return 1;
    }

    public abstract void draw(Canvas canvas);
}
