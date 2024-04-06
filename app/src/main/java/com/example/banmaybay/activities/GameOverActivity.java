package com.example.banmaybay.activities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GameOverActivity extends AppCompatActivity implements TextWatcher {
    private ActivityGameOverBinding binding;
    private SoundEffect sound;
    private SQLiteDatabase myDatabase;
    private Set<String> allNames = new HashSet<>();
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
        String music = i.getStringExtra("Music");

        myDatabase = openOrCreateDatabase("HighScores.db", MODE_PRIVATE, null);

        binding.textView2.setText(binding.textView2.getText().toString() + score);

        binding.retry.setOnClickListener(v -> {
            sound.buttonClick();
            Intent intent = new Intent(this, DifficultyChoosingActivity.class);
            intent.putExtra("Color", color);
            intent.putExtra("GameMode", gameMode);
            intent.putExtra("Music", music);
            startActivity(intent, null);
            this.finish();
        });

        binding.mainMenu.setOnClickListener(v -> {
            sound.buttonClick();
            Intent intent = new Intent(this, StartMenu.class);
            intent.putExtra("Color", color);
            intent.putExtra("GameMode", gameMode);
            intent.putExtra("Music", music);
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

        binding.name.addTextChangedListener(this);

        binding.saveHighScore.setOnClickListener(v -> {
            String name = binding.name.getText().toString();
            if (name.isEmpty()) {
                name = "No name";
            }
            String date = "";
            String time = "";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate localDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                date = localDate.format(formatter);
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalTime localDate = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                time = localDate.format(formatter);
            }
            ContentValues content = new ContentValues();
            content.put("name", name);
            content.put("score", score);
            content.put("date", date);
            content.put("time", time);
            if (myDatabase.insert("highScores", null, content) == -1) {
                Toast.makeText(this, "Saving has failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "The save was successful", Toast.LENGTH_SHORT).show();
            }

            sound.buttonClick();
            Intent intent = new Intent(this, StartMenu.class);
            intent.putExtra("Color", color);
            intent.putExtra("GameMode", gameMode);
            intent.putExtra("Music", music);
            startActivity(intent, null);
            this.finish();
        });

        allNames.clear();
        Cursor c = myDatabase.query("highScores", null, null, null, null, null, null);
        c.moveToNext();
        while (!c.isAfterLast()) {
            allNames.add(c.getString(0));
            c.moveToNext();
        }
        c.close();
        ArrayList<String> x = new ArrayList<>(allNames);
        binding.name.setAdapter(new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                x));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}