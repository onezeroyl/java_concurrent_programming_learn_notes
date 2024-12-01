# StampedLock：有没有比读写锁更快的锁

读写锁在在读多写少的场景下，通过降低锁粒度优化了性能。那还有什么能比低粒度锁更快呢
那就是无锁！
JDK1.8里新增了StampedLock类，支持写锁，悲观读锁和乐观读

## StampedLock支持的三种锁模式

StampedLock中的悲观读锁和写锁与ReadWriteLock中的读写锁是一样的，都是同一时间可以有多个线程获取读锁，但只能有一个线程获取写锁，且读锁和写锁是互斥的
但是用法是有区别的，StampedLock的`readLock()`和`writeLock()`
方法会返回一个Long类型的stamp，在使用解锁方法`unlock(Long stamp)`时要将该值作为参数传递进去

而乐观读这个操作是无锁的，他类似于数据库中的乐观读，例如我们可以根据新增version字段或数据行的更新时间来判断在我们查询-更新的这段时间内该数据是否已被更新

三种模式的基本操作见本包下`StampedLockExample.java`

StampedLock中的读操作和写操作模板见本包下`StampedLockTemplate.java`

## StampedLock使用过程中的注意事项

StampedLock的性能比ReadWriteLock的性能要好，那就肯定有不足的地方这样ReadWriteLock才有存在的必要

1. StampedLock是不可重入的
2. StampedLock中的悲观锁和写锁都不支持条件变量Condition
3. 如果线程在获取悲观读锁或写锁阻塞时被调用`interrupt()`
   方法，会使CPU飙升至100%（相关代码见本包下`InterruptTest.java`，但是不知为何运行之后观察任务管理器发现CPU使用率正常的）

StampedLock提供了支持中断的获取读锁`readLockInterruptibly()`
.java方法和支持中断的获取写锁`writeLockInterruptibly()`方法
