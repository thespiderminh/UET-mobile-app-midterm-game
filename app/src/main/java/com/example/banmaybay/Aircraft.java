package com.example.banmaybay;

import static com.example.banmaybay.MainActivity.SCREEN_HEIGHT;
import static com.example.banmaybay.MainActivity.SCREEN_WIDTH;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

/*
 * Aircraft is the main character of the game, which you can control
 * by touching the screen or using a joystick
 */
public class Aircraft extends GameObject{

    private static final double SPEED_PIXELS_PER_SECOND = 600.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private double anchorPositionX;
    private double anchorPositionY;
    private double oldPositionX;
    private double oldPositionY;
    private double radius;
    private Paint paint;
    private double velocityX;
    private double velocityY;
    private Joystick joystick;

    public Aircraft(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        super(positionX, positionY);
        this.radius = radius;
        this.joystick = joystick;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.red);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);
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
