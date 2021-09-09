package com.dan.locks;

public class ReadWriteLock {
    private int readers;
    private boolean isWriteLocked;

    public ReadWriteLock() {
        this.readers = 0;
        this.isWriteLocked = false;
    }

    public synchronized void lockRead() throws InterruptedException {
        if (isWriteLocked) {
            wait();
        }
        readers++;
    }

    public synchronized void unlockRead() {
        readers--;
        notify();
    }

    public synchronized void lockWrite() throws InterruptedException {
        if (isWriteLocked || readers != 0) {
            wait();
        }

        isWriteLocked = true;
    }

    public synchronized void unlockWrite() {
        isWriteLocked = false;
        notify();
    }

    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReadWriteLock();

        Thread t1 = new Thread(() -> {
            try {
                System.out.println("Attempting to acquire write lock in t1: " + System.currentTimeMillis());
                readWriteLock.lockRead();
                try {
                    System.out.println("write lock acquired t1: " + +System.currentTimeMillis());
                    while (true) {
                        Thread.sleep(500);
                    }
                } finally {
                    readWriteLock.unlockRead();
                }
            } catch (InterruptedException ie) {

            }
        });

        Thread t2 = new Thread(() -> {
            try {
                System.out.println("Attempting to acquire write lock in t2: " + System.currentTimeMillis());
                readWriteLock.lockRead();
                try {
                    System.out.println("write lock acquired t2: " + System.currentTimeMillis());
                } finally {
                    readWriteLock.unlockRead();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                System.out.println("Attempting to acquire write lock in t3: " + System.currentTimeMillis());
                readWriteLock.lockWrite();
                try {
                    System.out.println("write lock acquired t3: " + System.currentTimeMillis());
                } finally {
                    readWriteLock.unlockWrite();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        t1.start();
        t2.start();
        t3.start();

    }
}
