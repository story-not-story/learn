package com.imooc.sell.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
    public static JsonObject toObject(String str){
        Gson gson = new Gson();
        try{
            return gson.fromJson(str, JsonObject.class);
        }catch (JsonSyntaxException e){
            log.error("【转换json】json格式错误");
            throw new SellException(ErrorCode.PARAM_ERROR);
        }
    }
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        try{
             return gson.toJson(object);
        }catch (JsonSyntaxException e){
            log.error("【转换json】json格式错误");
            throw new SellException(ErrorCode.PARAM_ERROR);
        }
    }
}
