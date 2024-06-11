package org.dylan.hash;

/**
 * @author Dylan
 * @version 2024/6/11
 */
public class GoldenNumberTest {
    private static final int HASH_INCREMENT = 0x61c88647;

    public static void main(String[] args) throws Exception {
        hashCode(4);
        hashCode(16);
        hashCode(32);
    }

    private static void hashCode(int capacity) throws Exception {
        int keyIndex;
        for (int i = 0; i < capacity; i++) {
            keyIndex = ((i + 1) * HASH_INCREMENT) & (capacity - 1);
            System.out.print(keyIndex);
            System.out.print(" ");
        }
        System.out.println();
    }
}
