package com.vasulenko;

public class OS {

    private CPU cpuOne;
    private CPU cpuTwo;
    private CPUQueue cpuQueueOne;
    private CPUQueue cpuQueueTwo;
    private final Object lock = new Object();

    public OS(CPU cpuOne, CPU cpuTwo, CPUQueue cpuQueueOne, CPUQueue cpuQueueTwo) {
        this.cpuOne = cpuOne;
        this.cpuTwo = cpuTwo;
        this.cpuQueueOne = cpuQueueOne;
        this.cpuQueueTwo = cpuQueueTwo;
    }

    public void init() {
        queueWorkerThread.start();
    }

    public void coordinateProcess(Process process) {
        boolean loaded = false;
        synchronized (lock) {

            if (!loaded && !cpuOne.isBusy()) {
                cpuOne.loadProcess(process);
                loaded = true;
            }
            if (!loaded && !cpuTwo.isBusy()) {
                cpuTwo.loadProcess(process);
                loaded = true;

            }

            if (!loaded && !cpuQueueOne.isFull() && !cpuQueueTwo.isFull()) {
                CPUQueue queue = cpuQueueOne.size() <= cpuQueueTwo.size()
                        ? cpuQueueOne
                        : cpuQueueTwo;
                queue.push(process);
                System.out.printf("Process [%s] pushed to queue\n", process.getPid());
                loaded = true;
            }
            if (!loaded && !cpuQueueOne.isFull()) {
                cpuQueueOne.push(process);
                System.out.printf("Process [%s] pushed to queue\n", process.getPid());
                loaded = true;
            }
            if (!loaded && !cpuQueueTwo.isFull()) {
                cpuQueueTwo.push(process);
                System.out.printf("Process [%s] pushed to queue\n", process.getPid());
                loaded = true;
            }
            if (!loaded) {
                System.out.printf("Process [%s] skipped!\n", process.getPid());
            }

        }
    }

    private Thread queueWorkerThread = new Thread(() -> {
        while (true) {
            synchronized (lock) {
                if (!cpuOne.isBusy()) {
                    cpuQueueOne.pop().ifPresent(cpuOne::loadProcess);
                }
                if (!cpuTwo.isBusy()) {
                    cpuQueueTwo.pop().ifPresent(cpuTwo::loadProcess);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
}
