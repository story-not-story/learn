package com.imooc.sell.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductForm {
    private String id;
    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer stock;

    @NotNull
    private String description;

    @NotNull
    private String icon;

    @NotNull
    private String statusMsg;
    @NotNull
    private Integer categoryType;
}
