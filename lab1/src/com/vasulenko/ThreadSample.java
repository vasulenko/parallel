package com.vasulenko;

import java.util.Random;

public class ThreadSample {
    public static int SIZE = 200000;
    public static int NUMBER_OF_JOBS = 3;

    public static void main(String[] args) throws InterruptedException {

        double vectA[] = generateVector(SIZE);
        double vectB[] = generateVector(SIZE);

        long startTimeSync = System.currentTimeMillis();
        double serialResult = 0;
        for (int i = 0; i < SIZE; i++) {
            serialResult += vectA[i] * vectB[i];
        }

        long durationSync = System.currentTimeMillis() - startTimeSync;
        System.out.println(serialResult);
        System.out.println("Done! Execution took: " + durationSync + "ms");
        ThreadCacl TreadArrray[] = new ThreadCacl[NUMBER_OF_JOBS];
        long startTimeAsync = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_JOBS; i++) {

            TreadArrray[i] = new ThreadCacl(vectA, vectB,
                    SIZE / NUMBER_OF_JOBS * i,
                    i == NUMBER_OF_JOBS - 1 ? SIZE : SIZE / NUMBER_OF_JOBS * (i + 1));
            TreadArrray[i].start();
        }
        for (int i = 0; i < NUMBER_OF_JOBS; i++) {
            TreadArrray[i].join();
        }
        double parallelResult = 0;
        for (int i = 0; i < NUMBER_OF_JOBS; i++) {
            parallelResult += TreadArrray[i].getResult();
        }
        long durationAsync = System.currentTimeMillis() - startTimeAsync;
        System.out.println(parallelResult);
        System.out.println("Done! Execution took: " + durationAsync + "ms");
    }

    private static double[] generateVector(int size) {
        System.out.println("Started generating vector..");
        double[] vector = new double[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            vector[i] = random.nextDouble();
        }
        System.out.println("Finished!");
        return vector;
    }
}
