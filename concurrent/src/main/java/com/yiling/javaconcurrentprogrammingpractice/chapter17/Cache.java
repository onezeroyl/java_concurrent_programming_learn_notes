package com.yiling.javaconcurrentprogrammingpractice.chapter17;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author whs
 */
public class Cache<K, V> {

    private final Map<K, V> map = new HashMap<>();

    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    Lock writeLock = readWriteLock.writeLock();

    Lock readLock = readWriteLock.readLock();

    public static void main(String[] args) throws InterruptedException {

        Cache<Integer, Integer> cache = new Cache<>();

        new Thread(() -> {
            cache.put(1, 2);
            cache.put(2, 3);
        }).start();

        Thread.sleep(100);

        new Thread(() -> {
            cache.get(1);
        }).start();

        new Thread(() -> {
            cache.get(2);
        }).start();

        Thread.sleep(100);

        new Thread(() -> {
            cache.put(3, 4);
        }).start();


    }

    private void put(K k, V v) {
        writeLock.lock();
        try {
            System.out.println("存放 K:" + k + ", V:" + v);
            map.put(k, v);
        } finally {
            writeLock.unlock();
        }
    }

    private V get(K k) {
        readLock.lock();
        try {
            Thread.sleep(10000);
            System.out.println("获取 k:" + k);
            return map.get(k);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            readLock.unlock();
        }
    }

}
