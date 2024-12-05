package com.yiling.javaconcurrentprogrammingpractice.chapter21;

import java.util.concurrent.atomic.AtomicReference;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author whs
 */
public class AtomicReferenceExample {

    public static void main(String[] args) {
        AtomicReference<Student> reference = new AtomicReference<>();
        Student student = new Student(1L, "张三");
        reference.set(student);

        new Thread(() -> {
            Student oldStudent = reference.get();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Student newStudent = new Student(2L, "李四");
            reference.compareAndSet(oldStudent, newStudent);
            System.out.println("student = " + reference.get());
        }).start();

        new Thread(() -> {
            student.setName("王二");
        }).start();

    }


    @Data
    @AllArgsConstructor
    static class Student {

        private Long id;

        private String name;
    }

}
