# Lock和Condition（下）：Dubbo如何用管程实现异步转同步？

Condition接口解决线程同步的问题
例如第八章中的BlockedQueue.java的实现

## 同步与异步

同步：调用方法阻塞式等待返回结果
异步：调用方法非阻塞式等待返回结果

## Dubbo源码

简单来说就是成功获取到返回值后通过Condition中的线程同步方法进行唤醒操作

## 总结
Lock&Condition实现的管程相对于synchronized来说更加灵活，功能也更为丰富