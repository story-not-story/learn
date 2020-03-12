package com.imooc.sell.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Data
public class OrderForm {
    @JsonProperty(value = "name")
    @NotNull(message = "买家姓名必填")
   private String buyerName;

    @JsonProperty(value = "phone")
    @NotNull(message = "买家电话必填")
    private String buyerPhone;

    @JsonProperty(value = "address")
    @NotNull(message = "买家地址必填")
    private String buyerAddress;

    @JsonProperty(value = "openid")
    @NotNull(message = "买家openid必填")
    private String buyerOpenid;

    @NotNull(message = "购物车")
    private String items;
}
