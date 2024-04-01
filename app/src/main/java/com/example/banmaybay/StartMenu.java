package com.example.banmaybay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StartMenu extends AppCompatActivity {

    Button btPlay, btOptions, btQuit, btHighScore;
    ImageButton btContact;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        btPlay = (Button) findViewById(R.id.btStart);
        btOptions = (Button) findViewById(R.id.btOptions);
        btQuit = (Button) findViewById(R.id.btQuit);
        btContact = (ImageButton) findViewById(R.id.btContact);
        btHighScore = (Button) findViewById(R.id.btHighScore);
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
        btOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });
        btHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartMenu.this, ViewHighScore.class);
                startActivity(intent);
            }
        });
    }

    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(this, btOptions);
        popupMenu.getMenuInflater().inflate(R.menu.options_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.setting) {
                    Intent intent = new Intent(StartMenu.this, SettingMenu.class);
                    startActivity(intent);
                } else if(itemId == R.id.highScore) {
                    Intent intent = new Intent(StartMenu.this, CheckBrightness.class);
                    startActivity(intent);
                } else if (itemId == R.id.about) {
                    Intent intent = new Intent(StartMenu.this, About.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupMenu.show();
    }
}