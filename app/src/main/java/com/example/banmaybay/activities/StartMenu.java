package com.example.banmaybay.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import com.example.banmaybay.MainActivity;
import com.example.banmaybay.R;
import com.example.banmaybay.musicandsound.SoundEffect;
import com.example.banmaybay.musicandsound.StartMusic;
import com.google.android.material.internal.ManufacturerUtils;

public class StartMenu extends AppCompatActivity {

    Button btPlay;
    Button buttonOptions;
    Button buttonHighScore;
    Button buttonQuitGame;
    SoundEffect sound;
    private String color = "White";
    private String gameMode = "joystick";
    private String music = "Default";
    private final int REQUEST_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        btPlay = findViewById(R.id.btPlay);

        Intent i = getIntent();
        String colorIfFromGameOver = i.getStringExtra("Color");
        String gameModeIfFromGameOver = i.getStringExtra("GameMode");
        String musicIfFromGameOver = i.getStringExtra("Music");
        if (colorIfFromGameOver != null) {
            color = colorIfFromGameOver;
            gameMode = gameModeIfFromGameOver;
            music = musicIfFromGameOver;
        }

        buttonOptions = findViewById(R.id.buttonOptions);
        buttonHighScore = findViewById(R.id.buttonViewHighScores);
        buttonQuitGame = findViewById(R.id.buttonQuitGame);

        sound = new SoundEffect(this.getApplicationContext());

        btPlay.setOnClickListener(v -> {
            try {
                sound.buttonClick();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent myIntent = new Intent(StartMenu.this, MainActivity.class);
            myIntent.putExtra("Color", color);
            myIntent.putExtra("GameMode", gameMode);
            myIntent.putExtra("Music", music);
            startActivity(myIntent);

        });

        buttonOptions.setOnClickListener(v -> ShowMenu());

        buttonHighScore.setOnClickListener(v -> {
            Intent myIntent = new Intent(StartMenu.this, HighScoresActivity.class);
            startActivity(myIntent);
        });

        buttonQuitGame.setOnClickListener(v -> {
            sound.buttonClick();
            Intent intent = new Intent(StartMenu.this, StartMusic.class);
            stopService(intent);
            finish();
            System.exit(0);
        });
    }

    private void ShowMenu() {
        PopupMenu popupMenu = new PopupMenu(this, buttonOptions);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menuSetting) {
                    Intent intentSetting = new Intent(StartMenu.this, Setting.class);
                    intentSetting.putExtra("Color", color);
                    intentSetting.putExtra("GameMode", gameMode);
                    intentSetting.putExtra("Music", music);
                    startActivityForResult(intentSetting, REQUEST_CODE);
                }
                if (item.getItemId() == R.id.menuLightTesting) {
                    Intent intentShowLight = new Intent(StartMenu.this, ShowLight.class);
                    startActivity(intentShowLight);
                }
                if (item.getItemId() == R.id.menuAbout) {
                    StartMusic.mediaPlayerStart.pause();
                    Intent intentAbout = new Intent(StartMenu.this, AboutActivity.class);
                    startActivity(intentAbout);
                }
                if (item.getItemId() == R.id.menuProject) {
                    StartMusic.mediaPlayerStart.pause();
                    Intent intentProject = new Intent(StartMenu.this, ProjectActivity.class);
                    startActivity(intentProject);
                }

                return false;
            }
        });
        popupMenu.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("StartMenu.java", "onStart()");
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_MEDIA_AUDIO
            }, REQUEST_CODE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("StartMenu.java", "onResume()");
        Intent startMusic = new Intent(StartMenu.this, StartMusic.class);
        startMusic.putExtra("Music", music);
        startService(startMusic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            assert data != null;
            color = data.getStringExtra("Color");
            gameMode = data.getStringExtra("GameMode");
            music = data.getStringExtra("Music");
        }
    }
}