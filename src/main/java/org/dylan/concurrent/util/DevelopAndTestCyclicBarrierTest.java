package org.dylan.concurrent.util;

import java.util.concurrent.*;

/**
 * @author Dylan
 * @version 2024/6/6
 */
public class DevelopAndTestCyclicBarrierTest {

    public static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            10,
            20,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    public static final CyclicBarrier PRD_COUNT = new CyclicBarrier(1, new DevelopStart());

    public static final CyclicBarrier DEVELOP_COUNT = new CyclicBarrier(4, new TestStart());

    public static final CyclicBarrier TEST_COUNT = new CyclicBarrier(1, new OperationUpStart());

    public static final class DevelopStart implements Runnable {

        @Override
        public void run() {
            EXECUTOR.execute(new DevelopCode("java小红"));
            EXECUTOR.execute(new DevelopCode("java小绿"));
            EXECUTOR.execute(new DevelopCode("java小蓝"));
            EXECUTOR.execute(new DevelopCode("java小紫"));
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("产品经理此时正在紧张的设计原型和PRD.....");
            TimeUnit.SECONDS.sleep(3);
            System.out.println("原型设计完毕.");
            PRD_COUNT.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 开发人员开始进行开发代码
     */
    private static class DevelopCode implements Runnable {
        private final String name;

        private DevelopCode(String name) {
            this.name = name;
        }

        @Override
        public void run() {

            try {
                System.out.println(name + "开始开发代码.......");
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println(name + "完成了代码开发！");
                //等待其他人完成开发
                DEVELOP_COUNT.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static final class TestStart implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("开发人员全部都开发完成了，测试人员开始测试.");
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("测试人员完成测试，服务没有问题，可以准备上线了.");
                TEST_COUNT.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static final class OperationUpStart implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("检测到测试完成，运维开始上线代码");
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("上线完成");
                //上线完成后关闭线程池
                EXECUTOR.shutdown();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


}
