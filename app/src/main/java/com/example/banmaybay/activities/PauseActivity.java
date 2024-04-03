package com.example.banmaybay.activities;

// import static com.example.banmaybay.MainActivity.mediaPlayer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banmaybay.MainActivity;
import com.example.banmaybay.databinding.ActivityPauseBinding;
import com.example.banmaybay.musicandsound.SoundEffect;
import com.example.banmaybay.musicandsound.StartMusic;

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