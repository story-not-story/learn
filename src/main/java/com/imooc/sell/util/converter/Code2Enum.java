package com.imooc.sell.util.converter;

import com.imooc.sell.enums.CodeEnum;

public class Code2Enum {
    public static <T extends CodeEnum> T convert(Integer code, Class<T> className){
        for (T each : className.getEnumConstants()) {
            if (each.getCode().equals(code)){
                return each;
            }
        }
        return null;
    }
}
