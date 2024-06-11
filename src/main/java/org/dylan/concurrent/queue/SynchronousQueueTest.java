package org.dylan.concurrent.queue;

import java.util.concurrent.SynchronousQueue;

/**
 * @author Dylan
 * @version 2024/6/8
 */
public class SynchronousQueueTest {

    public static void main(String[] args) throws InterruptedException {

        final SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

        //模拟生产者
        new Thread(() -> {
            while (true) {
                try {
                    synchronousQueue.put("你好");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        //模拟消费者
        new Thread(() -> {
            while (true) {
                try {
                    //睡眠 使消费者的消费速度低于数据产生的速度
                    Thread.sleep(2000);
                    System.out.println(synchronousQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
