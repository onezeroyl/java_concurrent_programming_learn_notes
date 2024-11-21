package com.yiling.javaconcurrentprogrammingpractice.chapter08;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author whs
 */
public class BlockedQueue {

    // 创建锁
    final Lock lock = new ReentrantLock();

    // 创建条件变量
    final Condition notFull = lock.newCondition();

    // 创建条件变量
    final Condition notEmpty = lock.newCondition();

    Queue<Integer> queue = new LinkedList<>();

    Integer queueMaxSize;

    public BlockedQueue(Integer maxSize) {
        this.queueMaxSize = maxSize;
    }

    public void entry(Integer i) throws InterruptedException {
        lock.lock();
        System.out.println("entry start:" + i);
        try {
            while (queue.size() == queueMaxSize) {
                notFull.await();
            }
            System.out.println("queue add:" + i);
            queue.add(i);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void out() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            queue.remove();
            notFull.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockedQueue queue = new BlockedQueue(1);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                queue.out();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        for (int i = 0; i < 2; i++) {
            queue.entry(i);
        }


    }

}
