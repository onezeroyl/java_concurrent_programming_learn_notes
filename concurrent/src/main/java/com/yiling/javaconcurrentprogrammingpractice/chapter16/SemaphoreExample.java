package com.yiling.javaconcurrentprogrammingpractice.chapter16;

import java.util.concurrent.Semaphore;

/**
 * @author whs
 */
public class SemaphoreExample {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(1);
        down(semaphore, "one");
        down(semaphore, "two");
    }

    private static void down(Semaphore semaphore, String str) {
        Thread thread = new Thread(() -> {
            try {
                semaphore.acquire();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                semaphore.release();
            }
            System.out.println(str);
        });
        thread.start();
    }

}
