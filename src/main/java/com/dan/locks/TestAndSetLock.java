package com.dan.locks;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestAndSetLock implements Lock {

    AtomicBoolean state = new AtomicBoolean(false);

    @Override
    public void lock() {
        while (state.getAndSet(true)) {
        }

    }

    @Override
    public void unlock() {
        state.set(false);
    }
}
