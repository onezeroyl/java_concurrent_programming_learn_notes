package com.yiling.javaconcurrentprogrammingpractice.chapter18;

import java.util.concurrent.locks.StampedLock;

/**
 * @author whs
 */
public class StampedLockExample {


    public static void main(String[] args) throws InterruptedException {
        StampedLock stampedLock = new StampedLock();
        new Thread(() -> {
            long l = stampedLock.readLock();
            System.out.println("1获取到stamp：" + l);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            stampedLock.unlock(l);
        }).start();

        new Thread(() -> {
            long l = stampedLock.readLock();
            System.out.println("2获取到stamp：" + l);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            stampedLock.unlock(l);
        }).start();

        new Thread(() -> {
            long l = stampedLock.tryOptimisticRead();
            System.out.println("乐观读1获取到stamp：" + l);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(stampedLock.validate(l));
        }).start();

        new Thread(() -> {
            long l = stampedLock.writeLock();
            System.out.println("write获取到stamp：" + l);
            stampedLock.unlock(l);
        }).start();

        Thread.sleep(1000);
        new Thread(() -> {
            long l = stampedLock.tryOptimisticRead();
            System.out.println("乐观读2获取到stamp：" + l);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(stampedLock.validate(l));
        }).start();

    }

}
