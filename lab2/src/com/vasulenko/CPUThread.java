package com.vasulenko;


import java.util.LinkedList;
import java.util.Queue;

public class CPUThread extends Thread {
    private int id;
    private long generationTime;
    private int processAmount;
    private boolean finished = false;
    private Queue<CPUProcess> queue = new LinkedList<CPUProcess>();

    public CPUThread(int processAmount) {
        this.processAmount = processAmount;
        setGenerationTime(1000);
    }

    public void setGenerationTime(long time) {
        generationTime = time;
    }

    public long getGenerationTime() {
        return generationTime;
    }

    @Override
    public void run() {
        while (!finished) {
            if (--generationTime <= 0) {
                queue.add(new CPUProcess());
                processAmount--;
                setGenerationTime(40 + (long) (Math.random() * 160));
                if (processAmount <= 0)
                    finished = true;
            }

        }
    }

    public CPUProcess getCPUTask() {
        return queue.remove();

    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean isFinished() {
        return finished;
    }

}
