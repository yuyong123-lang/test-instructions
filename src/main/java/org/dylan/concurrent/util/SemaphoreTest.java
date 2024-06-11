package org.dylan.concurrent.util;

import java.util.concurrent.*;

/**
 * @author Dylan
 * @version 2024/6/6
 */
public class SemaphoreTest {

    private final static ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            10,
            10,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    private static final Semaphore SEMAPHORE = new Semaphore(2, true);

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            EXECUTOR.execute(new Task());
        }
    }

    /**
     * 任务
     */
    private static final class Task implements Runnable {
        @Override
        public void run() {
            try {
                // 申请许可
                SEMAPHORE.acquire();
                System.out.println(Thread.currentThread().getName() + "获取到了许可证，开始运行. ");
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println(Thread.currentThread().getName() + "运行结束. ");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                SEMAPHORE.release();
            }
        }
    }
}
