package com.example.banmaybay;

import android.annotation.SuppressLint;
import android.content.Intent;
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
    ImageButton btContact, btSetting;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        btPlay = (ImageButton) findViewById(R.id.btPlay);
        btContact = (ImageButton) findViewById(R.id.btContact);
        btSetting = (ImageButton) findViewById(R.id.btSetting);
        btContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(StartMenu.this, ContactMenu.class);
                startActivity(myIntent);
            }
        });
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(StartMenu.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
        btSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(StartMenu.this, SettingMenu.class);
                startActivity(myIntent);
            }
        });
    }
}