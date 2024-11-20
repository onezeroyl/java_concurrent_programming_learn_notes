package com.yiling.javaconcurrentprogrammingpractice.chapter06;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author whs
 */
@Data
@AllArgsConstructor
public class Account {

    private int id;

    private int balance;

    private static final Allocator allocator = new Allocator();


    public void transformPreventDeadlockMethod1(Account target, int count)
        throws InterruptedException {
        allocator.apply(target, this);
        try {
            synchronized (this) {
                synchronized (target) {
                    System.out.println(this.getId() + "获取的互斥资源:" + this.getId() + "和" + target.getId());
                    Thread.sleep(5000);
                    this.setBalance(getBalance() - count);
                    target.setBalance(target.getBalance() + count);
                    System.out.println(this.id + "向" + target.getId() + "转账:" + count +"元成功！");
                }
            }
        } finally {
            allocator.release(target, this);
        }
    }

    @Override
    public String toString() {
        return "Account{" +
            "id=" + id +
            '}';
    }

    public static void main(String[] args) {

        Account payAccount = new Account(1, 1000);
        Account reviceAccount = new Account(2, 1000);
        Account tpAccount = new Account(3, 1000);

        Thread thread1 = new Thread(() -> {
            try {
                tpAccount.transformPreventDeadlockMethod1(payAccount, 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                payAccount.transformPreventDeadlockMethod1(reviceAccount, 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread1.start();
        thread2.start();

    }


}
