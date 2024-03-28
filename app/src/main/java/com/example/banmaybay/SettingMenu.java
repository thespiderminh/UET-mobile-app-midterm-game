package com.example.banmaybay;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingMenu extends AppCompatActivity {
    SeekBar seekBar;
    ImageView imageView, imGameMode;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_menu);
        seekBar = (SeekBar) findViewById(R.id.seVolume);
        imageView = (ImageView) findViewById(R.id.imVolume);
        imGameMode = (ImageView) findViewById(R.id.imGameMode);
    }
}