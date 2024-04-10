package com.example.banmaybay;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.banmaybay.databinding.ActivityMainBinding;
import com.example.banmaybay.musicandsound.StartMusic;

import java.io.IOException;
import java.util.Objects;

/*
 * MainActivity is the entry point to our application
 */
public class MainActivity extends AppCompatActivity {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static Game game;
    public static MediaPlayer mediaPlayer;
    String music = "";
    private SensorManager sensorManager;
    Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity.java", "onCreate()");
        super.onCreate(savedInstanceState);

        StartMusic.mediaPlayerStart.pause();

        // Set window to fullscreen and hide status bar
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;
        Intent intent = getIntent();
        String color = intent.getStringExtra("Color");
        String gameMode = intent.getStringExtra("GameMode");
        String difficulty = intent.getStringExtra("Difficulty");

        if (music != intent.getStringExtra("Music")) {
            music = intent.getStringExtra("Music");

            // Background music
            if (Objects.equals(music, "Default")) {
                mediaPlayer = MediaPlayer.create(this, R.raw.cyclop);
            } else if (Objects.equals(music, "None")) {
                mediaPlayer = MediaPlayer.create(this, R.raw.nosound);
            } else if (music != null) {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(music);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.nosound);
                    Toast.makeText(this, "Failed to load in-game audio file", Toast.LENGTH_SHORT).show();
                }
            }
        }

        mediaPlayer.setLooping(true);

        // sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        // Set content view to game, so that objects in the Game class can be rendered to the screen
        game = new Game(this, color, gameMode, music, difficulty, sensorManager, accelerometer);
        setContentView(game);
    }

    @Override
    protected void onStart() {
        Log.d("MainActivity.java", "onStart()\nmediaPlayer.start()");
        super.onStart();
        mediaPlayer.start();
    }

    @Override
    protected void onResume() {
        Log.d("MainActivity.java", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("MainActivity.java", "onPause()\nmediaPlayer.pause()");
        game.pause();
        mediaPlayer.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivity.java", "onDestroy()\nmediaPlayer.release()");
        mediaPlayer.release();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d("MainActivity.java", "onStop()");
        super.onStop();
    }
}