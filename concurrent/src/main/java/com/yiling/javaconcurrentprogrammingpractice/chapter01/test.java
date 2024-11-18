package com.yiling.javaconcurrentprogrammingpractice.chapter01;

/**
 * @author whs
 */
public class test {
    private static volatile int a = 0, b = 0; // 共享变量
    private static volatile int x = 0, y = 0; // 结果存储

    public static void main(String[] args) throws InterruptedException {
        int iteration = 0;

        while (true) {
            iteration++;
            a = 0;
            b = 0;
            x = 0;
            y = 0;

            // 线程1：写a后读b
            Thread t1 = new Thread(() -> {
                a = 1; // 写操作
                x = b; // 读操作
            });

            // 线程2：写b后读a
            Thread t2 = new Thread(() -> {
                b = 1; // 写操作
                y = a; // 读操作
            });

            // 启动线程
            t1.start();
            t2.start();

            // 等待线程完成
            t1.join();
            t2.join();

            // 检查结果
            if (x == 0 && y == 0) {
                System.out.println("第" + iteration + "次，发现有序性问题：x = " + x + ", y = " + y);
                break;
            }
        }
    }
}

