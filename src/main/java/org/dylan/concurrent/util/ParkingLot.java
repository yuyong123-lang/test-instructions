package org.dylan.concurrent.util;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Dylan
 * @version 2024/6/6
 */
public class ParkingLot {

    public static ThreadPoolExecutor EXECUTOR = null;

    static {
        EXECUTOR = new ThreadPoolExecutor(
                10,
                10,
                2,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        EXECUTOR.allowCoreThreadTimeOut(true);
    }

    private final static ReentrantLock LOCK = new ReentrantLock();
    private final static Condition CONDITION = LOCK.newCondition();

    private final int totalParkingSpaces;

    private int occupiedParkingSpaces = 0;

    public ParkingLot(int totalParkingSpaces) {
        this.totalParkingSpaces = totalParkingSpaces;
    }

    public void park(String name) {
        LOCK.lock();
        try {
            if (occupiedParkingSpaces >= totalParkingSpaces) {
                // 如果停车场已满，等待
                System.out.println(name + ": 车辆等待停车位...");
                // 开始等待车位
                CONDITION.await();
            }
            occupiedParkingSpaces++;
            System.out.println(name + ": 车辆成功停车，剩余的停车位:" + (totalParkingSpaces - occupiedParkingSpaces));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            LOCK.unlock();
        }
    }

    public void bearOff(String name) {
        LOCK.lock();
        try {
            // 离开停车场  将已占用的数量-1
            occupiedParkingSpaces--;
            System.out.println(name + ": 车辆离开停车场，剩余停车位: " + (totalParkingSpaces - occupiedParkingSpaces));
            // 通知等待的车辆有空位了
            CONDITION.signal();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            LOCK.unlock();
        }
    }

    public static final class CarActive implements Runnable {

        private final ParkingLot parkingLot;

        private final String name;

        public CarActive(ParkingLot parkingLot, String name) {
            this.parkingLot = parkingLot;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                parkingLot.park(name);
                Thread.sleep((long) (Math.random() * 10000));
                parkingLot.bearOff(name);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(5);
        for (int i = 0; i < 10; i++) {
            EXECUTOR.execute(new CarActive(parkingLot, "车辆" + i));
        }
    }
}
