package org.dylan.concurrent.queue;

import java.util.concurrent.TimeUnit;

/**
 * @author Dylan
 * @version 2024/6/8
 */
public class TimeUnitTest {

    public static void main(String[] args) {
        final long convert = TimeUnit.NANOSECONDS.convert(3000, TimeUnit.MILLISECONDS);
        System.out.println(convert);
    }
}
