package com.atguigu.juc;

/**
 * @author star
 * @create 2019-03-25 20:16
 */
public class SaleTicketsTest {
    //    题目：三个售票员         卖出          30张票
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                ticket.sale();
            }
        },"A").start();new Thread(()->{
            for (int i = 0; i < 20; i++) {
                ticket.sale();
            }
        },"B").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                ticket.sale();
            }
        }, "C").start();
    }
}
