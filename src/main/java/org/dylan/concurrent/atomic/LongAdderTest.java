package org.dylan.concurrent.atomic;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author Dylan
 * @version 2024/6/8
 */
public class LongAdderTest {

    private final static LongAdder longAdder = new LongAdder();

    public static void main(String[] args) throws InterruptedException {

        final Thread thread1 = new Thread(new Task());
        final Thread thread2 = new Thread(new Task());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(longAdder.sum());
    }

    public static class Task implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                longAdder.increment();
            }
        }
    }
}
