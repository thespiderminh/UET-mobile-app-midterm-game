package com.example.banmaybay.activities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;


import com.example.banmaybay.databinding.ActivityGameOverBinding;
import com.example.banmaybay.musicandsound.SoundEffect;
import com.example.banmaybay.musicandsound.StartMusic;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class GameOverActivity extends AppCompatActivity implements TextWatcher {
    private ActivityGameOverBinding binding;
    private SoundEffect sound;
    private SQLiteDatabase myDatabase;
    private Set<String> allNames = new HashSet<>();
    private FusedLocationProviderClient fusedLocationClient;
    private int requestCode = 123456;
    private String name;
    private String date;
    private String time;
    double Lat = 0.0;
    double Long = 0.0;

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
            Log.d("GameOverActivity.java", music);
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

            // name
            name = binding.name.getText().toString();
            if (name.isEmpty()) {
                name = "No name";
            }

            //date and time
            date = "";
            time = "";

            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date = localDate.format(formatter);

            LocalTime localTime = LocalTime.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            time = localTime.format(timeFormatter);

            // Lat and long
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            ActivityCompat.requestPermissions(this, new String[] {
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION"
            }, requestCode);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e("GameOverActivity.java", "ACCESS_FINE_LOCATION" + ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION));
                Log.e("GameOverActivity.java", "ACCESS_COARSE_LOCATION" + ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION));
            } else {
                fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                    if (location != null) {
                        Lat = location.getLatitude();
                        Long = location.getLongitude();
                        Log.e("GameOverActivity.java", "Lat.get(): " + Lat);
                        Log.e("GameOverActivity.java", "Long.get(): " + Long);
                    } else {
                        Log.e("GameOverActivity.java", "Location is null");
                    }

                    ContentValues content = new ContentValues();
                    content.put("name", name);
                    content.put("score", score);
                    content.put("date", date);
                    content.put("time", time);
                    content.put("lat", Lat);
                    content.put("long", Long);
                    Log.e("GameOverActivity.java", "Lat.get(): " + Lat);
                    Log.e("GameOverActivity.java", "Long.get(): " + Long);
                    if (myDatabase.insert("highScores", null, content) == -1) {
                        Toast.makeText(this, "Saving has failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "The save was successful", Toast.LENGTH_SHORT).show();
                    }
                });
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