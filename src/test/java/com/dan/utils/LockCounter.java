package com.dan.utils;

import com.dan.Counter;
import com.dan.locks.Lock;

public class LockCounter implements Counter {
    private Lock lock;
    private int counter;

    public LockCounter(Lock lock) {
        this.counter = 0;
        this.lock = lock;
    }

    public LockCounter() {
        this.lock = new DummyLock();
    }

    @Override
    public void increment() {
        lock.lock();
        try {
            int temp = counter;
            Thread.sleep(500);
            counter = temp + 1;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public int getCounter() {
        return counter;
    }

    static class DummyLock implements Lock {

        @Override
        public void lock() {
        }

        @Override
        public void unlock() {
        }
    }
}
