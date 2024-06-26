package com.example.banmaybay.gameobject;

import static com.example.banmaybay.MainActivity.SCREEN_HEIGHT;
import static com.example.banmaybay.MainActivity.SCREEN_WIDTH;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.banmaybay.GameLoop;
import com.example.banmaybay.gamepanel.HealthBar;
import com.example.banmaybay.graphics.Sprite;
import com.example.banmaybay.graphics.SpriteSheet;

import java.util.Objects;
import java.util.Random;

public class Enemy extends Plane {
    public static int ENEMY_MAX_SPEED;
    private static int ENEMY_SPEED_UP;
    private static final double SPAWN_PER_MINUTE = 20.0;
    private static final double SPAWN_PER_SECOND = SPAWN_PER_MINUTE / 60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWN_PER_SECOND;
    public static double updateUntilNextSpawn = 0;
    private static final double STAGE_PER_SECOND = 60.0;
    private static final double UPDATES_PER_STAGE = GameLoop.MAX_UPS/STAGE_PER_SECOND;
    private double updateUntilNextExplodeStage = 0;
    protected final double SHOOT_PER_SECOND = 0.3;
    protected final double SECOND_PER_SHOOT = 1.0 / SHOOT_PER_SECOND;
    protected long fireTime;
    private final int[][] stages = {{5, 0}, {5, 1}, {5, 2}, {5, 3}, {5, 4}, {5, 5}, {5, 6}, {5, 7},
                    {6, 0}, {6, 1}, {6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {6, 7},
                    {7, 0}, {7, 1}, {7, 2}, {7, 3}, {7, 4}, {7, 5}, {7, 6}, {7, 7},
                    {8, 0}, {8, 1}, {8, 2}, {8, 3}, {8, 4}, {8, 5}, {8, 6}, {8, 7}};
    private boolean isDestroyed = false;
    private int explodeStage = 0;
    private boolean finishExplode = false;
    private final int movingPosition = new Random().nextInt((SCREEN_HEIGHT * 4) / 10) + (SCREEN_HEIGHT) / 10;
    private String enemyMode = "arriving";
    private boolean movingLeft;
    private int changeDirectionTimes = 0;
    public static int MAX_HEALTH_POINT;
    protected static int PLANE_SIZE;
    public Enemy(Context context, double positionX, double positionY, Sprite sprite, SpriteSheet spriteSheet, String difficulty) {
        super(positionX, positionY);
        if (Objects.equals(difficulty, "Easy")) {
            MAX_HEALTH_POINT = 2;
            ENEMY_MAX_SPEED = 5;
        } else if (Objects.equals(difficulty, "Normal")) {
            MAX_HEALTH_POINT = 3;
            ENEMY_MAX_SPEED = 5;
        } else if (Objects.equals(difficulty, "Hard")) {
            MAX_HEALTH_POINT = 3;
            ENEMY_MAX_SPEED = 7;
        } else if (Objects.equals(difficulty, "Insane")) {
            MAX_HEALTH_POINT = 4;
            ENEMY_MAX_SPEED = 10;
        }
        PLANE_SIZE = 150;

        this.sprite = sprite;
        this.spriteSheet = spriteSheet;
        fireTime = System.currentTimeMillis();
        healthBar = new HealthBar(context);
        this.healthPoint = MAX_HEALTH_POINT;
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
        sprite.draw(canvas, (int) positionX, (int) positionY, PLANE_SIZE);
        if (!isDestroyed) {
            healthBar.draw(canvas, this);
        }
    }
    public void update(Aircraft aircraft, int score) {
        updateSpeed(score);
        updatePosition();
        updateEnemyMode(aircraft);
    }

    private void updateSpeed(int score) {
        ENEMY_SPEED_UP = score / 10;
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
            if (positionX >= aircraft.positionX - (double) Aircraft.PLANE_SIZE / 4 &&
                    positionX <= aircraft.positionX + (double) Aircraft.PLANE_SIZE / 4) {
                enemyMode = "attacking";
            }
        } if (Objects.equals(enemyMode, "attacking")) {
            if (positionY - (double) PLANE_SIZE / 2 >= SCREEN_HEIGHT) {
                positionY = (double) (-SCREEN_HEIGHT * 2) / 10;
                positionX = new Random().nextInt((SCREEN_WIDTH * 6) / 10) + (double) (SCREEN_WIDTH * 2) / 10;
                enemyMode = "arriving";
            }
        }
    }

    private void updatePosition() {
        ENEMY_MAX_SPEED += ENEMY_SPEED_UP;
        if (Objects.equals(enemyMode, "arriving")) {
            positionY += ENEMY_MAX_SPEED;
        } else if (Objects.equals(enemyMode, "moving")) {
            if (movingLeft) {
                positionX -= ENEMY_MAX_SPEED;
                if (positionX - (double) PLANE_SIZE / 2 <= (double) (SCREEN_WIDTH) / 10) {
                    movingLeft = false;
                    changeDirectionTimes++;
                }
            } else {
                positionX += ENEMY_MAX_SPEED;
                if (positionX + (double) PLANE_SIZE / 2 >= (double) (SCREEN_WIDTH * 9) / 10) {
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
        ENEMY_MAX_SPEED -= ENEMY_SPEED_UP;
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

    public double getPositionX() {
        return positionX;
    }
    public double getPositionY() {
        return positionY;
    }

    public boolean readyToFire() {
        if (System.currentTimeMillis() - fireTime >= SECOND_PER_SHOOT * 1e3) {
            fireTime = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    public int getHealthPoint() {
        return healthPoint;
    }
}
