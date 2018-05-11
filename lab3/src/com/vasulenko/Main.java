package com.vasulenko;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        int[] array = new int[1000000];
        Arrays.fill(array, 1);

        System.out.println(countForPredicate(array, i -> i > 0));
        System.out.println(xorSum(array));
    }

    public static int countForPredicate(int[] array, IntPredicate predicate) {
        AtomicInteger atomicCounter = new AtomicInteger();
        IntStream.of(array).parallel()
                .forEach(i -> {
                    if (predicate.test(i)) {
                        int countValue;
                        do {
                            countValue = atomicCounter.get();
                        } while (!atomicCounter.compareAndSet(countValue, countValue + 1));
                    }
                });
        return atomicCounter.get(); //IntStream.of(array).parallel().filter(predicate).count();
    }

    public static int xorSum(int[] array) {
        AtomicInteger xorSum = new AtomicInteger();
        IntStream.of(array).parallel()
                .forEach(i -> {
                    int xorSumValue;
                    do {
                        xorSumValue = xorSum.get();
                    } while (!xorSum.compareAndSet(xorSumValue, xorSumValue ^ i));
                });
        return xorSum.get();
    }

}
