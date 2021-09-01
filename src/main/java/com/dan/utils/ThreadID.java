package com.dan.utils;
/*
 * This class helps us ensure that we can properly identify our threads,
 * because Java's thread ID's may not start counting at 0.
 *
 * This class comes from page 459 of The Art of Multiprocessor Programming.
 */

public class ThreadID {
    private static volatile int nextID = 0;

    private static class ThreadLocalID extends ThreadLocal<Integer> {
        protected synchronized Integer initialValue() {
            return nextID++;
        }
    }

    private static ThreadLocalID threadID = new ThreadLocalID();

    public static int get() {
        return threadID.get();
    }

    public static void set(int index) {
        threadID.set(index);
    }
}