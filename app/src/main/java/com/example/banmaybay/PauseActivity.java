package com.example.banmaybay;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.banmaybay.databinding.ActivityPauseBinding;

public class PauseActivity extends AppCompatActivity {
    private ActivityPauseBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPauseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textView.setOnClickListener(v -> {
            MainActivity.game.isPause = false;
            this.finish();
        });
    }
}