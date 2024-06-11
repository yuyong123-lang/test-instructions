package org.dylan.concurrent.atomic;

import cn.hutool.core.util.StrUtil;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Dylan
 * @version 2024/6/8
 */
public class SpinLockDemo {

    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(null, thread)) {
            System.out.println(StrUtil.format("{},尝试重新获取锁", Thread.currentThread().getName()));
        }
    }

    public void unLock() {
        //获取当前线程
        Thread thread = Thread.currentThread();
        //如果是当前线程 就将当前线程设为null  解锁
        atomicReference.compareAndSet(thread, null);
    }

    public static void main(String[] args) throws InterruptedException {
        Task task = new Task(new SpinLockDemo());
        Thread thread1 = new Thread(task,"线程1");
        Thread thread2 = new Thread(task,"线程2");
        Thread thread3 = new Thread(task,"线程3");
        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("此时值为:" + task.i);
    }

    private static class Task implements Runnable {
        int i = 0;
        private SpinLockDemo spinLock;

        public Task(SpinLockDemo spinLock) {
            this.spinLock = spinLock;
        }

        @Override
        public void run() {
            spinLock.lock();
            try {
                for (int j = 0; j < 100000; j++) {
                    i++;
                }
            } finally {
                spinLock.unLock();
            }
        }
    }
}
