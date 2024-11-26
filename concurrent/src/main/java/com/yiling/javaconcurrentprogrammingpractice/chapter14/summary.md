# Lock和Condition（上）：隐藏在并发包中的管程

Java并发编程有两大核心问题：互斥和同步
Java SDK并发包通过Lock和Condition两个接口来解决这两个问题
其中Lock接口解决互斥的问题
Condition接口解决同步的问题

## 再造管程的理由

已经有了synchronized关键字了，那Lock存在的意义是什么？
在第五章讲死锁的时我们讲到，可以通过破坏“不可抢占”条件来避免死锁，而synchronized没提供对应的功能，它在获取不到锁的时候会一直阻塞知道获取锁
而Lock接口除了常规的加锁解锁的操作外还定义了下面三个方法：

```java
// 该方法支持在获取锁失败阻塞的过程中监听到interrupt状态变化，当该线程被标记为interrupt状态时抛出InterruptedException

void lockInterruptibly() throws InterruptedException;

// 该方法尝试加锁，成功返回true，失败返回false
boolean tryLock();

// 该方法尝试加锁，如果在指定的时间内加锁成功返回true，如果加锁失败返回false
// 在等待期间中断会抛出InterruptedException异常
boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
```

这三个方法对应三方方案来解决破坏不可抢占条件：

1. `lockInterruptibly()`方法支持中断
2. `tryLock()`支持非阻塞式获取锁
3. `tryLock(long time, TimeUnit unit)`支持获取锁超时

另外除了上述原因外，前文中还提到synchronized中同步操作只能通过`wait()`,`notify()`,`notifyAll()`
三个方法来实现
这三个方法本质上对应的是一个条件变量，而Lock中定义了`newCondition()`方法支持我们在锁中创建多个条件变量，同步的方案更丰富

## Lock如何保证可见性

Lock使用的范式可以概括为`try{}finally{}`，释放锁的操作放在finally代码块中可以保证程序正常异常都能释放锁
下面代码我们使用Lock来保证对value+1的操作是互斥的

```java
class X {

    private final Lock rtl =
        new ReentrantLock();
    int value;

    public void addOne() {
        // 获取锁
        rtl.lock();
        try {
            value += 1;
        } finally {
            // 保证锁能释放
            rtl.unlock();
        }
    }
}
```

如果两个线程执行该方法，前一个线程的操作更新的value值对后一个线程是可见的吗？
如果是`synchronized`
关键字加锁我们很容易理解是可见的，因为Happens-Before规则里定义了Synchronized的解锁操作Happens-Before于后续synchronized的加锁操作
但是Happens-Before里并没有定义Lock加锁解锁的可见性规则，那Lock怎么实现类似于synchronized关键字的效果呢
其实Lock接口的实现类里面都间接或直接定义了一个volatile修饰的stat变量，获取锁的时候会读写stat的值，释放锁的时候也会读写stat的值
所以根据Happens-Before规则来看：

1. 同个线程顺序性规则指出`value+1`的操作Happens-Before于解锁操作
2. volatile规则指出解锁操作Happens_Before于后续的加锁操作
3. 传递性规则可以得出`value+1`的操作Happens-Before于后续的加锁操作

所以前一个线程更新的value值对后续线程是可见的

## 可重入锁

> 见本包中代码：ReentrantLockExample.java

Lock最常用的实现类是ReentrantLock，翻译过来就是：可重入锁
可重入锁就是持有锁的线程可以重复获取锁，要注意：一定要根据范式加锁和释放锁成对存在
可重入函数就是支持多个线程同时调用该函数，能保证线程安全

## 公平锁与非公平锁

公平锁即唤醒策略是谁等待时间长唤醒谁，可以视为先进先出的队列
非公平锁不能保证公平性，可能等待时间段的先被唤醒
ReentrantLock类定义了两个构造函数，如下：

```java
public ReentrantLock() {
    sync = new NonfairSync();
}

public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}
```

根据ReentrantLock的构造函数可以看出，空参构造函数会创建非公平锁，想要创建公平锁可以调用有参构造函数

## 用锁的最佳实践

1. 永远只在更新对象的成员变量时加锁
2. 永远只在访问可变的成员变量时加锁
3. 永远不在调用其他对象的方法时加锁

为什么不在调用其他对象的方法时加锁，因为我们无法感知到其他对象方法中会做什么操作，如果也需要获取锁那么可能会导致死锁，如果存在很慢的I/O操作则会影响性能

另外还要注意以下两点能够提高程序的性能：

1. 尽量减小锁的粒度
2. 尽量减少锁的时间


