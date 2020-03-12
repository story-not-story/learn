package com.imooc.sell.repository;

import com.imooc.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ProductInfoRepository interface
 *
 * @author hujun
 * @date 2020/02/05
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    /**
     * 按照商品状态查找商品详情
     *
     * @param status
     * @return List<ProductInfo>
     */
    List<ProductInfo> findByStatus(Integer status);
}
