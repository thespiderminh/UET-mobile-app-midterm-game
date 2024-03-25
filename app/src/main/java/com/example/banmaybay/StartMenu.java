package com.example.banmaybay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StartMenu extends AppCompatActivity {

    ImageButton btPlay;
    SoundEffect sound;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        btPlay = (ImageButton) findViewById(R.id.btPlay);
        sound = new SoundEffect(this.getApplicationContext());
        mediaPlayer = MediaPlayer.create(this, R.raw.battle_theme);
        mediaPlayer.setLooping(true);
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sound.buttonClick();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mediaPlayer.stop();
                Intent myIntent = new Intent(StartMenu.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer.start();
    }
}