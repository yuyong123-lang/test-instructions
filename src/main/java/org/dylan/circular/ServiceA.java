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

    public void testInstructions() {
        System.out.println("测试命令项目");
    }
}