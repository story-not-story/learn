package com.imooc.sell.service;

import com.imooc.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * ProductService interface
 *
 * @author hujun
 * @date 2020/02/05
 */
public interface ProductService {
    /**
     * 按照id查找商品详情
     *
     * @param id
     * @return ProductInfo
     */
    ProductInfo findOne(String id);
    /**
     * 所有上架商品详情
     *
     * @return List<ProductInfo>
     */
    List<ProductInfo> findUpAll();
    /**
     * 按照分页查找商品详情
     *
     * @param pageable
     * @return Page<ProductInfo>
     */
    Page<ProductInfo> findAll(Pageable pageable);
    /**
     * 保存商品详情
     *
     * @param productInfo
     * @return ProductInfo
     */
    ProductInfo save(ProductInfo productInfo);
    ProductInfo offSale(String id);
    ProductInfo onSale(String id);

    void increase(List<ProductInfo> productInfoList);

    void decrease(List<ProductInfo> productInfoList) throws Exception;
}
