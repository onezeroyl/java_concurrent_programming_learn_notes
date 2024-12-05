package com.yiling.javaconcurrentprogrammingpractice.chapter21;

/**
 * @author whs
 */
public class SimulatedCAS {

    private int count;

    // 使用synchronized修饰和Happens-Before规则确保了原子性和可见性
    public synchronized boolean cas(int expectValue, int updateValue) {
        int oldValue = count;
        if (oldValue == expectValue) {
            count = updateValue;
            return true;
        }
        return false;
    }

    public void spinCas(int updateValue) {
        int oldValue;
        do {
            oldValue = count;
        } while (!cas(oldValue, updateValue));
    }

}
