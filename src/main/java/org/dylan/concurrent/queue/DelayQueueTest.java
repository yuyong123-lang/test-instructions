package org.dylan.concurrent.queue;

import cn.hutool.core.date.StopWatch;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author Dylan
 * @version 2024/6/8
 */
public class DelayQueueTest {

    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();
        final Thread thread1 = new Thread(() -> {
            delayQueue.add(new DelayedTask(50000, "a1"));
        }, "线程一");
        thread1.start();

        final Thread thread2 = new Thread(() -> {
            try {
                System.out.println(delayQueue.take().taskName);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "线程二");
        thread2.start();

        // 主线程去消费
        TimeUnit.SECONDS.sleep(40);
        delayQueue.add(new DelayedTask(40000, "a2"));
    }

    public static class DelayedTask implements Delayed {

        private final long delayTime;
        private final String taskName;

        public DelayedTask(long delayTime, String taskName) {
            this.delayTime = System.currentTimeMillis() + delayTime;
            this.taskName = taskName;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = delayTime - System.currentTimeMillis();
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            long diff = this.delayTime - ((DelayedTask) o).delayTime;
            return Long.compare(diff, 0);
        }
    }
}
