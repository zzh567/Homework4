package com.zzh.monitor_sdk;

import android.util.Log;
import android.view.Choreographer;

public class FluencyMonitor {
    private static final String TAG = "FluencyMonitor";
    private boolean isMonitoring = false;

    private long lastFrameTimeNanos = 0;

    private static final long STANDARD_FRAME_NS = 16_600_000;

    private Choreographer.FrameCallback frameCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            if (lastFrameTimeNanos == 0) {
                lastFrameTimeNanos = frameTimeNanos;
            } else {
                long diffNanos = frameTimeNanos - lastFrameTimeNanos;
                if (diffNanos > STANDARD_FRAME_NS) {
                    long skippedFrames = diffNanos / STANDARD_FRAME_NS;
                    if (skippedFrames > 5) {
                        Log.w(TAG, "检测到卡顿！上两帧间隔: " + (diffNanos / 1000000) + "ms, 丢帧: " + skippedFrames + " 个");
                    }
                }
                lastFrameTimeNanos = frameTimeNanos;
            }

            if (isMonitoring) {
                Choreographer.getInstance().postFrameCallback(this);
            }
        }
    };

    public void start() {
        isMonitoring = true;
        lastFrameTimeNanos = 0;
        Choreographer.getInstance().postFrameCallback(frameCallback);
        Log.d(TAG, "流畅性监控已启动...");
    }
    public void stop() {
        isMonitoring = false;
        Choreographer.getInstance().removeFrameCallback(frameCallback);
    }
}