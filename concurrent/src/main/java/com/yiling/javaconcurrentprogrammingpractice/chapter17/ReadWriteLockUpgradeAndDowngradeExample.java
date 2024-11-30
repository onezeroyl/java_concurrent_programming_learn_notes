package com.yiling.javaconcurrentprogrammingpractice.chapter17;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author whs
 */
public class ReadWriteLockUpgradeAndDowngradeExample {

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Lock readLock = readWriteLock.readLock();

    private final Lock writeLock = readWriteLock.writeLock();

    public static void main(String[] args) {
        ReadWriteLockUpgradeAndDowngradeExample example = new ReadWriteLockUpgradeAndDowngradeExample();
        example.downgrade();
        // example.upgrade();
    }

    public void upgrade() {
        readLock.lock();
        System.out.println("读锁加锁成功");
        writeLock.lock();
        System.out.println("写锁加锁成功");
        writeLock.unlock();
        System.out.println("写锁释放成功");
        readLock.unlock();
        System.out.println("读锁释放成功");
    }

    public void downgrade() {
        writeLock.lock();
        System.out.println("写锁加锁成功");
        readLock.lock();
        System.out.println("读锁加锁成功");
        readLock.unlock();
        System.out.println("读锁释放成功");
        writeLock.unlock();
        System.out.println("写锁释放成功");
    }

}


