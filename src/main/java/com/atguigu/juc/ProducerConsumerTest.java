package com.atguigu.juc;

/**
 * @author star
 * @create 2019-03-25 19:42
 */
/*题目：现在两个线程，可以操作初始值为零的一个变量，
        * 实现一个线程对该变量加1，一个线程对该变量减1，
        * 实现交替，来10轮，变量初始值为零。

        1    高聚低合前提下，线程操作资源类
        2    判断+干活+通知
        3    防止线程的虚假唤醒,只要有wait需要用while判断*/
//
public class ProducerConsumerTest {
    public static void main(String[] args) {
        Producer producer = new Producer();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                producer.decrease();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                producer.increase();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                producer.decrease();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                producer.increase();
            }
        }).start();
    }
}

class Producer {
    private int num = 0;

    public synchronized void increase() {
        while (num != 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        num++;
        System.out.println(num);
        this.notifyAll();
    }

    public synchronized void decrease() {
        while (num == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        num--;
        System.out.println(num);
        this.notifyAll();
    }
}
