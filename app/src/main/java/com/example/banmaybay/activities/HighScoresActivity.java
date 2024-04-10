package com.example.banmaybay.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.banmaybay.R;
import com.example.banmaybay.databinding.ActivityHighScoresBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class HighScoresActivity extends AppCompatActivity {
    private ActivityHighScoresBinding binding;
    ArrayList<String> highScores = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private SQLiteDatabase myDatabase;
    private FusedLocationProviderClient fusedLocationClient;
    double[] latLong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHighScoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myDatabase = openOrCreateDatabase("HighScores.db", MODE_PRIVATE, null);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        ActivityCompat.requestPermissions(this, new String[] {
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.ACCESS_COARSE_LOCATION"
        }, 123456);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("GameOverActivity.java", "ACCESS_FINE_LOCATION" + ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION));
            Log.e("GameOverActivity.java", "ACCESS_COARSE_LOCATION" + ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION));
        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                latLong = getLocation(location);
                getData(latLong);
                setAdapterToList();
            });
        }

        binding.global.setOnClickListener(v -> {
            binding.global.setAlpha(1F);
            binding.local.setAlpha(0.5F);
            getData(latLong);
            setAdapterToList();
        });

        binding.local.setOnClickListener(v -> {
            binding.local.setAlpha(1F);
            binding.global.setAlpha(0.5F);
            getData(latLong);
            setAdapterToList();
        });

        binding.back.setOnClickListener(v -> {
            finish();
        });

        binding.clearHistory.setOnClickListener(v -> {
            myDatabase.execSQL("delete from highScores");
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });

        binding.viewMap.setOnClickListener(v -> {
            Intent intent = new Intent(this, HighScoresMapActivity.class);
            startActivity(intent);
        });
    }

    private void setAdapterToList() {
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.highscoreitem, highScores);
        binding.highScores.setAdapter(adapter);
    }

    private void getData(double[] latLong) {
        highScores.clear();
        Cursor c = myDatabase.query("highScores", null, null, null, null, null, "score DESC", "5");
        c.moveToNext();
        String data;
        while (!c.isAfterLast()) {
            if (binding.local.getAlpha() == 1) {
                double lat = c.getDouble(4);
                double Long = c.getDouble(5);
                double distance = getDistance(latLong[0], latLong[1], lat, Long);
                if (distance > 3) {
                    c.moveToNext();
                    continue;
                }
            }
            data = "Top " + (highScores.size() + 1) + ":\n" +
                    "       Name:      " + c.getString(0) + "\n" +
                    "       Score:      " + c.getString(1) + "\n" +
                    "       Date:        " + c.getString(2) + "\n" +
                    "       Time:       " + c.getString(3);
            c.moveToNext();
            highScores.add(data);
        }
        c.close();
    }

    private double getDistance(double lat1, double long1, double lat2, double long2) {
        double R = 6371; // earth's radius (km)
        lat1 = Math.toRadians(lat1);
        long1 = Math.toRadians(long1);
        lat2 = Math.toRadians(lat2);
        long2 = Math.toRadians(long2);

        double dLat = lat2 - lat1;
        double dLon = long2 - long1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        double ans = R * c;
        Log.d("HighScoresActivity.java", String.valueOf(ans));
        return ans;
    }

    private double[] getLocation(Location location) {
        double[] ans = new double[2];
        if (location != null) {
            ans[0] = location.getLatitude();
            ans[1] = location.getLongitude();
            return ans;
        } else {
            Log.e("GameOverActivity.java", "Location is null");
            return null;
        }
    }
}