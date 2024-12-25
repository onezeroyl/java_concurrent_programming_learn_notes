package com.yiling.javaconcurrentprogrammingpractice.chapter30;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author whs
 */
public class SimpleDateFormatTest {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final ThreadLocal<SimpleDateFormat> threadLocalFormat = ThreadLocal.withInitial(
        () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


    static class ParseDate implements Runnable {

        private final int i;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            Date date;
            try {
                date = sdf.parse("2024-12-23 20:12:0" + i % 10);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            System.out.println(i + ":" + date);

        }
    }

    static class SafeParseDate implements Runnable {

        private final int i;

        public SafeParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            Date date;
            try {
                SimpleDateFormat simpleDateFormat = threadLocalFormat.get();
                date = simpleDateFormat.parse("2024-12-23 20:12:0" + i % 10);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            System.out.println(i + ":" + date);

        }
    }

    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 100,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));
        for (int i = 0; i < 1000; i++) {
            threadPoolExecutor.execute(new SafeParseDate(i));
        }
    }

}
