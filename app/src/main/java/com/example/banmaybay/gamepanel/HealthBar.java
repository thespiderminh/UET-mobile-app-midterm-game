package com.example.banmaybay.gamepanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.banmaybay.R;
import com.example.banmaybay.gameobject.Aircraft;

public class HealthBar {
    private float width, height, margin;
    private Paint borderPaint, healthPaint;

    public HealthBar(Context context) {
        this.width = 200;
        this.height = 20;
        this.margin = 4;

        int borderColor = ContextCompat.getColor(context, R.color.healthBarColor);
        this.borderPaint = new Paint();
        borderPaint.setColor(borderColor);

        int healthColor = ContextCompat.getColor(context, R.color.healthBarHealth);
        this.healthPaint = new Paint();
        healthPaint.setColor(healthColor);
    }

    public void draw(Canvas canvas, Aircraft aircraft) {
        float x = (float) aircraft.getPositionX();
        float y = (float) aircraft.getPositionY();
        float distanceToAircraft = 100;
        float healthPointPercentage = (float) aircraft.getHealthPoint() / aircraft.MAX_HEALTH_POINT;

        // Draw border
        float borderLeft = x - width / 2;
        float borderRight = x + width / 2;
        float borderTop = y + distanceToAircraft - height / 2;
        float borderBottom = y + distanceToAircraft + height / 2;
        canvas.drawRect(borderLeft, borderTop, borderRight, borderBottom, borderPaint);

        // Draw health
        float healthWidth = width - 2 * margin;
        float healthHeight = height - 2 * margin;
        float healthLeft = borderLeft + margin;
        float healthRight = healthLeft + healthWidth * healthPointPercentage;
        float healthTop = borderTop + margin;
        float healthBottom = borderBottom - margin;
        canvas.drawRect(healthLeft, healthTop, healthRight, healthBottom, healthPaint);
    }
}
