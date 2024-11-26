package com.yiling.javaconcurrentprogrammingpractice.chapter14;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author whs
 */
public class ReentrantLockExample {

    private static int value = 0;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        ReentrantLockExample.addOne(lock);
        ReentrantLockExample.anoThread(lock);
    }

    private static void addOne(Lock lock) {
        try {
            lock.lock();
            value += 1;
        } finally {
            lock.unlock();
        }
    }

    private static void anoThread(Lock lock) {
        Thread thread = new Thread(() -> {
            lock.lock();
            System.out.println("加锁成功");
            lock.unlock();
        });
        thread.start();
    }
}
