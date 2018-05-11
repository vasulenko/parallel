package com.vasulenko;


public class CPU extends Thread {
    CPUProcess task;

    public CPU() {
        task = new CPUProcess();
    }

    public void setTask(CPUProcess task) {
        this.task = task;
    }

    public CPUProcess getTask() {
        return task;
    }

    @Override
    public void run() {
        while (!this.isInterrupted())
            task.process();
    }

    public void finish() {
        this.interrupt();
    }

}

