package com.yiling;

import com.yiling.blockedqueue.BlockedQueue;

public class Main {

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