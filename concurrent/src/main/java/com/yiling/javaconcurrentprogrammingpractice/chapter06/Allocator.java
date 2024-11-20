package com.yiling.javaconcurrentprogrammingpractice.chapter06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author whs
 */
public class Allocator {

    private static List<Object> lockList = new ArrayList<>();

    public synchronized void apply(Object... locks) throws InterruptedException {
        for (Object lock : locks) {
            if (lockList.contains(lock)) {
                System.out.println(lock.toString() + "已被占用, 进入等待队列");
                wait();
            }
        }
        lockList.addAll(Arrays.asList(locks));
    }

    public synchronized void release(Object... locks) {
        lockList.removeAll(Arrays.asList(locks));
        System.out.println("唤醒等待队列中的所有线程");
        notifyAll();
    }

}
