package com.dan;

public class ThreadSafeCounter implements Counter {
    private int count;

    @Override
    synchronized public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
