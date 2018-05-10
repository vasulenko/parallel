package com.vasulenko;

public class Process {
    private static int nextPid = 0;

    private int pid;
    private int complexity;

    public Process(int complexity) {
        this.complexity = complexity;
        this.pid = nextPid++;
    }

    public int getPid() {
        return pid;
    }

    public int getComplexity() {
        return complexity;
    }
}
