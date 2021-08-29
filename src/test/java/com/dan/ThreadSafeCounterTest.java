package com.dan;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class ThreadSafeCounterTest {

    @Test
    public void testCounterWithSingleThread() {
        ThreadSafeCounter counter = new ThreadSafeCounter();
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
        ThreadSafeCounter counter = new ThreadSafeCounter();
        for (int i = 0; i < numberOfThreads; i++) {
            service.submit(() -> {
                counter.increment();
                latch.countDown();
            });
        }
        latch.await();

        assertEquals(numberOfThreads, counter.getCount());
    }
}
