package com.imooc.sell.exception;

import com.imooc.sell.enums.ErrorCode;
import lombok.Getter;

@Getter
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public SellException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
