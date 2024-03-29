package com.example.banmaybay;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class InGameMusic extends Service {
    public InGameMusic() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}