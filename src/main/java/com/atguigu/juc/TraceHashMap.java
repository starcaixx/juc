package com.atguigu.juc;

import java.util.HashMap;
import java.util.Random;

/**
 * @author star
 * @create 2019-03-30 10:20
 */
public class TraceHashMap {
    public static void main(String[] args) {
        HashMap<String,String> map = new HashMap<>();
        Random random = new Random();
        int i = 0;
        while (i < 64) {
            map.put("test"+i,"haha"+i++);
        }
        System.out.println("map:"+map);
    }
}
