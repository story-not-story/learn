package com.imooc.sell.repository;

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
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void saveTest(){
        Optional<ProductCategory> optional = productCategoryRepository.findById(1);
        if (optional.isPresent()){
            ProductCategory productCategory = optional.get();
            Integer id = productCategory.getId();
            Assert.assertEquals(Integer.valueOf(1), id);
            productCategory.setName("book");
            productCategoryRepository.save(productCategory);
        }
    }

    @Test
    @Transactional
    public void insertTest(){
        ProductCategory productCategory = new ProductCategory("pen", 22);
        ProductCategory result = productCategoryRepository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    public void findListTest(){
        List<Integer> typeList = Arrays.asList(1,22,29);
        List<ProductCategory> productCategoryList = productCategoryRepository.findByTypeIn(typeList);
        Assert.assertEquals(2, productCategoryList.size());
    }
}