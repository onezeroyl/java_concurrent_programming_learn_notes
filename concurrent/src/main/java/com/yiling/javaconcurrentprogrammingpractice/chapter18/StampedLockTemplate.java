package com.yiling.javaconcurrentprogrammingpractice.chapter18;

import java.util.concurrent.locks.StampedLock;

/**
 * @author whs
 */
public class StampedLockTemplate {

    private Integer i = 1;

    private final StampedLock stampedLock = new StampedLock();

    public static void main(String[] args) {
        StampedLockTemplate stampedLockTemplate = new StampedLockTemplate();
        new Thread(() -> {
            try {
                stampedLockTemplate.out();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(stampedLockTemplate::writeLock).start();
    }

    public void writeLock() {
        long stamp = stampedLock.writeLock();
        i += 1;
        stampedLock.unlock(stamp);
        System.out.println("更新后的值：" + i);
    }

    public void out() throws InterruptedException {
        // 获取乐观读stamp
        long stamp = stampedLock.tryOptimisticRead();
        Thread.sleep(1000);
        // 校验在获取乐观读到此时是否有线程获取到writeLock
        if (stampedLock.validate(stamp)) {
            System.out.println("乐观读获取到的值：" + i);
        } else {
            // 被修改后使用悲观读锁进行计算
            long readLockStamp = stampedLock.readLock();
            System.out.println("悲观读锁获取到的值：" + i);
            stampedLock.unlock(readLockStamp);
        }


    }

}
