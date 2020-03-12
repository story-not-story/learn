package com.imooc.sell.enums;

import com.imooc.sell.util.converter.Code2Enum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatus implements CodeEnum{
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功");
    private Integer code;
    private String msg;
}
