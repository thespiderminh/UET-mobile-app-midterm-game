package com.example.banmaybay.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.banmaybay.R;
import com.example.banmaybay.musicandsound.MusicFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Setting extends AppCompatActivity {
    TabHost mytab;
    Button buttonOutSetting;
    private ImageView imageView;
    private String color;
    private String gameMode;
    private String music;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        addControl();

        buttonOutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Color", color);
                intent.putExtra("GameMode", gameMode);
                intent.putExtra("Music", music);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @SuppressLint("ResourceType")
    private void addControl() {
        buttonOutSetting = findViewById(R.id.buttonOutSetting);

        mytab = findViewById(R.id.mytab);
        mytab.setup();

        //khai bao tab con
        TabHost.TabSpec spec1, spec2, spec3;

        intent = getIntent();
        color = intent.getStringExtra("Color");
        gameMode = intent.getStringExtra("GameMode");
        music = intent.getStringExtra("Music");
        Spinner spinner = findViewById(R.id.spinner);
        imageView = findViewById(R.id.imageView);
        RadioGroup radioGroupMusic = findViewById(R.id.radioGroupMusic);
        ListView listMusic = findViewById(R.id.listMusic);

        //xu ly tab 1
        spec1 = mytab.newTabSpec("t1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Sound");
        mytab.addTab(spec1);

        if (Objects.equals(music, "Default")) {
            radioGroupMusic.check(R.id.defaultMusic);
            listMusic.setVisibility(View.INVISIBLE);
        } else if (Objects.equals(music, "None")) {
            radioGroupMusic.check(R.id.noMusic);
            listMusic.setVisibility(View.INVISIBLE);
        } else {
            radioGroupMusic.check(R.id.chooseMusic);
            listMusic.setVisibility(View.VISIBLE);
        }

        radioGroupMusic.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.defaultMusic) {
                listMusic.setVisibility(View.INVISIBLE);
                music = "Default";
            } else if (checkedId == R.id.chooseMusic) {
                listMusic.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.noMusic) {
                music = "None";
                listMusic.setVisibility(View.INVISIBLE);
            }
        });

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> paths = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA, // path
        };
        Cursor cursor = getContentResolver().query(uri,
                projection, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(0);
                String duration = cursor.getString(1);
                String path = cursor.getString(2);

                titles.add(title);
                paths.add(path);
            }
            cursor.close();
        }
        listMusic.setAdapter(new ArrayAdapter<>(this, R.layout.highscoreitem, titles));
        listMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) findViewById(R.id.songName)).setText("           " + titles.get(position));
                music = paths.get(position);
            }
        });


            //xu ly tab 2
        spec2 = mytab.newTabSpec("t2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Game Mode");
        mytab.addTab(spec2);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        if (Objects.equals(gameMode, "joystick")) {
            radioGroup.check(R.id.joystick);
        } else if (Objects.equals(gameMode, "touch")) {
            radioGroup.check(R.id.touch);
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.joystick) {
                gameMode = "joystick";
            } else if (checkedId == R.id.touch) {
                gameMode = "touch";
            }
        });


        //xu ly tab 3
        spec3 = mytab.newTabSpec("t3");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("Color");
        mytab.addTab(spec3);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colors, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String[] data = getResources().getStringArray(R.array.colors);
        spinner.setSelection(findIndex(data, color));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Bitmap fullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_sheet_white);
                int maxX = fullBitmap.getWidth();
                int maxY = fullBitmap.getHeight();
                int x = maxX/10*2;
                int y = maxY/10*2;
                int width = maxX/10;
                int height = maxY/10;

                if (parent.getItemAtPosition(position).toString().equals("White")) {
                    fullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_sheet_white);
                    imageView.setImageBitmap(Bitmap.createBitmap(fullBitmap, x, y, width, height));
                } else if (parent.getItemAtPosition(position).toString().equals("Red")) {
                    fullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_sheet_red);
                    imageView.setImageBitmap(Bitmap.createBitmap(fullBitmap, x, y, width, height));
                } else if (parent.getItemAtPosition(position).toString().equals("Yellow")) {
                    fullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_sheet_yellow);
                    imageView.setImageBitmap(Bitmap.createBitmap(fullBitmap, x, y, width, height));
                } else if (parent.getItemAtPosition(position).toString().equals("Blue")) {
                    fullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_sheet_blue);
                    imageView.setImageBitmap(Bitmap.createBitmap(fullBitmap, x, y, width, height));
                } else if (parent.getItemAtPosition(position).toString().equals("Orange")) {
                    fullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_sheet_orange);
                    imageView.setImageBitmap(Bitmap.createBitmap(fullBitmap, x, y, width, height));
                } else if (parent.getItemAtPosition(position).toString().equals("Green")) {
                    fullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_sheet_green);
                    imageView.setImageBitmap(Bitmap.createBitmap(fullBitmap, x, y, width, height));
                }

                color = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static int findIndex(String[] array, String item) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(item)) {
                return i; // Return the index if item is found
            }
        }
        return -1; // Return -1 if item is not found
    }
}