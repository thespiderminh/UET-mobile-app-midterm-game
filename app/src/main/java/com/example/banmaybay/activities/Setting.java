package com.example.banmaybay.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.banmaybay.R;

public class Setting extends AppCompatActivity {
    TabHost mytab;
    Button buttonOutSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        addControl();

        buttonOutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControl() {
        buttonOutSetting = findViewById(R.id.buttonOutSetting);

        mytab = findViewById(R.id.mytab);
        mytab.setup();

        //khai bao tab con
        TabHost.TabSpec spec1, spec2, spec3;

        //xu ly tab 1
        spec1 = mytab.newTabSpec("t1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Sound");
        mytab.addTab(spec1);

        //xu ly tab 2
        spec2 = mytab.newTabSpec("t2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Smooth");
        mytab.addTab(spec2);

        //xu ly tab 3
        spec3 = mytab.newTabSpec("t3");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("Theme");
        mytab.addTab(spec3);
    }
}