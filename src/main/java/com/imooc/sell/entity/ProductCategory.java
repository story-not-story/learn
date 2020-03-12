package com.imooc.sell.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * ProductCategory class
 *
 * @author hujun
 * @date 2020/02/05
 */
@Entity
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ProductCategory {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** 类目id */
    private Integer id;

    @Column(name = "category_name")
    /** 类目名称 */
    private String name;

    @Column(name = "category_type")
    /** 类目编号 */
    private Integer type;

    private Date createTime;

    private Date updateTime;

    public ProductCategory(String name, Integer type) {
        this.name = name;
        this.type = type;
    }
}
