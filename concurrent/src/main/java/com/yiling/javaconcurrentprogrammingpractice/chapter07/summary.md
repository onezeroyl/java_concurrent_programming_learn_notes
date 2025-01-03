# 安全性、活跃性以及性能问题

在前面六章中我们主要讲了并发过程中可能出现的三大问题：可见性、原子性和有序性，从问题的产生原因讲到解决方法
这三个问题是线程并发方面微观的问题，这章要站在宏观层面看并发程序要考虑的三个问题：安全性、活跃性以及性能问题

## 安全性

线程并发安全的必要条件是多个线程同时读写同一共享资源
那么最简单的方法是将这一资源设为不可变的或不共享的
例如我们可以使用`final`修饰变量来让变量不可变
也可以实现ThreadLocal线程本地存储数据来让数据不共享
但实际场景中，共享可变的变量是最多的，所以我们需要使用互斥锁来使同一时间只能有一个线程读或写改变量

## 活跃性

活跃性除了前面章节介绍的死锁外，还可能存在活锁和饥饿问题

- [ ] **活锁问题没太理解**

解决饥饿问题的方案有：

1. 保证资源充足
2. 公平的分配资源
3. 避免持有锁的线程长时间执行

## 性能问题

使用互斥锁修饰的代码块会串行化执行，这和我们并发的目的相悖，所以我们要尽可能的减少性能的损失

1. 使用无锁方案，例如线程本地存储ThreadLocal、写入时复制copy-on-write、乐观锁、CAS等
2. 减少锁的范围，例如使用更细粒度的锁（例如ConcurrentHashMap使用了分段锁的技术）、使用读写锁（读不加锁、写加锁）等