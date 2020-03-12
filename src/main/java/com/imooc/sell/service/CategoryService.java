package com.imooc.sell.service;

import com.imooc.sell.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    /**
     * 按照id查找商品类目
     *
     * @param id
     * @return ProductCategory
     */
    ProductCategory findOne(Integer id);
    /**
     * 所有商品类目
     *
     * @return List<ProductCategory>
     */
    List<ProductCategory> findAll();
    Page<ProductCategory> findAll(Pageable pageable);
    /**
     * 按照若干商品编号查找商品类目
     *
     * @param typeList
     * @return List<ProductCategory>
     */
    List<ProductCategory> findByTypeIn(List<Integer> typeList);
    /**
     * 保存商品类目
     *
     * @param productCategory
     * @return ProductCategory
     */
    ProductCategory save(ProductCategory productCategory);
}
