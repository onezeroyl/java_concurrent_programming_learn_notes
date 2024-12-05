package com.yiling.javaconcurrentprogrammingpractice.chapter21;

import com.yiling.javaconcurrentprogrammingpractice.chapter21.AtomicReferenceExample.Student;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author whs
 */
public class AtomicStampedReferenceExample {

    public static void main(String[] args) {

        AtomicStampedReference<Student> atomicStampedReference = new AtomicStampedReference<>(
            new Student(1L, "张三"), 1);
        int[] stampedArray = new int[1];
        Student student = atomicStampedReference.get(stampedArray);
        System.out.println(stampedArray[0]);
        Student newStudent = new Student(2L, "李四");
        boolean b = atomicStampedReference.compareAndSet(student, newStudent, stampedArray[0], 2);
        System.out.println(b);
        Student student1 = atomicStampedReference.get(stampedArray);
        System.out.println(stampedArray[0]);
        System.out.println(student1);

    }

}
