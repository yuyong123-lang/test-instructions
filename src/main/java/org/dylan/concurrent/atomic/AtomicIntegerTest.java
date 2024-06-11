package org.dylan.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dylan
 * @version 2024/6/8
 */
public class AtomicIntegerTest {

    protected static AtomicInteger atomicInteger = new AtomicInteger(0);


    public static void main(String[] args) throws InterruptedException {
        final AtomicInteger number = new AtomicInteger();
        number.compareAndSet(0, 2);
        number.getAndIncrement();
        System.out.println(number);
    }

    private static void executeMultiThread() throws InterruptedException {
        Thread thread1 = new Thread(new Task());
        Thread thread2 = new Thread(new Task());

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(atomicInteger.get());
    }

    public static class Task implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                //累加并返回
                atomicInteger.incrementAndGet();
            }
        }
    }
}
