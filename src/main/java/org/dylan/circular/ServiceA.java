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

    public void test() {
        System.out.println("测试命令项目");
    }

    public void test1() {
        System.out.println("测试命令项目-github");
    }
    
    public void test2() {
        System.out.println("测试命令项目-github");
    }
}
