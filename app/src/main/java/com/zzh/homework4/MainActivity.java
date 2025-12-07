package com.zzh.homework4;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.zzh.monitor_sdk.MonitorManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MonitorManager.getInstance().startMonitor();

        Button btnLag = findViewById(R.id.btn_lag);
        btnLag.setOnClickListener(v -> {
                try {
                    Log.w("Test", "模拟掉帧...");
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        Button btnLag2 = findViewById(R.id.btn_lag2);
        btnLag2.setOnClickListener(v -> {
            try {
                Log.w("Test", "模拟死锁/卡死...");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}