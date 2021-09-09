package com.dan.locks;

import com.dan.locks.BakeryLock;
import com.dan.utils.LockCounter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class BakeryLockTest {

    @Test
    public void testCounterWithoutLock() throws InterruptedException {
        LockCounter lockCounter = new LockCounter();
        Thread thread1 = new Thread(lockCounter::increment);
        Thread thread2 = new Thread(lockCounter::increment);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertNotEquals(2, lockCounter.getCounter());
    }

    @Test
    public void testCounterWithBakeryLock() throws InterruptedException {
        BakeryLock bakeryLock = new BakeryLock(2);
        LockCounter lockCounter = new LockCounter(bakeryLock);
        Thread thread1 = new Thread(lockCounter::increment);
        Thread thread2 = new Thread(lockCounter::increment);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(2, lockCounter.getCounter());
    }

}
