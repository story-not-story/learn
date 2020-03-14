package com.imooc.sell.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Data
public class OrderForm {
    
    @NotNull(message = "买家姓名必填")
   private String name;

    
    @NotNull(message = "买家电话必填")
    private String phone;

    
    @NotNull(message = "买家地址必填")
    private String address;

    
    @NotNull(message = "买家openid必填")
    private String openid;

    @NotNull(message = "购物车")
    private String items;
}
