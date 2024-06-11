package org.dylan.hash;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dylan
 * @version 2024/6/11
 */
@Slf4j
public class HashTest {

    public static void main(String[] args) {
        final int result1 = "李二狗".hashCode();
        final int result2 = "拎瓢冲".hashCode();
        log.info("{},{},{}", result1 % 7, result2 % 7, result1 == result2);
    }
}
