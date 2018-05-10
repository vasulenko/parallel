package com.vasulenko;

import java.util.Random;

public class CPUProcess extends Thread {

    private OS os;
    private long delay;
    private int processCount;

    public CPUProcess(OS os, long delay, int processCount) {
        this.os = os;
        this.processCount = processCount;
        this.delay = delay;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (processCount > 0) {
            Process process = new Process(10 + random.nextInt(90));
            os.coordinateProcess(process);
            try {
                Thread.sleep(delay + random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            processCount -= 1;
        }
    }
}
