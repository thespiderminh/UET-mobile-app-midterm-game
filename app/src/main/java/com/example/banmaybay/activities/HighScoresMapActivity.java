package com.example.banmaybay.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.banmaybay.R;
import com.example.banmaybay.databinding.ActivityHighScoresMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class HighScoresMapActivity extends AppCompatActivity {
    private ActivityHighScoresMapBinding binding;
    private GoogleMap googleMap;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> scores = new ArrayList<>();
    ArrayList<Double> lats = new ArrayList<>();
    ArrayList<Double> longs = new ArrayList<>();
    private SQLiteDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHighScoresMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get data
        myDatabase = openOrCreateDatabase("HighScores.db", MODE_PRIVATE, null);

        names.clear();
        scores.clear();
        lats.clear();
        longs.clear();
        Cursor c = myDatabase.query("highScores", null, null, null, null, null, "score DESC", "5");
        c.moveToNext();
        while (!c.isAfterLast()) {
            names.add(c.getString(0));
            scores.add(c.getInt(1));
            lats.add(c.getDouble(4));
            longs.add(c.getDouble(5));
            c.moveToNext();
        }
        c.close();


        // Google map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            for (int i = 0; i < names.size(); i++) {
                LatLng a = new LatLng(lats.get(i), longs.get(i));
                this.googleMap.addMarker(new MarkerOptions().position(a).title(names.get(i) + " - " + scores.get(i)));
            }
        });

        binding.back.setOnClickListener(v -> {
            finish();
        });
    }
}