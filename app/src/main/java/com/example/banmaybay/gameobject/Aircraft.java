package com.example.banmaybay.gameobject;

import static com.example.banmaybay.MainActivity.SCREEN_HEIGHT;
import static com.example.banmaybay.MainActivity.SCREEN_WIDTH;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.banmaybay.GameLoop;
import com.example.banmaybay.gamepanel.Joystick;
import com.example.banmaybay.R;
import com.example.banmaybay.graphics.Sprite;

/*
 * Aircraft is the main character of the game, which you can control
 * by touching the screen or using a joystick
 */
public class Aircraft extends GameObject {

    private static final double SPEED_PIXELS_PER_SECOND = 600.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    public static final int AIRCRAFT_SIZE = 200;
    private double anchorPositionX;
    private double anchorPositionY;
    private double oldPositionX;
    private double oldPositionY;
    private double velocityX;
    private double velocityY;
    private Joystick joystick;
    private Sprite sprite;

    public Aircraft(Joystick joystick, double positionX, double positionY, Sprite sprite) {
        super(positionX, positionY);
        this.joystick = joystick;
        this.sprite = sprite;
    }

    public void draw(Canvas canvas) {
        sprite.draw(canvas, (int) positionX, (int) positionY, AIRCRAFT_SIZE);
        Log.e("", sprite.toString());
    }

    public void update() {
        velocityX = joystick.getActuatorX() * MAX_SPEED;
        velocityY = joystick.getActuatorY() * MAX_SPEED;
        setPositionOnJoystick(positionX + velocityX, positionY + velocityY);
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
}
