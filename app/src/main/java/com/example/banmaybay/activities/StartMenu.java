package com.example.banmaybay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banmaybay.MainActivity;
import com.example.banmaybay.R;
import com.example.banmaybay.musicandsound.SoundEffect;
import com.example.banmaybay.musicandsound.StartMusic;

public class StartMenu extends AppCompatActivity {

    ImageButton btPlay;
    SoundEffect sound;
    private String color = "white";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        btPlay = (ImageButton) findViewById(R.id.btPlay);
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