package com.dan;

import com.dan.utils.ThreadID;

import java.util.concurrent.atomic.AtomicInteger;

public class FilterLock implements Lock {

    private AtomicInteger[] level;
    private AtomicInteger[] victim;

    private int n;

    public FilterLock(int n) {
        this.n = n;
        level = new AtomicInteger[n];
        victim = new AtomicInteger[n];
        for (int i = 0; i < n; i++) {
            level[i] = new AtomicInteger();
            victim[i] = new AtomicInteger();
        }
    }

    @Override
    public void lock() {
        int me = ThreadID.getCurrentThreadId(n);
        for (int i = 1; i < n; i++) {
            level[me].set(i);
            victim[i].set(me);
            for (int k = 0; k < n; k++) {
                while ((k != me) && (level[k].get() >= i && victim[i].get() == me)) {
                    // spinning...
                }
            }
        }
    }

    @Override
    public void unlock() {
        int me = ThreadID.getCurrentThreadId(n);
        level[me].set(0);
    }
}