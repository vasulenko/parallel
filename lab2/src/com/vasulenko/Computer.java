package com.vasulenko;


public class Computer {
    CPUThread thread;
    CPUQueue [] queues;
    CPU [] processors;

    static int PROCESS_COUNT = 5000; //the best = PROCESSOR_COUNT*(QUEUE_SIZE+1), everyone gets equal amount of processes
    static int PROCESSOR_COUNT = 8;
    static int QUEUE_SIZE = 200;

    public void process() throws InterruptedException{

        boolean PROCESSING_END = false;

        thread = new CPUThread(PROCESS_COUNT);
        queues = new CPUQueue[PROCESSOR_COUNT];
        processors = new CPU[PROCESSOR_COUNT];

        int [] queueMaxLengthes = new int[PROCESSOR_COUNT];
        int [] CPUStats = new int[PROCESSOR_COUNT];

        for (int i = 0; i < processors.length; i++) {
            processors[i] = new CPU();
            processors[i].start();
        }
        for (int i = 0; i < queues.length; i++) {
            queues[i] = new CPUQueue();
        }
        thread.start();

        while(!thread.isFinished() || !PROCESSING_END){
            for(int i = 0; i < queues.length; i++) {
                if (queues[i].getLength() > queueMaxLengthes[i]) {
                    queueMaxLengthes[i] = queues[i].getLength();
                }
            }
            for(int i = 0; i < processors.length; i++)
                if(processors[i].getTask().getProcessingTime() <= 0 && !queues[i].isEmpty()){
                    System.out.println("DEBUG: cpu #"+(i+1)+" gets a process from its queue");
                    CPUStats[i]++;
                    processors[i].setTask(queues[i].get());
            }

            while(!thread.isEmpty()){
                for(int i = 0; i < processors.length; i++) {
                   if(processors[i].getTask().getProcessingTime() <= 0) {
                       System.out.println("DEBUG: CPU #" + (i+1) + " GETS A PROCESS");
                       CPUStats[i]++;
                       processors[i].setTask(thread.getCPUTask());
                       break;
                   }
                   else
                       if(queues[i].getLength() < QUEUE_SIZE){
                           System.out.println("DEBUG: QUEUE #" + (i+1) + " GETS A PROCESS");
                           queues[i].add(thread.getCPUTask());
                           break;
                       }
                }
            }
            if(thread.isFinished()) {
                PROCESSING_END = true;
                for (int i = 0; i < processors.length; i++) {
                    if (processors[i].getTask().getProcessingTime() > 0
                            || !queues[i].isEmpty()) {
                        PROCESSING_END = false;
                        break;
                    }
                }
            }

            }
        for (int i = 0; i < PROCESSOR_COUNT; i++) {
                processors[i].finish();
                System.out.println("CPU №" + (i + 1));
                System.out.println("Оброблено " + CPUStats[i] * 100.0 / PROCESS_COUNT + "% процесів");
                System.out.println("Максимальна довжина черги = " + queueMaxLengthes[i] + " процесів");
        }


    }
}
