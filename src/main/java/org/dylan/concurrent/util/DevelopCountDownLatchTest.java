package org.dylan.concurrent.util;

import java.util.concurrent.*;

/**
 * {@link CountDownLatch} 测试
 *
 * @author Dylan
 * @version 2024/6/6
 */
public class DevelopCountDownLatchTest {

    public static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            10,
            20,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    protected final static CountDownLatch DEMAND_COUNT = new CountDownLatch(1);
    protected final static CountDownLatch DEVELOP_COUNT = new CountDownLatch(4);

    private final static class OperationUp implements Runnable {
        private final String name;

        private OperationUp(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + "正在等待开发完成...");
                DEVELOP_COUNT.await();
                System.out.println("项目开发完毕，运维" + name + "开始上线.");
                System.out.println("上线成功..");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private final static class ProjectDemandPrd implements Runnable {

        private final String name;

        private ProjectDemandPrd(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + "产品经理此时正在紧张的设计原型和PRD.....");
                TimeUnit.SECONDS.sleep(3);
                System.out.println(name + "原型设计完毕.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                DEMAND_COUNT.countDown();
            }
        }
    }


    private final static class ProjectDevelop implements Runnable {
        private final String name;

        private ProjectDevelop(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + "正在等待产品经理的原型和PRD...");
                DEMAND_COUNT.await();
                System.out.println(name + "获取到了原型和PRD，开始开发.");
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println(name + "开发完毕.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                DEVELOP_COUNT.countDown();
            }
        }
    }

    public static void main(String[] args) {
        EXECUTOR.execute(new ProjectDevelop("java小红"));
        EXECUTOR.execute(new ProjectDevelop("java小绿"));
        EXECUTOR.execute(new ProjectDevelop("java小蓝"));
        EXECUTOR.execute(new ProjectDevelop("java小紫"));
        EXECUTOR.execute(new ProjectDemandPrd("需求小王"));
        EXECUTOR.execute(new OperationUp("运维奇奇"));
    }
}
