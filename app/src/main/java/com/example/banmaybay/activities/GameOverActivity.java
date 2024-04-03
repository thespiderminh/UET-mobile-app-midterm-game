package com.example.banmaybay.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.banmaybay.MainActivity;
import com.example.banmaybay.R;
import com.example.banmaybay.databinding.ActivityGameOverBinding;
import com.example.banmaybay.musicandsound.SoundEffect;
import com.example.banmaybay.musicandsound.StartMusic;

import java.io.FileInputStream;
import java.util.ArrayList;

public class GameOverActivity extends AppCompatActivity {
    private ActivityGameOverBinding binding;
    private SoundEffect sound;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameOverBinding.inflate(getLayoutInflater());
        sound = new SoundEffect(this.getApplicationContext());
        setContentView(binding.getRoot());

        Intent i = getIntent();
        int score = i.getIntExtra("Score", -1);
        String color = i.getStringExtra("Color");
        String gameMode = i.getStringExtra("GameMode");

        binding.textView2.setText(binding.textView2.getText().toString() + score);

        binding.retry.setOnClickListener(v -> {
            sound.buttonClick();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Color", color);
            intent.putExtra("GameMode", gameMode);
            startActivity(intent, null);
            this.finish();
        });

        binding.mainMenu.setOnClickListener(v -> {
            sound.buttonClick();
            Intent intent = new Intent(this, StartMenu.class);
            intent.putExtra("Color", color);
            intent.putExtra("GameMode", gameMode);
            startActivity(intent, null);
            this.finish();
        });

        binding.quitGame.setOnClickListener(v -> {
            sound.buttonClick();
            Intent intent = new Intent(GameOverActivity.this, StartMusic.class);
            stopService(intent);
            this.finishAffinity();
            System.exit(0);
        });

        binding.saveHighScore.setOnClickListener(v -> {
            binding.name.getText();
        });
    }
}