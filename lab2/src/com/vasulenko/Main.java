package com.vasulenko;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int PROCESS_COUNT = 100;
        int QUEUE_SIZE = 5;
        CPU cpuOne = new CPU("cpu_1");
        CPU cpuTwo = new CPU("cpu_2");
        CPUQueue cpuQueueOne = new CPUQueue(3);
        CPUQueue cpuQueueTwo = new CPUQueue(3);
        OS os = new OS(cpuOne, cpuTwo, cpuQueueOne, cpuQueueTwo);
        CPUProcess cpuProcess = new CPUProcess(os, 500, PROCESS_COUNT);

        cpuOne.start();
        cpuTwo.start();
        cpuProcess.start();
        os.init();

        cpuProcess.join();
    }
}
