package com.example.banmaybay.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.banmaybay.MainActivity;
import com.example.banmaybay.R;
import com.example.banmaybay.musicandsound.SoundEffect;
import com.example.banmaybay.musicandsound.StartMusic;

public class StartMenu extends AppCompatActivity {

    Button btPlay;
    Button buttonOptions;

    Button buttonQuitGame;
    SoundEffect sound;
    public boolean startMusicIsPlaying = true;

    ImageButton btPlay;
    SoundEffect sound;
    private String color = "white";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        btPlay = findViewById(R.id.btPlay);


        buttonOptions = findViewById(R.id.buttonOptions);
        buttonQuitGame = findViewById(R.id.buttonQuitGame);

        sound = new SoundEffect(this.getApplicationContext());



        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sound.buttonClick();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                StartMusic.mediaPlayerStart.pause();

                Intent myIntent = new Intent(StartMenu.this, MainActivity.class);
                myIntent.putExtra("Color", color);
                startActivity(myIntent);

            }
        });

        buttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu();
            }
        });

        buttonQuitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
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
                    startActivity(intentSetting);
                }
                if (item.getItemId() == R.id.menuLightTesting) {
                    Intent intentShowLight = new Intent(StartMenu.this, ShowLight.class);
                    startActivity(intentShowLight);
                }
                if (item.getItemId() == R.id.menuAbout) {

                }

                return false;
            }
        });
        popupMenu.show();
    }


    @Override
    protected void onStart() {
        super.onStart();

//        mediaPlayer = MediaPlayer.create(this, R.raw.battle_theme);
//        mediaPlayer.start();

        Intent startMusic = new Intent(StartMenu.this, StartMusic.class);
        startService(startMusic);
    }


}