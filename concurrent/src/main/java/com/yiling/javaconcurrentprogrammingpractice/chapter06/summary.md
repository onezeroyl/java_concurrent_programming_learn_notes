# 用“等待-通知”机制优化循环等待

在上一章里，我们在破坏占有且等待条件时，我们使用Allocator类来确保同一个线程要么能同时持有两个资源，否则就while循环等待
这种方式可能会导致线程长时间的阻塞
在这个章节里，我们通过等待通知机制来优化这个问题

## 通知等待机制

1. 线程A获取互斥锁，执行业务逻辑
2. 当指定的条件不满足时，线程A释放互斥锁，进入该互斥锁的等待队列
3. 线程B获取互斥锁，执行业务逻辑
4. 发现指定的条件满足，唤醒等待队列中的线程
5. 被唤醒的线程重新获取互斥锁，执行业务逻辑

## synchronized中的通知-等待机制

synchronized提供了`wait()`, `notify()`, `notifyAll()`三个方法来实现通知等待机制
`wait()`方法会使线程释放互斥锁并进入该互斥锁的等待队列
`notify()`方法会随机唤醒一个等待队列中的线程
`notifyAll()`方法会唤醒等待队列中的所有线程，这些线程会再去竞争CPU执行权，最终还是只有一个方法能获取到互斥锁

使用上述方法改造上一章中的代码，具体见本包下的Account.java

- [ ] 文章里说使用`notify()`方法可能会导致唤醒到不满足等待条件的线程导致死锁，所以推荐使用`notifyAll()`
但是`notifyAll()`好像也解决不了这个问题，因为也可能是不满足等待条件的线程竞争到执行权，所以放个待办在这里