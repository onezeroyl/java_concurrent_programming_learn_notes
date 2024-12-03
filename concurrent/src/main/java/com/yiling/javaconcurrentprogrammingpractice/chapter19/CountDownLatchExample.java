package com.yiling.javaconcurrentprogrammingpractice.chapter19;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author whs
 */
public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {

        Executor executor = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        executor.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(new Date() + "执行第一步");
            countDownLatch.countDown();
        });
        executor.execute(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(new Date() + "执行第一步");
            countDownLatch.countDown();
        });

        countDownLatch.await();

        System.out.println(new Date() + "执行第一步");
        System.out.println(new Date() + "执行第一步");

    }

}
