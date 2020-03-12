package com.imooc.sell.service.impl;

import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.enums.ProductStatus;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.ProductInfoRepository;
import com.imooc.sell.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findOne(String id) {
        Optional<ProductInfo> productInfo = productInfoRepository.findById(id);
        return productInfo.orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByStatus(ProductStatus.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public ProductInfo offSale(String id) {
        ProductInfo productInfo = findOne(id);
        if (productInfo == null){
            log.error("【商品下架】商品不存在");
            throw new SellException(ErrorCode.PRODUCT_NOT_EXISTS);
        }
        if (productInfo.getStatus().equals(ProductStatus.DOWN.getCode())){
            log.error("【商品下架】商品已下架");
            throw new SellException(ErrorCode.PRODUCT_ALREDY_DOWN);
        }
        productInfo.setStatus(ProductStatus.DOWN.getCode());
        productInfoRepository.save(productInfo);
        return productInfo;
    }

    @Override
    @Transactional
    public ProductInfo onSale(String id) {
        ProductInfo productInfo = findOne(id);
        if (productInfo == null){
            log.error("【商品上架】商品不存在");
            throw new SellException(ErrorCode.PRODUCT_NOT_EXISTS);
        }
        if (productInfo.getStatus().equals(ProductStatus.UP.getCode())){
            log.error("【商品上架】商品已上架");
            throw new SellException(ErrorCode.PRODUCT_ALREDY_UP);
        }
        productInfo.setStatus(ProductStatus.UP.getCode());
        productInfoRepository.save(productInfo);
        return productInfo;
    }

    @Override
    @Transactional
    public void decrease(List<ProductInfo> productInfoList) throws Exception{
        for (ProductInfo productInfo : productInfoList) {
            Integer stock = productInfo.getStock();
            if (stock - 1 < 0){
                throw new SellException(ErrorCode.STOCK_ERROR);
            }
            productInfo.setStock(stock - 1);
        }
    }

    @Override
    @Transactional
    public void increase(List<ProductInfo> productInfoList) {
        for (ProductInfo productInfo : productInfoList) {
            productInfo.setStock(productInfo.getStock() + 1);
        }
    }
}
