package com.yiling.javaconcurrentprogrammingpractice.chapter21;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author whs
 */
public class AtomicLongExample {

    public static void main(String[] args) throws InterruptedException {

        AtomicLong atomicLong = new AtomicLong(0L);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 20000; i++) {
                atomicLong.incrementAndGet();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 20000; i++) {
                atomicLong.incrementAndGet();
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(atomicLong.get());

    }

}
