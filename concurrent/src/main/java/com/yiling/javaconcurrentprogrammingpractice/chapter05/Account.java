package com.yiling.javaconcurrentprogrammingpractice.chapter05;

import lombok.Data;

/**
 * @author whs
 */
@Data
public class Account {

    private int id;

    private int balance;

    private final Allocator allocator = new Allocator();

    public void transformByObjectLock(Account target, int count) {
        synchronized (this) {
            synchronized (target) {
                this.setBalance(getBalance() - count);
                target.setBalance(target.getBalance() + count);
            }
        }
    }

    public void transformPreventDeadlockMethod1(Account target, int count) {
        while (allocator.apply(target, this)) {
            try {
                synchronized (this) {
                    synchronized (target) {
                        this.setBalance(getBalance() - count);
                        target.setBalance(target.getBalance() + count);
                    }
                }
            } finally {
                allocator.release(target, this);
            }
        }
    }

    public void transformPreventDeadlockMethod2(Account target, int count) {
        Account before, after;
        if (this.getId() > target.getId()) {
            before = target;
            after = this;
        } else {
            before = this;
            after = target;
        }
        synchronized (before) {
            synchronized (after) {
                this.setBalance(getBalance() - count);
                target.setBalance(target.getBalance() + count);
            }
        }
    }


}
