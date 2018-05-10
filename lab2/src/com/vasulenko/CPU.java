package com.vasulenko;

public class CPU extends Thread {

    private static final int COMPLEXITY_COEF = 100;

    private String name;
    private volatile Process currentCpuProcess;

    public CPU(String name) {
        this.name = name;
    }

    public void loadProcess(Process process) {
        this.currentCpuProcess = process;
    }

    @Override
    public void run() {
        while (true) {
            if (this.currentCpuProcess != null) {
                runCurrentProcess();
            }
        }
    }

    private void runCurrentProcess() {
        System.out.printf("Processor [%s] start working on process [%s]...\n", name, currentCpuProcess.getPid());
        try {
            Thread.sleep(currentCpuProcess.getComplexity() * COMPLEXITY_COEF);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.currentCpuProcess = null;
            System.out.printf("Processor [%s] stopped working on process\n", name);
        }
    }

    public boolean isBusy() {
        return currentCpuProcess != null;
    }

}
