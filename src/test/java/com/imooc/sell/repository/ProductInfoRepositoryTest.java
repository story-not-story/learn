package com.imooc.sell.repository;

import com.imooc.sell.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    @Transactional
    public void insertTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId("1357");
        productInfo.setCategoryType(11);
        productInfo.setDescription("美味营养");
        productInfo.setIcon("http://2.jpg");
        productInfo.setName("八宝粥");
        productInfo.setPrice(BigDecimal.valueOf(9));
        productInfo.setStatus(0);
        productInfo.setStock(1000);
        ProductInfo result = productInfoRepository.save(productInfo);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByStatus() {
        List<ProductInfo> productInfoList = productInfoRepository.findByStatus(0);
        Assert.assertNotEquals(0, productInfoList.size());
    }
}