package org.dylan.circular;

/**
 * @author Dylan
 * @version 2024/5/27
 */
public class ServiceA {

    private ServiceB serviceB;

    public void setServiceB(ServiceB serviceB) {
        this.serviceB = serviceB;
    }

    public void test_direct_merge() {}
}
