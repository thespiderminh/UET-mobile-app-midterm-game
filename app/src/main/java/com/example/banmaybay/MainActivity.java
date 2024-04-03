package com.example.banmaybay;

import android.animation.Animator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.banmaybay.databinding.ActivityMainBinding;
import com.example.banmaybay.musicandsound.StartMusic;

/*
 * MainActivity is the entry point to our application
 */
public class MainActivity extends AppCompatActivity {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static Game game;
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity.java", "onCreate()");
        super.onCreate(savedInstanceState);

        // Background music
        mediaPlayer = MediaPlayer.create(this, R.raw.cyclop);
        mediaPlayer.setLooping(true);

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

        // Set content view to game, so that objects in the Game class can be rendered to the screen
        game = new Game(this, color, gameMode);
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