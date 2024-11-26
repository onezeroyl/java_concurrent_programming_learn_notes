package com.yiling.javaconcurrentprogrammingpractice.chatper14;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author whs
 */
public class LockExample {


    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        LockExample.lock(lock);
        Thread.sleep(100);
        // Lock接口提供的三种非阻塞式获取锁的方法示例
        LockExample.lockInterruptiblyTest(lock);
        LockExample.tryLockTest(lock);
        LockExample.tryLockTimedTest(lock);
    }

    private static void lock(Lock lock) {
        Thread lockThread = new Thread(() -> {
            System.out.println("lock");
            lock.lock();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("unlock");
            lock.unlock();
        });
        lockThread.start();
    }

    private static void tryLockTimedTest(Lock lock) {
        Thread tryLockThread = new Thread(() -> {
            try {
                if (lock.tryLock(15000, TimeUnit.MILLISECONDS)) {
                    System.out.println("tryLockTimed Thread lock success");
                } else {
                    System.out.println("tryLockTimed Thread lock fail");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        tryLockThread.start();
    }

    private static void tryLockTest(Lock lock) {

        Thread tryLockThread = new Thread(() -> {
            if (lock.tryLock()) {
                System.out.println("tryLock Thread lock success");
            } else {
                System.out.println("tryLock Thread lock fail");
            }
        });
        tryLockThread.start();
    }

    private static void lockInterruptiblyTest(Lock lock) throws InterruptedException {

        Thread lockInterruptiblyThread = new Thread(() -> {
            try {
                System.out.println("lockInterruptibly");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
                throw new RuntimeException(e);
            }
        });
        lockInterruptiblyThread.start();
        lockInterruptiblyThread.interrupt();
    }

}
