package com.imooc.sell.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductInfoResult implements Serializable {

    private static final long serialVersionUID = -1459436465628935282L;
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("icon")
    private String icon;
}
