package com.atguigu.juc;

/**
 * @author star
 * @create 2019-03-26 20:37
 */
public class TestCodeBlock {
    public static void main(String[] args) {
        new Thread(()->{new Car();}).start();
        new Thread(()->{new Car();}).start();
        new Thread(()->{new Car();}).start();
    }
}

class Car{

    private int num = 0;

    public Car(){
        synchronized (this){
            System.out.println("");
        }
    }

    {
        System.out.println(Thread.currentThread().getName()+"====");
    }
}
