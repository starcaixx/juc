package com.atguigu.juc;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author star
 * @create 2019-03-25 10:58
 */
public class SaleTickets {

    public static void main(String[] args) {
        Thread t1 = new Thread();

        t1.start();
        t1.start();//IllegalThreadStateException

    }

    private static void mapConcurrentTest() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
//        Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>());
//        Hashtable<String, String> map = new Hashtable<>();
//        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0, 3));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    private static void setTest() {
        CopyOnWriteArraySet<String> hs = new CopyOnWriteArraySet<>();
//        HashSet<String> hs = new HashSet<>();
//        Set<String> hs = Collections.synchronizedSet(new HashSet<>());

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                hs.add(UUID.randomUUID().toString());
                System.out.println(Thread.currentThread().getName() + "=====" + hs);
            }, String.valueOf(i)).start();
        }
    }

    private static void arrayListTest() {
        List<String> list = new CopyOnWriteArrayList<>();
//        List<Object> list = Collections.synchronizedList(new ArrayList<>());
        //new Vector<String>();//new ArrayList<>();//ConcurrentModificationException

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                list.add(UUID.randomUUID().toString());
                System.out.println(Thread.currentThread().getName() + ":" + list.size());
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                list.add(UUID.randomUUID().toString());
                System.out.println(Thread.currentThread().getName() + ":" + list.size());
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                list.add(UUID.randomUUID().toString());
                System.out.println(Thread.currentThread().getName() + ":" + list.size());
            }
        }).start();
    }

    private static void saleTicket() {
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "a-thread").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "b-thread").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "c-thread").start();
    }
}

class Ticket {
    private int num = 30;

    Lock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try {
            if (num > 0) {
                System.out.println(Thread.currentThread().getName() + "======售出" + (30 - (--num)) + "，还剩下：" + num);
            }
        } finally {
            lock.unlock();
        }
    }
    /*public synchronized void sale1() {
        if (num>0){
            System.out.println(Thread.currentThread().getName()+"======售出"+(30-(--num))+"，还剩下："+num);
        }
    }*/
}