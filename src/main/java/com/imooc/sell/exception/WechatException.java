package com.imooc.sell.exception;

import com.imooc.sell.enums.ErrorCode;

public class WechatException extends RuntimeException  {
    private Integer code;

    public WechatException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public WechatException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
