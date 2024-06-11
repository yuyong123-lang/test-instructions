package org.dylan.concurrent.atomic;

import java.util.concurrent.atomic.LongAccumulator;

/**
 * @author Dylan
 * @version 2024/6/8
 */
public class LongAccumulatorTest {

    public static void main(String[] args) {
        LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x * y, 2);
        longAccumulator.accumulate(1);
        longAccumulator.accumulate(2);
        longAccumulator.accumulate(3);
        System.out.println(longAccumulator.getThenReset());
    }
}
