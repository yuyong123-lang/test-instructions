package org.dylan.concurrent.queue;

import cn.hutool.core.util.StrUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Dylan
 * @version 2024/6/8
 */
public class BlockingQueueTest {

    private final static ArrayBlockingQueue<String> ARRAY_BLOCKING_QUEUE = new ArrayBlockingQueue<>(10);

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(10,
            10,
            10,
            java.util.concurrent.TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    public static void main(String[] args) {

        //顾客排队
        THREAD_POOL_EXECUTOR.execute(new QueueUp());

        for (int i = 0; i < 3; i++) {
            THREAD_POOL_EXECUTOR.execute(new EatingTask());
        }
    }

    public static class QueueUp implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    String customerName = "顾客" + i;
                    System.out.println(StrUtil.format("{}, 开始排队", customerName));
                    ARRAY_BLOCKING_QUEUE.put(customerName);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static class EatingTask implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    final String thisCustomerName = ARRAY_BLOCKING_QUEUE.take();
                    System.out.println(StrUtil.format("{}, 排队成功，进入餐厅开始吃饭。", thisCustomerName));
                    TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
                    System.out.println(StrUtil.format("{},吃完饭离开。", thisCustomerName));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
