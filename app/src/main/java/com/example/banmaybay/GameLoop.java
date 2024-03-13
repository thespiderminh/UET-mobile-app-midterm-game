package com.example.banmaybay;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
    }

    public double getAverageUPS() {
        return 0;
    }

    public double getAverageFPS() {
        return 0;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

//        Canvas canva = SurfaceHolder.

        // Gameloop
        while(isRunning) {
            // Try to update and render game

            // Pause game loop to not exceed the UPS

            // skip frame to keep up with the UPS

            // Calculate average UPS and FPS
        }
    }
}
