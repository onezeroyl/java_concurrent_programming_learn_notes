package com.yiling.javaconcurrentprogrammingpractice.chapter01;

/**
 * @author whs
 */
public class VisibilityExample {

    // 共享变量，没有使用volatile或其他同步机制
    private static volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        // 写线程：在1秒后将flag设为true
        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(1000);
                flag = true;
                System.out.println("写线程：flag已设为true");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 读线程：持续检查flag的值
        Thread reader = new Thread(() -> {
            System.out.println("读线程：开始监视flag...");
            while (!flag) {
                // boolean h = flag;
                // 忙等待
                // System.out.println("我检测不到啊");
            }
            System.out.println("读线程：检测到flag为true");
        });

        // 启动两个线程
        writer.start();
        reader.start();

        // 等待两个线程结束
        writer.join();
        reader.join();

        System.out.println("主线程：程序结束");
    }
}

