package org.dylan.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Dylan
 * @version 2024/6/8
 */
public class AtomicReferenceTest {
    public static void main(String[] args) {
        AtomicReference<String> atomicReference = new AtomicReference<>();

        atomicReference.set("abcd");
        // 比较前获取值
        final String result = atomicReference.get();
        System.out.println("获取值：" + result);

        //比较后更新
        System.out.println(atomicReference.compareAndSet("abcd", "hf"));

        System.out.println("更新值：" + atomicReference.get());
    }
}
