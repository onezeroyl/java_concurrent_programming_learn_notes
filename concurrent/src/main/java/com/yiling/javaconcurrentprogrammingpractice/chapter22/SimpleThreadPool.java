package com.yiling.javaconcurrentprogrammingpractice.chapter22;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author whs
 */
public class SimpleThreadPool {

    private BlockingQueue<Runnable> workQueue;

    private List<RunTask> threadPool;

    public SimpleThreadPool(int poolSize, BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        threadPool = new ArrayList<>();
        for (int i = 0; i < poolSize; i++) {
            RunTask thread = new RunTask();
            thread.start();
            threadPool.add(thread);
        }
    }

    class RunTask extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = workQueue.take();
                    System.out.println("当前执行线程" + Thread.currentThread());
                    task.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void execute(Runnable runnable) {
        workQueue.add(runnable);
    }

    public static void main(String[] args) {
        SimpleThreadPool simpleThreadPool = new SimpleThreadPool(10, new LinkedBlockingQueue<>(20));
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            simpleThreadPool.execute(() -> System.out.println("执行第" + finalI + "次"));
        }
    }
}
