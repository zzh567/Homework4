package com.zzh.monitor_sdk;

import android.util.Log;

public class MonitorManager {
    private static final String TAG = "PerformanceManager";
    private static final MonitorManager instance = new MonitorManager();

    private FluencyMonitor fluencyMonitor;
    private AnrMonitor anrMonitor;
    private MonitorManager() {
        fluencyMonitor = new FluencyMonitor();
        anrMonitor = new AnrMonitor();
    }
    public static MonitorManager getInstance() {
        return instance;
    }
    public void startMonitor() {
        fluencyMonitor.start();
        anrMonitor.startMonitor();
        Log.d(TAG, "流畅性、ANR监控启动完成！");
    }
    public void stopMonitor() {
        fluencyMonitor.stop();
        // anrMonitor 目前没有写 stop，实际开发中建议加上
        Log.d(TAG, "性能监控 SDK 已停止。");
    }
}
