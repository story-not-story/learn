package com.imooc.sell.service.impl;

import com.imooc.sell.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    ProductServiceImpl productService;
    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("1355");
        Assert.assertNotNull(productInfo);
        Assert.assertEquals("1355", productInfo.getId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0, productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        System.out.println(productInfoPage.getTotalElements());
        System.out.println(productInfoPage.getTotalPages());
    }

    @Test
    @Transactional
    public void insert() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId("1320");
        productInfo.setCategoryType(10);
        productInfo.setDescription("美味营养");
        productInfo.setIcon("http://3.jpg");
        productInfo.setName("玉米粥");
        productInfo.setPrice(BigDecimal.valueOf(9.5));
        productInfo.setStatus(0);
        productInfo.setStock(1000);
        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }
}