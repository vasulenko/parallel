package com.vasulenko;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CPUQueue {

    private int maxSize;
    private List<Process> list;

    public CPUQueue(int maxSize) {
        this.maxSize = maxSize;
        this.list = new LinkedList();
    }

    public boolean push(Process process) {
        return this.list.add(process);
    }

    public Optional<Process> pop() {
        if (list.isEmpty()) {
            return Optional.empty();
        }
        Process process = list.get(0);
        list.remove(0);
        return Optional.ofNullable(process);
    }

    public boolean isFull() {
        return this.list.size() == maxSize;
    }


    public int size() {
        return this.list.size();
    }
}
