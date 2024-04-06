package com.example.banmaybay.gameobject;

import static com.example.banmaybay.MainActivity.SCREEN_HEIGHT;
import static com.example.banmaybay.MainActivity.SCREEN_WIDTH;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.banmaybay.GameLoop;
import com.example.banmaybay.gamepanel.HealthBar;
import com.example.banmaybay.gamepanel.Joystick;
import com.example.banmaybay.graphics.Sprite;
import com.example.banmaybay.graphics.SpriteSheet;

import java.util.Objects;

/*
 * Aircraft is the main character of the game, which you can control
 * by touching the screen or using a joystick
 */
public class Aircraft extends Plane {
    public static int MAX_HEALTH_POINT;
    public static int PLANE_SIZE;
    private static final double SPEED_PIXELS_PER_SECOND = 600.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    protected static final double SHOOT_PER_SECOND = 1.0;
    protected static final double SECOND_PER_SHOOT = 1.0 / SHOOT_PER_SECOND;
    protected static long fireTime;
    private double anchorPositionX;
    private double anchorPositionY;
    private double oldPositionX;
    private double oldPositionY;
    private double velocityX;
    private double velocityY;
    private Joystick joystick;
    public Aircraft(Context context, Joystick joystick, double positionX, double positionY, Sprite sprite, String difficulty) {
        super(positionX, positionY);
        if (Objects.equals(difficulty, "Easy")) {
            MAX_HEALTH_POINT = 5;
        } else if (Objects.equals(difficulty, "Normal")) {
            MAX_HEALTH_POINT = 4;
        } else if (Objects.equals(difficulty, "Hard")) {
            MAX_HEALTH_POINT = 3;
        } else if (Objects.equals(difficulty, "Insane")) {
            MAX_HEALTH_POINT = 2;
        }
        PLANE_SIZE = 200;
        
        this.joystick = joystick;
        this.sprite = sprite;
        fireTime = System.currentTimeMillis();
        healthBar = new HealthBar(context);
        this.healthPoint = MAX_HEALTH_POINT;
    }

    public static boolean readyToFire() {
        if (System.currentTimeMillis() - fireTime >= SECOND_PER_SHOOT * 1e3) {
            fireTime = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    public void draw(Canvas canvas) {
        sprite.draw(canvas, (int) positionX, (int) positionY, PLANE_SIZE);
        healthBar.draw(canvas, this);
    }

    public void update(SpriteSheet spriteSheet) {

        // update position
        velocityX = joystick.getActuatorX() * MAX_SPEED;
        velocityY = joystick.getActuatorY() * MAX_SPEED;
        setPositionOnJoystick(positionX + velocityX, positionY + velocityY);

        // update aircraft
        int spriteX = 2;
        int spriteY = 2;
        if (velocityX > 0.2 * MAX_SPEED) {
            spriteX++;
        }
        if (velocityX > 0.9 * MAX_SPEED) {
            spriteX++;
        }
        if (velocityX < -0.2 * MAX_SPEED) {
            spriteX--;
        }
        if (velocityX < -0.9 * MAX_SPEED) {
            spriteX--;
        }

        if (velocityY > 0.2 * MAX_SPEED) {
            spriteY++;
        }
        if (velocityY > 0.9 * MAX_SPEED) {
            spriteY++;
        }
        if (velocityY < -0.2 * MAX_SPEED) {
            spriteY--;
        }
        if (velocityY < -0.9 * MAX_SPEED) {
            spriteY--;
        }
        this.sprite = spriteSheet.getSprite(spriteX, spriteY);
    }
    public void setPositionOnTouch(double positionX, double positionY) {
        double newPositionX = oldPositionX - anchorPositionX + positionX;
        double newPositionY = oldPositionY - anchorPositionY + positionY;
        if(newPositionX >= 0 && newPositionX <= SCREEN_WIDTH) {
            this.positionX = newPositionX;
        }
        if(newPositionY >= 0 && newPositionY <= SCREEN_HEIGHT) {
            this.positionY = newPositionY;
        }
    }
    public void setPositionOnJoystick(double positionX, double positionY) {
        if(positionX >= 0 && positionX <= SCREEN_WIDTH) {
            this.positionX = positionX;
        }
        if(positionY >= 0 && positionY <= SCREEN_HEIGHT) {
            this.positionY = positionY;
        }
    }
    public void setAnchorPosition(double anchorPositionX, double anchorPositionY) {
        this.anchorPositionX = anchorPositionX;
        this.anchorPositionY = anchorPositionY;
        this.oldPositionX = positionX;
        this.oldPositionY = positionY;
    }
    public double getPositionX() {
        return positionX;
    }
    public double getPositionY() {
        return positionY;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

}
