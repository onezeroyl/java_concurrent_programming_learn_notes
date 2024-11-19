package com.yiling.javaconcurrentprogrammingpractice.chapter05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author whs
 */
public class Allocator {

    private static List<Object> lockList = new ArrayList<>();

    public synchronized boolean apply(Object... locks) {
        for (Object lock : locks) {
            if (lockList.contains(lock)) {
                return false;
            }
        }
        lockList.addAll(Arrays.asList(locks));
        return true;
    }

    public synchronized void release(Object... locks) {
        lockList.removeAll(Arrays.asList(locks));
    }

}
