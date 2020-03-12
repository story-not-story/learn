package com.imooc.sell.util;

import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.result.Result;

/**
 * ResultUtil class
 *
 * @author hujun
 * @date 2020/02/05
 */
public class ResultUtil {
    public static Result success(Object data){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    public static Result success(){
        return success(null);
    }

    public static Result error(ErrorCode errorCode){
        Result result = new Result();
        result.setCode(errorCode.getCode());
        result.setMsg(errorCode.getMsg());
        return result;
    }

    public static Result error(SellException e){
        Result result = new Result();
        result.setCode(e.getCode());
        result.setMsg(e.getMessage());
        return result;
    }
}
