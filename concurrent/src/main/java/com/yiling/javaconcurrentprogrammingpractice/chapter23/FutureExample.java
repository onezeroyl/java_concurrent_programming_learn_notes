package com.yiling.javaconcurrentprogrammingpractice.chapter23;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author whs
 */
public class FutureExample {

    private static final AtomicInteger threadNumber = new AtomicInteger(1);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 60, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(10),
            r -> new Thread(r, "FutureExample-thread-" + threadNumber.getAndIncrement()),
            new AbortPolicy());
        Future<?> runnableExampleTask = threadPoolExecutor.submit(new RunnableExample());
        Object runnableResult = runnableExampleTask.get();
        System.out.println("RunnableExample 执行结果为：" + runnableResult);
        Student student = new Student();
        Future<?> returnValueRunnableExampleTask = threadPoolExecutor.submit(
            new ReturnValueRunnableExample(student), student);
        System.out.println("ReturnValueRunnableExample 执行前：" + student);
        Object returnValueRunnableResult = returnValueRunnableExampleTask.get();
        System.out.println("ReturnValueRunnableExample 执行后：" + student);
        System.out.println("ReturnValueRunnableExample 执行结果为：" + returnValueRunnableResult);
        Future<String> callableExampleFuture = threadPoolExecutor.submit(new CallableExample());
        String callResult = callableExampleFuture.get();
        System.out.println("CallableExample 执行结果为：" + callResult);
    }

    static class RunnableExample implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("RunnableExample 执行完成");
        }
    }

    static class ReturnValueRunnableExample implements Runnable {

        private final Student student;

        public ReturnValueRunnableExample(Student student) {
            this.student = student;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            student.setId(1L);
            student.setName("张三");
            System.out.println("ReturnValueRunnableExample 执行完成");
        }
    }

    static class CallableExample implements Callable<String> {

        @Override
        public String call() throws Exception {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("ReturnValueRunnableExample 执行完成");
            return "成功啦!";
        }
    }

}
