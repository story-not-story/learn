package com.imooc.sell.util;


import java.util.Random;

public class KeyUtil {
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        int i = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(i);
    }
}
