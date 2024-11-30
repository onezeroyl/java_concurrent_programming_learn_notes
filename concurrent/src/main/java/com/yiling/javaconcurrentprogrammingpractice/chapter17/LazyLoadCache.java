package com.yiling.javaconcurrentprogrammingpractice.chapter17;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author whs
 */
public class LazyLoadCache<K, V> {

    private final Map<K, V> map = new HashMap<>();

    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    Lock writeLock = readWriteLock.writeLock();

    Lock readLock = readWriteLock.readLock();

    public static void main(String[] args) {
        LazyLoadCache<Integer, String> cache = new LazyLoadCache<>();
        System.out.println(cache.get(1, "hello"));

    }

    public V get(K k, V defaultValue) {
        V v;
        readLock.lock();
        try {
            v = map.get(k);
        } finally {
            readLock.unlock();
        }
        if (v != null) {
            return v;
        }
        writeLock.lock();
        try {
            // 在获取写锁后需再次查询判断缓存是否有值，防止在获取写锁阻塞期间缓存已经被加载
            v = map.get(k);
            if (v != null) {
                return v;
            }
            map.put(k, defaultValue);
            return defaultValue;
        } finally {
            writeLock.unlock();
        }
    }

}
