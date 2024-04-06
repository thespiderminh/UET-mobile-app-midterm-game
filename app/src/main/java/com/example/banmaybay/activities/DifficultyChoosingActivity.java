package com.example.banmaybay.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banmaybay.MainActivity;
import com.example.banmaybay.R;
import com.example.banmaybay.databinding.ActivityDifficultyChoosingBinding;

public class DifficultyChoosingActivity extends AppCompatActivity {
    private ActivityDifficultyChoosingBinding binding;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDifficultyChoosingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.difficultyImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.easy));
        binding.textView3.setText("Easy");

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    binding.difficultyImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.easy));
                    binding.textView3.setText("Easy");
                } else if (progress == 1) {
                    binding.difficultyImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.normal));
                    binding.textView3.setText("Normal");
                } else if (progress == 2) {
                    binding.difficultyImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.hard));
                    binding.textView3.setText("Hard");
                } else if (progress == 3) {
                    binding.difficultyImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.insane));
                    binding.textView3.setText("Insane");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.start.setOnClickListener(v -> {
            Intent myIntent = new Intent(DifficultyChoosingActivity.this, MainActivity.class);
            myIntent.putExtra("Color", getIntent().getStringExtra("Color"));
            myIntent.putExtra("GameMode", getIntent().getStringExtra("GameMode"));
            myIntent.putExtra("Music", getIntent().getStringExtra("Music"));
            myIntent.putExtra("Difficulty", binding.textView3.getText().toString());
            startActivity(myIntent);
        });
    }
}