package com.vasulenko;


public class CPUProcess {
    private long processingTime;
    boolean paused = false;

    public CPUProcess() {
        processingTime = 10 + (long) (Math.random() * 30);
    }

    public void setProcessingTime(long time) {
        if (time > 0) {
            processingTime = time;
        }
    }

    public long getProcessingTime() {
        return processingTime;
    }

    public boolean wasPaused() {
        return paused;
    }

    public void pause() {
        paused = true;
    }

    public void process() {
        --processingTime;
    }
}
