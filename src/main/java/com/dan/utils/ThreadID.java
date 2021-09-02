package com.dan.utils;


public final class ThreadID {
    private ThreadID() {
    }

    public static int getCurrentThreadId(int capacity) {
        return (int) (Thread.currentThread().getId() % capacity);
    }

    public static int getCurrentThreadId() {
        return Integer.parseInt(Thread.currentThread().getName());
    }

}