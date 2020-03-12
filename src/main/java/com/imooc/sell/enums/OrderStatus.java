package com.imooc.sell.enums;

import com.imooc.sell.util.converter.Code2Enum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus implements CodeEnum {
    NEW(0, "新订单"),
    FINISH(1, "已完结"),
    CANCLE(2, "已取消");
    private Integer code;
    private String msg;
}
