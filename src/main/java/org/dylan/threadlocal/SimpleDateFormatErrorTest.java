package org.dylan.threadlocal;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Dylan
 * @version 2024/6/7
 */
public class SimpleDateFormatErrorTest {

    private final static ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(10,
            10,
            60,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
            new ThreadPoolExecutor.DiscardPolicy()
    );

    public static void main(String[] args) {

        EXECUTOR.execute(() -> {
            System.out.println(DateFormatCache.getSimpleDateFormat().format(new Date(1)));
            System.out.println(DateFormatCache.getSimpleDateFormat().format(new Date(1)));
        });

        final Thread thread = new Thread();
        System.out.println(thread);
    }
}
