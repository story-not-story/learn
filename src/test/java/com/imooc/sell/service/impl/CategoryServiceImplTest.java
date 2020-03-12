package com.imooc.sell.service.impl;

import com.imooc.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Test
    public void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertNotNull(productCategory);
        Assert.assertEquals(Integer.valueOf(1), productCategory.getId());

    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    public void findByTypeIn() {
        List<ProductCategory> productCategoryList = categoryService.findByTypeIn(Arrays.asList(1, 22, 29));
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    @Transactional
    public void insert() {
        ProductCategory productCategory = new ProductCategory("eraser", 11);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);
    }
}