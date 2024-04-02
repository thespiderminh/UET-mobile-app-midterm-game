package com.example.banmaybay.activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.banmaybay.R;
import com.example.banmaybay.R.id;

public class ShowLight extends AppCompatActivity implements SensorEventListener {
    TextView textViewShowLight, textViewWarning;
    SensorManager sensorManager;
    float changedValue;
    Button buttonQuitShowLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_light);

        textViewShowLight = findViewById(R.id.textViewShowLight);
        textViewWarning = findViewById(R.id.textViewWarning);
        buttonQuitShowLight = findViewById(id.buttonQuitShowLight);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        buttonQuitShowLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        changedValue = event.values[0];
        textViewShowLight.setText("Độ sáng môi trường: " + String.valueOf(changedValue) + " Lux");
        if (changedValue < 300) {
            textViewWarning.setText("Thiếu ánh sáng, nguy hại cho mắt!");
        }
        if (changedValue >= 300 && changedValue < 600) {
            textViewWarning.setText("Ánh sáng tạm đủ, nên cung cấp thêm!");
        }
        if (changedValue >= 600 && changedValue < 1700) {
            textViewWarning.setText("Môi trường tuyệt vời để chơi!");
        }
        if (changedValue >= 1700 ) {
            textViewWarning.setText("Quá thừa sáng, gây nhanh mỏi mắt!");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}