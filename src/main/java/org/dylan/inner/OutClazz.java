package org.dylan.inner;

import lombok.Data;

/**
 * @author Dylan
 * @version 2024/6/10
 */

@Data
public class OutClazz {

    private String name;

    public class InnerClazz {
        public String test() {
            return name;
        }
    }

    public InnerClazz newInnerClazz() {
        return new InnerClazz();
    }

    public static void main(String[] args) {
        final OutClazz outClazz = new OutClazz();
        outClazz.setName("YuYong");
        final InnerClazz innerClazz = outClazz.newInnerClazz();
        System.out.println(innerClazz.test());
    }

}
