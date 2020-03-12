package com.imooc.sell.enums;

import com.imooc.sell.util.converter.Code2Enum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aspectj.apache.bcel.classfile.Code;

@Getter
@AllArgsConstructor
public enum ProductStatus implements CodeEnum {
    /**
     * 商品在架状态
     */
    UP(0, "在架"),
    /**
     * 商品下架状态
     */
    DOWN(1, "下架");
    private Integer code;
    private String msg;
}
