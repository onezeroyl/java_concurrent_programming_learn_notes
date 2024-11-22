# Java线程（上）：Java线程的生命周期

## 通用的线程生命周期

![img.png](img.png)

* 初始状态：线程在编程语言层面被创建，但是在操作系统里还未真正创建
* 可运行状态：在操作系统层面已经创建该线程，但是还没被分配CPU执行
* 运行状态：被分配了CPU执行的线程
* 休眠状态：调用了阻塞API（例如以阻塞的方式读取文件）或在等待某个事件（例如条件变量）等该线程会进入到休眠状态
* 终止状态：线程执行结束或出现异常会进入终止状态

## Java中线程生命周期

![img_1.png](img_1.png)

* NEW（初始状态）：对应通用里面的初始状态，对应Java中的`new Thread()`语句
*

RUNNABLE（可运行/运行状态）：Java线程把通用线程生命周期中的可运行和运行状态合并成一个RUNNABLE状态，所以出于这个状态的线程可以理解为拥有CPU的使用权，但是是否被分配时间使用是未知的。对应Java中的
`thread.start()`方法

* BLOCKED（阻塞状态）：Java线程把等待获取隐式锁的过程称为阻塞状态，对应Java中的`synchronized(lock){}`
  。值得注意的是线程调用阻塞式API时在操作系统层面线程会进入休眠状态，而在Java层面也是RUNNABLE状态，因为他也属于等待资源
* WAITING（无时限等待状态）：进入该状态的方式有三种：
    * 线程加锁代码块中使用`wait()`方法
    * 线程调用另一个线程的`join()`方法
    * 调用`LockSupport.park()`方法
* TIMED_WAITING（有时限等待状态）：进入该状态的方式有五种：
    * 调用带超时参数的`Thread.sleep(long millis)`方法
    * 调用带超时参数的`join(long millis)`方法
    * 调用带超时参数的`wait(long millis)`方法
    * 调用带超时参数的`LockSupport.parkNanos(Object blocker, long deadline)`方法
    * 调用带超时参数的`LockSupport.parkUnit(long deadline)`方法
* TERMINATED（终止状态）：当线程执行完或者抛出异常后线程进入TERMINATED状态。当需要强制中断线程的执行是可以使用
  `interrupt()`方法，该方法并不会立刻停止线程的执行，而只是通知线程。被终止的线程接收到通知有两种方式：
    * 异常：当被调用`interrupt()`
      方法的线程除于WAITING、TIMED_WAITING状态时，会使该线程进入RUNNABLE状态，同时线程会触发InterruptedException异常
    * 主动检测：处于RUNNABLE状态的线程可以调用`isInterrupted()`方法来主动检测线程的中断状态

## 课后思考