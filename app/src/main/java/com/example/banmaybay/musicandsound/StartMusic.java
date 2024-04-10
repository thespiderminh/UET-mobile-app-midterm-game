package com.example.banmaybay.musicandsound;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.banmaybay.R;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class StartMusic extends Service {
    public static MediaPlayer mediaPlayerStart;
    String music = "";

    public StartMusic() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.d("StartMusic.java", "onCreate()");
        super.onCreate();
        mediaPlayerStart = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("StartMusic.java", "onStartCommand()");
        if (intent == null) {
            return super.onStartCommand(null, flags, startId);
        }
        if (Objects.equals(music, intent.getStringExtra("Music"))) {
            return super.onStartCommand(null, flags, startId);
        }

        music = intent.getStringExtra("Music");
        mediaPlayerStart.reset();
        if (Objects.equals(music, "Default")) {
            mediaPlayerStart = MediaPlayer.create(StartMusic.this, R.raw.battle_theme);
        } else if (Objects.equals(music, "None")) {
            mediaPlayerStart = MediaPlayer.create(StartMusic.this, R.raw.nosound);
        } else if (music != null){
            mediaPlayerStart = MediaPlayer.create(StartMusic.this, Uri.parse(music));
        }

        mediaPlayerStart.setLooping(true);
        mediaPlayerStart.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("StartMusic.java", "onDestroy()");
        super.onDestroy();
        mediaPlayerStart.stop();
    }
}

