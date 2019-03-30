package com.atguigu.juc;


import java.util.concurrent.TimeUnit;

/**
 * @author star
 * @create 2019-03-26 19:48
 */
public class LockTest {
    public static void main(String[] args) {
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();
        new Thread(()->{phone1.sendEmail();},"a").start();

        try {
            TimeUnit.MICROSECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{phone2.sendMs();},"b").start();
    }
}


/* 1    标准访问，请问先打印邮件还是短信？
 2    暂停4秒钟在邮件方法里面，请问先打印邮件还是短信？
 3    新增普通hello方法，请问先打印邮件还是hello？
 4    两部手机，请问先打印邮件还是短信？
 5    两个静态同步方法，1部手机，请问先打印邮件还是短信？
 6    两个静态同步方法，2部手机，请问先打印邮件还是短信？
 7    1个静态同步方法，1个普通同步方法，1部手机，请问先打印邮件还是短信？
 8    1个静态同步方法，1个普通同步方法，2部手机，请问先打印邮件还是短信？*/
class Phone{

    public synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("sendEmail.........");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMs() {
        System.out.println("sendMs.........");
    }

}