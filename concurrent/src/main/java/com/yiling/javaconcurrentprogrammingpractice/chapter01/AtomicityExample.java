package com.yiling.javaconcurrentprogrammingpractice.chapter01;

/**
 * 多线程原子性问题举例
 *
 * @author whs
 */
public class AtomicityExample {

    private static volatile long count = 0;

    private static void add() {
        for (int j = 0; j < 10000; j++) {
            count += 1;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new addTask());
        Thread thread2 = new Thread(new addTask());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(count + "'");
    }

    static class addTask implements Runnable {

        @Override
        public void run() {
            AtomicityExample.add();
        }
    }
}
