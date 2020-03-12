package com.imooc.sell.Data2Object.mapper;

import com.imooc.sell.entity.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ProductCategoryMapper {
    @Insert("insert into product_category(category_name, category_type) values (#{Name, jdbcType=VARCHAR},#{Type,jdbcType=INTEGER})")
    int insert1(Map<String, Object> map);

    @Insert("insert into product_category(category_name, category_type) values (#{name, jdbcType=VARCHAR},#{type,jdbcType=INTEGER})")
    int insert2(ProductCategory productCategory);

    @Select("select * from product_category where category_name = #{name}")
    @Results({
            @Result(column = "category_id", property = "id"),
            @Result(column = "category_name", property = "name"),
            @Result(column = "category_type", property = "type")
    })
    List<ProductCategory> find1(ProductCategory productCategory);

    @Select("select * from product_category where category_type = #{type}")
    @Results({
            @Result(column = "category_id", property = "id"),
            @Result(column = "category_name", property = "name"),
            @Result(column = "category_type", property = "type")
    })
    ProductCategory find2(ProductCategory productCategory);

    @Select("select * from product_category where category_type = #{type}")
    @Results({
            @Result(column = "category_id", property = "id"),
            @Result(column = "category_name", property = "name"),
            @Result(column = "category_type", property = "type")
    })
    ProductCategory find3(@Param("type") Integer type);

    @Update("update product_category set category_name = #{name} where category_type = #{type}")
    int update1(ProductCategory productCategory);

    @Update("update product_category set category_name = #{name} where category_type = #{type}")
    int update2(@Param("name") String name, @Param("type") Integer type);

    @Delete("delete from product_category where category_type = #{type}")
    int delete1(ProductCategory productCategory);

    @Delete("delete from product_category where category_type = #{type}")
    int delete2(@Param("type") Integer type);

}
