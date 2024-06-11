package org.dylan.concurrent.exception;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Dylan
 * @version 2024/6/8
 */
public class RunnableTest {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    public static Condition condition = lock1.newCondition();

    public static void main(String[] args) {
        new Thread(() -> {
            lock2.lock();
            try {
                System.out.println("线程1获取锁1");
                condition.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock2.unlock();
            }

        }, "线程").start();
    }
}
