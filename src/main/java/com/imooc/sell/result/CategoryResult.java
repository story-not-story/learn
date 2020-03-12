package com.imooc.sell.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CategoryResult implements Serializable {

    private static final long serialVersionUID = -6324630359034816035L;
    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private Integer type;

    @JsonProperty("foods")
    private List<ProductInfoResult> productInfoResultList;
}
