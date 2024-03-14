package com.example.banmaybay;

import static com.example.banmaybay.MainActivity.SCREEN_HEIGHT;
import static com.example.banmaybay.MainActivity.SCREEN_WIDTH;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.Objects;

/*
 * Game manages all objects in the game and is responsible for updating all states
 * and render all objects to the screen
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoop gameLoop;
    private Aircraft aircraft;
    private Context context;
    private Joystick joystick;
    private String gameMode; // gameMode = 0:touch, gameMode = 1:joystick
    public Game(Context context) {
        super(context);
        this.context = getContext();

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        // Create a game loop
        gameLoop = new GameLoop(this, surfaceHolder);
        setFocusable(true);

        // Create joystick
        joystick = new Joystick(SCREEN_WIDTH / 2, SCREEN_HEIGHT * 9 / 10, 150, 75);

        // Create an Aircraft
        aircraft = new Aircraft(context, joystick, (double) SCREEN_WIDTH / 2, (double) (SCREEN_HEIGHT * 8) / 10, 50);
//        gameMode = "touch";
        gameMode = "joystick";
    }

    // For handling all touch action
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (Objects.equals(gameMode, "touch")) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    aircraft.setAnchorPosition((double) event.getX(), (double) event.getY());
                    return true;
                case MotionEvent.ACTION_MOVE:
                    aircraft.setPositionOnTouch((double) event.getX(), (double) event.getY());
                    return true;
                default:
                    break;
            }
        } else if (Objects.equals(gameMode, "joystick")) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    joystick.setPosition((double) event.getX(), (double) event.getY());
                    joystick.setIsPressed(true);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (joystick.getIsPressed()) {
                        joystick.setActuator((double) event.getX(), (double) event.getY());
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                    return true;
                default:
                    break;
            }
        }


        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startLoop();
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);

        aircraft.draw(canvas);

        if(joystick.getIsPressed()) {
            joystick.draw(canvas);
        }
    }

    private void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 50, paint);
    }

    private void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 150, paint);
    }

    public void update() {
        // update all state
        joystick.update();
        aircraft.update();
    }
}
