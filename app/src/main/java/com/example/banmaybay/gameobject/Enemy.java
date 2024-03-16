package com.example.banmaybay.gameobject;

import static com.example.banmaybay.MainActivity.SCREEN_HEIGHT;
import static com.example.banmaybay.MainActivity.SCREEN_WIDTH;
import static com.example.banmaybay.gameobject.Aircraft.AIRCRAFT_SIZE;

import android.graphics.Canvas;

import com.example.banmaybay.graphics.Sprite;

import java.util.Objects;
import java.util.Random;

public class Enemy extends GameObject {
    public static final int ENEMY_SIZE = 150;
    public static final int ENEMY_MAX_SPEED = 5;
    private Sprite sprite;
    private String enemyMode = "arriving";
    private boolean movingLeft = true;
    private int changeDirectionTimes = 0;

    public Enemy(double positionX, double positionY, Sprite sprite) {
        super(positionX, positionY);
        this.sprite = sprite;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, (int) positionX, (int) positionY, ENEMY_SIZE);
    }
    public void update(Aircraft aircraft) {
        updatePosition();
        updateEnemyMode(aircraft);
    }

    private void updateEnemyMode(Aircraft aircraft) {
        if (Objects.equals(enemyMode, "arriving")) {
            if (positionY >= (double) (SCREEN_HEIGHT * 2) / 10) {
                enemyMode = "moving";
            }
        } if (Objects.equals(enemyMode, "moving")) {
            if (changeDirectionTimes == 3) {
                enemyMode = "detecting";
                changeDirectionTimes = 0;
            }
        } if (Objects.equals(enemyMode, "detecting")) {
            if (positionX >= aircraft.positionX - (double) AIRCRAFT_SIZE / 4 &&
                    positionX <= aircraft.positionX + (double) AIRCRAFT_SIZE / 4) {
                enemyMode = "attacking";
            }
        } if (Objects.equals(enemyMode, "attacking")) {
            if (positionY - (double) ENEMY_SIZE / 2 >= SCREEN_HEIGHT) {
                positionY = (double) (-SCREEN_HEIGHT * 2) / 10;
                positionX = new Random().nextInt((SCREEN_WIDTH * 6) / 10) + (double) (SCREEN_WIDTH * 2) / 10;
                enemyMode = "arriving";
            }
        }
    }

    private void updatePosition() {
        if (Objects.equals(enemyMode, "arriving")) {
            positionY += ENEMY_MAX_SPEED;
        } else if (Objects.equals(enemyMode, "moving")) {
            if (movingLeft) {
                positionX -= ENEMY_MAX_SPEED;
                if (positionX - (double) ENEMY_SIZE / 2 <= (double) (SCREEN_WIDTH) / 10) {
                    movingLeft = false;
                    changeDirectionTimes++;
                }
            } else {
                positionX += ENEMY_MAX_SPEED;
                if (positionX + (double) ENEMY_SIZE / 2 >= (double) (SCREEN_WIDTH * 9) / 10) {
                    movingLeft = true;
                    changeDirectionTimes++;
                }
            }
        } else if (Objects.equals(enemyMode, "detecting")) {
            if (movingLeft) {
                positionX -= ENEMY_MAX_SPEED;
            } else {
                positionX += ENEMY_MAX_SPEED;
            }
        } else if (Objects.equals(enemyMode, "attacking")) {
            positionY += 3 * ENEMY_MAX_SPEED;
        }
    }
}
