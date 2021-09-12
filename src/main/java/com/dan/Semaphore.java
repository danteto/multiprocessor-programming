package com.dan;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Semaphore {
    private final int capacity;
    private int state;
    private Lock lock;
    private Condition condition;

    public Semaphore(int capacity) {
        this.capacity = capacity;
        this.state = 0;
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    public void acquire() {
        lock.lock();
        try {
            while (state == capacity) {
                condition.await();
            }
            state++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            state--;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
