package com.example.banmaybay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.banmaybay.databinding.ActivityPauseBinding;

public class PauseActivity extends AppCompatActivity {
    private ActivityPauseBinding binding;
    private SoundEffect sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPauseBinding.inflate(getLayoutInflater());
        sound = new SoundEffect(this.getApplicationContext());
        setContentView(binding.getRoot());

        binding.resumeGame.setOnClickListener(v -> {
            sound.buttonClick();
            MainActivity.game.isPause = false;
            this.finish();
        });

        binding.restartGame.setOnClickListener(v -> {
            sound.buttonClick();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent, null);
            this.finish();
        });

        binding.quitGame.setOnClickListener(v -> {
            sound.buttonClick();
            this.finishAffinity();
            System.exit(0);
        });
    }
}