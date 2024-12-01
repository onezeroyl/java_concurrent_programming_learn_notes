package com.yiling.javaconcurrentprogrammingpractice.chapter18;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

/**
 * @author whs
 */
public class InterruptTest {


    public static void main(String[] args) throws InterruptedException {
        StampedLock stampedLock = new StampedLock();
        new Thread(() -> {
            long stamp = stampedLock.readLock();
            System.out.println("读锁获取到stamp：" + stamp);
            LockSupport.park();
        }).start();
        Thread.sleep(1000);
        Thread thread = new Thread(() -> {
            long stamp = stampedLock.writeLock();
            System.out.println("写锁获取到stamp：" + stamp);
        });
        thread.start();
        Thread.sleep(1000);
        System.out.println("调用写锁的interrupt方法");
        thread.interrupt();

    }

}
