# Java内存模型：看Java如何解决可见性和有序性的问题

> 一句话总结：Java通过Java内存模型限制缓存和编译优化来解决可见性和有序性的问题

可见性和有序性是因为CPU缓存和编译优化导致的，所以解决问题的方法就是禁止缓存和编译优化，Java内存模型就规范了JVM提供给开发者禁止缓存和编译优化的方法

## volatile关键字

volatile关键字的作用是告诉编译器不使用缓存，直接访问内存中的数据，这样就解决了可见性的问题

## Happens-Before规则

> Happens-Before规则指的是：前面一个操作对后续操作是可见的

Happens-Before规则约束了编译器的优化行为，要求编译器优化后一定遵守Happens-Before规则
1. 程序的顺序性规则
在一个线程中，前面的操作Happens-Before与后续的操作
2. volatile变量规则
对一个volatile变量的写操作，Happens-Before于后续对这个变量的读操作
3. 传递规则
如果A Happens-Before B，且B Happens-Before C，那么A Happens-Before C
4. 管程中锁的规则
一个锁的解锁 Happens-Before 于后续对这个锁的加锁
5. 线程start()规则
主线程A启动子线程B，那么子线程B能够看到主线程A在启动线程B之前的操作
6. 线程join规则
线程A调用了线程B的join()方法，则线程B中的操作对join()方法后的线程A的操作是可见的
