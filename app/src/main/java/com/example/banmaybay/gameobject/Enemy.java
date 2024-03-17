package com.example.banmaybay.gameobject;

import static com.example.banmaybay.MainActivity.SCREEN_HEIGHT;
import static com.example.banmaybay.MainActivity.SCREEN_WIDTH;
import static com.example.banmaybay.gameobject.Aircraft.AIRCRAFT_SIZE;

import android.graphics.Canvas;

import com.example.banmaybay.GameLoop;
import com.example.banmaybay.graphics.Sprite;
import com.example.banmaybay.graphics.SpriteSheet;

import java.util.Objects;
import java.util.Random;

public class Enemy extends GameObject {
    public static final int ENEMY_SIZE = 150;
    public static final int ENEMY_MAX_SPEED = 5;
    private static final double SPAWN_PER_MINUTE = 30;
    private static final double SPAWN_PER_SECOND = SPAWN_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWN_PER_SECOND;
    public static double updateUntilNextSpawn = 0;
    private static final double STAGE_PER_SECOND = 60.0;
    private static final double UPDATES_PER_STAGE = GameLoop.MAX_UPS/STAGE_PER_SECOND;
    private double updateUntilNextExplodeStage = 0;
    private final int[][] stages = {{5, 0}, {5, 1}, {5, 2}, {5, 3}, {5, 4}, {5, 5}, {5, 6}, {5, 7},
                    {6, 0}, {6, 1}, {6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {6, 7},
                    {7, 0}, {7, 1}, {7, 2}, {7, 3}, {7, 4}, {7, 5}, {7, 6}, {7, 7},
                    {8, 0}, {8, 1}, {8, 2}, {8, 3}, {8, 4}, {8, 5}, {8, 6}, {8, 7}};
    private boolean isDestroyed = false;
    private int explodeStage = 0;
    private boolean finishExplode = false;
    private final int movingPosition = new Random().nextInt((SCREEN_HEIGHT * 4) / 10) + (SCREEN_HEIGHT) / 10;
    private Sprite sprite;
    private final SpriteSheet spriteSheet;
    private String enemyMode = "arriving";
    private boolean movingLeft;
    private int changeDirectionTimes = 0;

    public Enemy(double positionX, double positionY, Sprite sprite, SpriteSheet spriteSheet) {
        super(positionX, positionY);
        this.sprite = sprite;
        this.spriteSheet = spriteSheet;
        movingLeft = new Random().nextBoolean();
    }

    public static boolean readyToSpawn() {
        if (updateUntilNextSpawn <= 0) {
            updateUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        } else {
            updateUntilNextSpawn--;
            return false;
        }
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
            if (positionY >= movingPosition) {
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

    public void explode() {
        if (explodeStage >= 32) {
            finishExplode = true;
        } else {
            sprite = spriteSheet.getSprite(stages[explodeStage][1], stages[explodeStage][0]);
        }

        if (updateUntilNextExplodeStage < UPDATES_PER_STAGE) {
            updateUntilNextExplodeStage += 1;
        } else {
            explodeStage++;
            updateUntilNextExplodeStage -= UPDATES_PER_STAGE;
        }
    }

    public boolean isFinishExplode() {
        return finishExplode;
    }
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }
}
