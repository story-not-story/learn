package com.imooc.sell.entity;

import com.imooc.sell.enums.ProductStatus;
import com.imooc.sell.util.converter.Code2Enum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@DynamicUpdate
public class ProductInfo {
    @Id
    @Column(name = "product_id")
    private String id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_price")
    private BigDecimal price;

    @Column(name = "product_stock")
    private Integer stock;

    @Column(name = "product_description")
    private String description;

    @Column(name = "product_icon")
    private String icon;

    @Column(name = "product_status")
    private Integer status;

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;
    public ProductStatus getStatusByCode(Integer code){
        return Code2Enum.convert(code, ProductStatus.class);
    }
}
