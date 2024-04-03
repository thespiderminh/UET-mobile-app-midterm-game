package com.example.banmaybay.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banmaybay.R;
import com.example.banmaybay.databinding.ActivityHighScoresBinding;

import java.util.ArrayList;

public class HighScoresActivity extends AppCompatActivity {
    private ActivityHighScoresBinding binding;
    ArrayList<String> highScores = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private SQLiteDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHighScoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myDatabase = openOrCreateDatabase("HighScores.db", MODE_PRIVATE, null);
        try {
            String table = "CREATE TABLE highScores(name TEXT,score INTEGER, date TEXT, time TEXT)";
            myDatabase.execSQL(table);
        } catch (Exception e) {
            Log.e("HighScoresActivity.java", "Table existed");
        }

        highScores.clear();
        Cursor c = myDatabase.query("highScores", null, null, null, null, null, "score DESC", "5");
        c.moveToNext();
        String data;
        while (!c.isAfterLast()) {
            data = "Top " + (highScores.size() + 1) + ":\n" +
                    "       Name:      " + c.getString(0) + "\n" +
                    "       Score:      " + c.getString(1) + "\n" +
                    "       Date:        " + c.getString(2) + "\n" +
                    "       Time:       " + c.getString(3);
            c.moveToNext();
            highScores.add(data);
        }
        c.close();

        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.highscoreitem, highScores);
        binding.highScores.setAdapter(adapter);

        binding.back.setOnClickListener(v -> {
            finish();
        });
    }
}