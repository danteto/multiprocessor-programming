package com.dan.locks;

import com.dan.utils.ThreadID;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BakeryLock implements Lock {

    // BakeryLock algorithm requires sequential consistency so we use atomic variables
    private AtomicBoolean[] flag;
    private AtomicInteger[] label;
    private static int n;

    public BakeryLock(int n) {
        this.n = n;
        flag = new AtomicBoolean[n];
        label = new AtomicInteger[n];
        for (int i = 0; i < n; i++) {
            flag[i] = new AtomicBoolean();
            label[i] = new AtomicInteger();
        }
    }

    @Override
    public void lock() {
        int i = ThreadID.getCurrentThreadId(n);
        flag[i].set(true);
        label[i].set(findMax(label) + 1);
        for (int k = 0; k < n; k++) {
            while ((k != i) && flag[k].get() && ((label[k].get() < label[i].get()) || ((label[k].get() == label[i].get()) && k < i))) {
                // spinning
            }
        }
    }

    @Override
    public void unlock() {
        flag[ThreadID.getCurrentThreadId(n)].set(false);
    }

    private int findMax(AtomicInteger[] a) {
        int max = 0;
        for (AtomicInteger e : a) {
            if (e.get() > max) {
                max = e.get();
            }
        }
        return max;
    }
}