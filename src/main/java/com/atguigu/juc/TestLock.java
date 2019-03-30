package com.atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author star
 * @create 2019-03-26 18:15
 *//*
题目：现在两个线程，可以操作初始值为零的一个变量，
        * 实现一个线程对该变量加1，一个线程对该变量减1，
        * 实现交替，来10轮，变量初始值为零。

        1    高聚低合前提下，线程操作资源类
        2    判断+干活+通知
        3    防止线程的虚假唤醒,只要有wait需要用while判断*/
public class TestLock {
    public static void main(String[] args) {
        Consumer consumer = new Consumer();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                consumer.increase();
            }
        },"A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                consumer.decrease();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                consumer.increase();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                consumer.decrease();
            }
        }, "D").start();

    }
}

class Consumer {
    private int num = 0;
    private Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();

    public void increase() {
        lock.lock();
        try{
            while (num != 0) {
                c1.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName()+"===:"+num);
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }

    public void decrease() {
        lock.lock();
        try{
            while (num == 0) {
                c1.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName()+"==:"+num);
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }
}
