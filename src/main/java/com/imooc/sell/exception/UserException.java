package com.imooc.sell.exception;

import com.imooc.sell.enums.ErrorCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private Integer code;

    public UserException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public UserException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
