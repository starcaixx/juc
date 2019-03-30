package com.atguigu.jvm;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author star
 * @create 2019-03-27 18:27
 */
public class TestStackOverflowError {
    public static void main(String[] args) {
//        test1();//Exception in thread "main" java.lang.StackOverflowError
//        test2();
//        test3();
        URL resource = TestStackOverflowError.class.getClassLoader().getResource("test.propetties");
        System.out.println(resource);
    }

    private static void test3() {
        //        InputStream resourceAsStream = TestStackOverflowError.class.getClassLoader().getResourceAsStream("com/sun/corba/se/impl/logging/LogStrings.properties");
        InputStream resourceAsStream = TestStackOverflowError.class.getClassLoader().getResourceAsStream("src/test.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
            Enumeration<?> enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                Object element = enumeration.nextElement();
                System.out.println(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void test2() {
        ClassLoader classLoader = new TestStackOverflowError().getClass().getClassLoader();
        System.out.println(classLoader);
        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent());
        System.out.println("=========================");
        System.out.println();
        ClassLoader classLoader1 = TestStackOverflowError.class.getClassLoader();
        System.out.println(classLoader);

        System.out.println("=======================");
        InputStream resourceAsStream = classLoader.getResourceAsStream("src/test.properties");
//        Thread.currentThread().getContextClassLoader.getResourceAsStream("confg.properties")

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.properties");
        Properties properties = new Properties();
        try {
            System.out.println(inputStream.available());
            properties.load(resourceAsStream);
            Enumeration<String> pns = (Enumeration<String>) properties.propertyNames();
            while(pns.hasMoreElements()) {
                String name = pns.nextElement();
                System.out.println(name + "---" + properties.getProperty(name));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("============");
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);

        System.out.println("=================");
    }

    public static void test1() {
        test1();
    }
}
