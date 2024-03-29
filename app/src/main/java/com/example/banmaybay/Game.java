package com.example.banmaybay;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.banmaybay.MainActivity.SCREEN_HEIGHT;
import static com.example.banmaybay.MainActivity.SCREEN_WIDTH;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.banmaybay.activities.GameOverActivity;
import com.example.banmaybay.activities.PauseActivity;
import com.example.banmaybay.gameobject.Aircraft;
import com.example.banmaybay.gameobject.Bullet;
import com.example.banmaybay.gameobject.Enemy;
import com.example.banmaybay.gameobject.GameObject;
import com.example.banmaybay.gamepanel.GamePause;
import com.example.banmaybay.gamepanel.Joystick;
import com.example.banmaybay.gamepanel.Performance;
import com.example.banmaybay.graphics.BackGround;
import com.example.banmaybay.graphics.SpriteSheet;
import com.example.banmaybay.musicandsound.SoundEffect;
import com.example.banmaybay.musicandsound.StartMusic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/*
 * Game manages all objects in the game and is responsible for updating all states
 * and render all objects to the screen
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoop gameLoop;
    private Aircraft aircraft;
    private List<Enemy> enemyList = new ArrayList<>();
    private List<Bullet> bulletList = new ArrayList<>();
    private Joystick joystick;
    private String gameMode; // gameMode = 0:touch, gameMode = 1:joystick
    private SpriteSheet spriteSheet;
    private Performance performance;
    private BackGround background;
    private GamePause gamePause;
    public boolean isPause = false;
    private Context context;
    private int castNumberOfPause = 0;

    // Variable for recreating sound effects
    private final SoundEffect sound;
    private int score;
    public Game(Context context) {
        super(context);
        this.context = context;

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        // Create a game loop
        gameLoop = new GameLoop(this, surfaceHolder);
        setFocusable(true);

        background = new BackGround(context);

        // Initialize game panels
        performance = new Performance(context, gameLoop);
        gamePause = new GamePause(context);
        joystick = new Joystick(SCREEN_WIDTH / 2, SCREEN_HEIGHT * 9 / 10, 150, 75);

        // Create an Aircraft
        spriteSheet = new SpriteSheet(context);
        aircraft = new Aircraft(context, joystick, (double) (SCREEN_WIDTH) / 2, (double) (SCREEN_HEIGHT * 8) / 10, spriteSheet.getSprite(2,2));

        // Sound
        sound = new SoundEffect(this.getContext());

        score = 0;

//        gameMode = "touch";
        gameMode = "joystick";
    }

    // For handling all touch action
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if ((event.getX() < SCREEN_WIDTH - gamePause.getSize() || event.getY() > gamePause.getSize())) {
            if (!isPause) {
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
            }
        } else {
            isPause = !isPause;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, holder);
            castNumberOfPause = 0;
            score = 0;
        }
        gameLoop.startLoop();
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d("Game.java", "surfaceChanged()");
    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d("Game.java", "surfaceDestroyed()");
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Draw a background
        background.draw(canvas);

        // Draw game panels
        gamePause.draw(canvas);
        if(joystick.getIsPressed()) {
            joystick.draw(canvas);
        }
        performance.draw(canvas);
        drawScore(canvas);

        for (Enemy enemy : enemyList) {
            enemy.draw(canvas);
        }

        for (Bullet bullet : bulletList) {
            bullet.draw(canvas);
        }
        aircraft.draw(canvas);
    }

    private void drawScore(Canvas canvas) {
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("Score: " + score, 100, 250, paint);
    }

    public void update() {

        // Stop updating the game if your aircraft is dead
        if (aircraft.getHealthPoint() <= 0) {
            StartMusic.mediaPlayerStart.start();
            MainActivity.mediaPlayer.release();
            sound.buttonClick();

            Intent intent = new Intent(this.context, GameOverActivity.class);
            intent.putExtra("Score", score);
            startActivity(context, intent, null);

            castNumberOfPause++;
            return;
        }

        if (isPause && castNumberOfPause == 0) {
            sound.buttonClick();
            Intent intent = new Intent(this.context, PauseActivity.class);
            startActivity(context, intent, null);
            castNumberOfPause++;
            return;
        }

        // update states
        joystick.update();
        aircraft.update(spriteSheet);

        // Spawn new enemy if possible
        if (Enemy.readyToSpawn()) {
            int x = (int) (new Random().nextInt((SCREEN_WIDTH * 6) / 10) + (double) (SCREEN_WIDTH * 2) / 10);
            enemyList.add(new Enemy(x, (double) (-SCREEN_HEIGHT * 2) / 10, spriteSheet.getSprite(5, 0), spriteSheet));
            sound.enemySpawn();
        }

        // Spawn new Bullet if possible
        if (Aircraft.readyToFire()) {
            bulletList.add(new Bullet(aircraft.getPositionX(), aircraft.getPositionY(), spriteSheet.getSprite(5, 1)));
            sound.shootingSound();
        }

        // Check collision between enemies and everything else
        for (int i = 0; i < enemyList.size(); i++) {
            // Enemy trúng đạn
            int check = GameObject.isColliding(enemyList.get(i), bulletList);
            if (check >= 0) {
                sound.enemyDestroyed();
                enemyList.get(i).setDestroyed(true);
                bulletList.remove(check);
                score++;
            }

            // Enemy trúng aircraft
            if (GameObject.isColliding(enemyList.get(i), aircraft)) {
                if (!enemyList.get(i).isDestroyed()) {
                    aircraft.lossHealth();
                }
                sound.enemyBreak();
                enemyList.get(i).setDestroyed(true);
            }

            // Enemy vẫn sống
            if (!enemyList.get(i).isDestroyed()) {
                enemyList.get(i).update(aircraft);
            } else { // Enemy chết
                if (!enemyList.get(i).isFinishExplode()) {
                    enemyList.get(i).explode();
                } else {
                    enemyList.remove(i);
                    Enemy.updateUntilNextSpawn -= 1;
                    i--;
                }
            }
        }

        // Update bullets
        for (int i = 0; i < bulletList.size(); i++) {
            bulletList.get(i).update();
            if (bulletList.get(i).outOfScreen()) {
                bulletList.remove(i);
                i--;
            }
        }
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}
