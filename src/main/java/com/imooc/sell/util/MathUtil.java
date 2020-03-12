package com.imooc.sell.util;

public class MathUtil {
    private static final double DISTANCE = 0.0001;
    public static boolean isEqual(Double d1, Double d2){
        if (Math.abs(d1 - d2) < DISTANCE){
            return true;
        }
        return false;
    }
}
