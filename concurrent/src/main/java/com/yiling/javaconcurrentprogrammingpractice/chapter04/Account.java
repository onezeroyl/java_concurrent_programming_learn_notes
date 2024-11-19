package com.yiling.javaconcurrentprogrammingpractice.chapter04;

/**
 * @author whs
 */
public class Account {

    private String name;

    private String password;

    private int balance;

    // 为没有关联的name和password加锁
    // name的锁对象
    private final Object nameLock = new Object();
    // password的锁对象
    private final Object passwordLock = new Object();

    public String getName() {
        synchronized (nameLock) {
            return name;
        }
    }

    public void setName(String name) {
        synchronized (nameLock) {
            this.name = name;
        }
    }

    public String getPassword() {
        synchronized (nameLock) {
            return password;
        }
    }

    public void setPassword(String password) {
        synchronized (nameLock) {
            this.password = password;
        }
    }

    public void transform(Account target, int count) {
        synchronized (Account.class) {
            balance = balance - count;
            target.balance = target.balance + count;
        }
    }
}
