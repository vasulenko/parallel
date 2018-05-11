package com.vasulenko;


import java.util.LinkedList;
import java.util.Queue;

public class CPUQueue {
    private Queue<CPUProcess> queue = new LinkedList<CPUProcess>();

    public void add(CPUProcess cpu) {
        if (getLength() < Computer.QUEUE_SIZE)
            queue.add(cpu);
    }

    public CPUProcess get() {
        return queue.remove();
    }

    public int getLength() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}