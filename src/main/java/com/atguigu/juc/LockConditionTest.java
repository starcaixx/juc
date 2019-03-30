package com.atguigu.juc;

/**
 * @author star
 * @create 2019-03-26 18:44
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 备注：多线程之间按顺序调用，实现A->B->C
 * 三个线程启动，要求如下：
 * <p>
 * AA打印5次，BB打印10次，CC打印15次
 * 接着
 * AA打印5次，BB打印10次，CC打印15次
 * 来10轮
 */
public class LockConditionTest {
    public static void main(String[] args) {
        AirConditioner airConditioner = new AirConditioner();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                airConditioner.println();
            }
        }, "a").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                airConditioner.println();
            }
        }, "b").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                airConditioner.println();
            }
        }, "c").start();
    }
}

class AirConditioner {
    private int num = 0;
    private Lock lock = new ReentrantLock();

    private int[][] flagArr = {{0,1,2},{5, 10, 15}};
    Condition[] conditions = {lock.newCondition(), lock.newCondition(), lock.newCondition()};

    public void println() {
        lock.lock();
        try {
            String threadName = Thread.currentThread().getName();
            int key = threadName.startsWith("a")?0:threadName.startsWith("b")?1:2;
            while (num != flagArr[0][key]) {
                conditions[key].await();
            }
            int tmp = flagArr[1][key];
            for (int i = 0; i < tmp; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + key + ":" + i);
            }
            num = (key+1) % 3;
            conditions[num].signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /*
    public void println5() {
        lock.lock();
        try {
            while (flag != 0) {
                c1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + i);
            }
            flag = 1;
            c2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void println10() {
        lock.lock();
        try {
            while (flag != 1) {
                c2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + i);
            }
            flag = 2;
            c3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void println15() {
        lock.lock();
        try {
            while (flag != 2) {
                c3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + i);
            }
            flag = 0;
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }*/

}