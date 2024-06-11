package org.dylan.concurrent.util;

import java.util.concurrent.*;

/**
 * @author Dylan
 * @version 2024/6/6
 */
public class DineTest {

    public static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            10,
            20,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    private static final CyclicBarrier BUDDY_COUNT = new CyclicBarrier(4, () -> {
        System.out.println("人都到齐了，出发去团建;每一个人都很开心，脸上挂着幸福的笑容.");
        System.out.println("公司班车开始发往目的地...");
        try {
            Thread.sleep((long) (Math.random() * 10000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("两个小时后...");
    });

    private static class EmployeeAct implements Runnable {
        private final String name;

        private EmployeeAct(String name) {
            this.name = name;
        }


        @Override
        public void run() {
            try {
                System.out.println(name + "出发前往公司.");
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println(name + "到达公司");
                BUDDY_COUNT.await();

                System.out.println(name + "经过2个小时的车程，到达了目的地");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        EXECUTOR.execute(new EmployeeAct("打工人1号"));
        EXECUTOR.execute(new EmployeeAct("打工人2号"));
        EXECUTOR.execute(new EmployeeAct("打工人3号"));
        EXECUTOR.execute(new EmployeeAct("打工人4号"));
    }
}
