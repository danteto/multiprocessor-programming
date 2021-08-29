package com.dan;

public class NotThreadSafeCounter implements Counter {
    private int count;

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
