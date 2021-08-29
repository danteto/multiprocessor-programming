package com.dan;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NotThreadSafeCounterTest {

    @Test
    public void testCounterWithSingleThread() {
        NotThreadSafeCounter counter = new NotThreadSafeCounter();
        for (int i = 0; i < 500; i++) {
            counter.increment();
        }

        assertEquals(500, counter.getCount());
    }

    @Test
    public void testCounterWithMultipleThreads() throws InterruptedException {
        int numberOfThreads = 2;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        NotThreadSafeCounter counter = new NotThreadSafeCounter();
        for (int i = 0; i < numberOfThreads; i++) {
            service.submit(() -> {
                try {
                    counter.incrementWithWait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }
        latch.await();

        assertNotEquals(numberOfThreads, counter.getCount());
    }
}

