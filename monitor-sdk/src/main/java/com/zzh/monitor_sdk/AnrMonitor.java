package com.zzh.monitor_sdk;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class AnrMonitor extends Thread {
    private static final String TAG = "AnrMonitor";
    private static final long ANR_TIMEOUT = 3000;
    private boolean isMonitoring = false;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    private volatile long lastTick = 0;
    private volatile long currentTick = 0;

    private Runnable ticker = new Runnable() {
        @Override
        public void run() {
            currentTick = (currentTick + 1) % Long.MAX_VALUE;
        }
    };

    @Override
    public void run() {
        while (isMonitoring) {
            lastTick = currentTick;

            mainHandler.post(ticker);

            try {
                Thread.sleep(ANR_TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

            if (currentTick == lastTick) {
                Log.e(TAG, "发生 ANR！主线程已经卡死 " + (ANR_TIMEOUT/1000) + " 秒了！");

                StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
                for (StackTraceElement element : stackTrace) {
                    Log.e(TAG, "\tat " + element.toString());
                }
            } else {
                Log.d(TAG, "主线程很健康，看门狗继续巡逻...");
            }
        }
    }

    public void startMonitor() {
        isMonitoring = true;
        this.start();
        Log.d(TAG, "ANR 看门狗已启动...");
    }
}