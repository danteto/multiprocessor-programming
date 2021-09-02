package com.dan;

public class NotThreadSafeCounter implements Counter {
    private int count;

    public NotThreadSafeCounter() {
        this.count = 0;
    }

    @Override
    public void increment() {
        count++;
    }

    public synchronized void incrementWithWait() throws InterruptedException {
        int temp = count;
        wait(100);
        count = temp + 1;
    }

    public int getCount() {
        return count;
    }
}
