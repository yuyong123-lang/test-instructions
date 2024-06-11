package org.dylan.concurrent.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author Dylan
 * @version 2024/6/8
 */
public class AtomicIntegerArrayTest {

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(1000);

        // 20组线程 每组线程 A和 B 两个线程，分别作加减操作
        List<Thread> threads = new ArrayList<>(40);

        IncrementTask task1 = new IncrementTask(atomicIntegerArray);
        DecrementTask task2 = new DecrementTask(atomicIntegerArray);

        for (int i = 0; i < 20; i++) {
            final Thread task1Thread = new Thread(task1);
            threads.add(task1Thread);
            final Thread task2Thread = new Thread(task2);
            threads.add(task2Thread);
        }

        // 将所有的线程在主线程之前执行完成
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("线程执行完毕");
        //获取当前原子数组中的数据
        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.println(atomicIntegerArray.get(i));
        }
    }

    /**
     * 添加任务
     */
    public static class IncrementTask implements Runnable {
        private final AtomicIntegerArray atomicIntegerArray;

        public IncrementTask(AtomicIntegerArray atomicIntegerArray) {
            this.atomicIntegerArray = atomicIntegerArray;
        }

        @Override
        public void run() {
            for (int i = 0; i < atomicIntegerArray.length(); i++) {
                atomicIntegerArray.getAndIncrement(i);
            }
        }
    }


    /**
     * 减少任务
     */
    public static class DecrementTask implements Runnable {
        private final AtomicIntegerArray atomicIntegerArray;

        public DecrementTask(AtomicIntegerArray atomicIntegerArray) {
            this.atomicIntegerArray = atomicIntegerArray;
        }

        @Override
        public void run() {
            for (int i = 0; i < atomicIntegerArray.length(); i++) {
                atomicIntegerArray.getAndDecrement(i);
            }
        }
    }
}
