package org.dylan.concurrent.queue;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Dylan
 * @version 2024/6/8
 */
public class PriorityQueueTest {
    public static final PriorityBlockingQueue<String> priorityQueue = new PriorityBlockingQueue<>(10,
            Comparator.comparing(String::length).reversed()
    );

    public static void main(String[] args) throws InterruptedException {
        priorityQueue.add("a");
        priorityQueue.add("ab");
        priorityQueue.add("abc");
        //4
        System.out.println(priorityQueue.take());
        //3
        System.out.println(priorityQueue.take());
        //2
        System.out.println(priorityQueue.take());
        //1
        System.out.println(priorityQueue.take());
    }

}
