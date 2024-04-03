package com.example.banmaybay.musicandsound;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.banmaybay.R;

public class StartMusic extends Service {
    public static MediaPlayer mediaPlayerStart;

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
        mediaPlayerStart = MediaPlayer.create(StartMusic.this, R.raw.battle_theme);
        mediaPlayerStart.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("StartMusic.java", "onStartCommand()");
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

