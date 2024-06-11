package org.dylan.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author Dylan
 * @version 2024/6/8
 */
public class AtomicIntegerFieldUpdaterTest {

    public static void main(String[] args) throws InterruptedException {
        // 定义需要修改的字段
        final AtomicIntegerFieldUpdater<Count> numberUpdater =
                AtomicIntegerFieldUpdater.newUpdater(Count.class, "number");

        final Count count = new Count();

        Task task = new Task(numberUpdater, count);
        Thread thread = new Thread(task);
        Thread thread1 = new Thread(task);

        thread.start();
        thread1.start();
        thread.join();
        thread1.join();

        System.out.println(count.number);
    }

    public static class Count {
        public volatile int number = 0;
    }

    private static class Task implements Runnable {
        private final AtomicIntegerFieldUpdater<Count> numberUpdater;
        private final Count count;

        private Task(AtomicIntegerFieldUpdater<Count> numberUpdater, Count count) {
            this.numberUpdater = numberUpdater;
            this.count = count;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                //对对象内的数据进行累加操作
                numberUpdater.getAndIncrement(count);
            }
        }
    }
}
