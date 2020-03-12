package com.imooc.sell.repository;

import com.imooc.sell.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    /**
     * 按照若干商品编号查找商品类目
     *
     * @param typeList
     * @return List<ProductCategory>
     */
    List<ProductCategory> findByTypeIn(List<Integer> typeList);
}
