package com.yiling.javaconcurrentprogrammingpractice.chapter23;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author whs
 */
public class MakeTeaExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 60, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(100));
        Future<String> boilWaterFuture = threadPoolExecutor.submit(new TakeTeaTask());
        Future<String> makeTeaFuture = threadPoolExecutor.submit(new MakeTeaTask(boilWaterFuture));
        String value = makeTeaFuture.get();
        System.out.println("获取到：" + value);
    }


    static class MakeTeaTask implements Callable<String> {

        private final Future<String> future;

        public MakeTeaTask(Future<String> future) {
            this.future = future;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(100);
            System.out.println("洗水壶完成");
            Thread.sleep(1500);
            System.out.println("烧开水完成");
            String tea = future.get();
            System.out.println("拿到茶叶：" + tea);
            System.out.println("泡茶");
            return "热气腾腾的茶水~";
        }
    }

    static class TakeTeaTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("洗茶壶完成");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("洗茶杯完成");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("拿茶叶完成");
            return "西湖龙井";
        }

    }

}
