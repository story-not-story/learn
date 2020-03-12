package com.imooc.sell.Data2Object.mapper;

import com.imooc.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Test
    public void insert1() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("Name", "peach");
        map.put("Type", 54);
        Assert.assertEquals(1, productCategoryMapper.insert1(map));
    }

    @Test
    public void insert2() {
        ProductCategory productCategory = new ProductCategory("strawberry", 52);
        Assert.assertEquals(1, productCategoryMapper.insert2(productCategory));
    }

    @Test
    public void find1(){
        ProductCategory productCategory = new ProductCategory("strawberry", 51);
        List<ProductCategory> list = productCategoryMapper.find1(productCategory);
        Assert.assertNotNull(list);
        Assert.assertNotEquals(0, list.size());
        list.forEach(o -> System.out.println(o));
    }

    @Test
    public void find2(){
        ProductCategory productCategory = new ProductCategory("strawberry", 51);
        ProductCategory productCategory1 = productCategoryMapper.find2(productCategory);
        Assert.assertNotNull(productCategory1);
        System.out.println(productCategory1);
    }

    @Test
    public void find3(){
        ProductCategory productCategory1 = productCategoryMapper.find3(51);
        Assert.assertNotNull(productCategory1);
        System.out.println(productCategory1);
    }

    @Test
    public void update1(){
        ProductCategory productCategory = new ProductCategory("bread", 51);
        Assert.assertEquals(1, productCategoryMapper.update1(productCategory));
    }

    @Test
    public void update2(){
        Assert.assertEquals(1, productCategoryMapper.update2("nomango", 354));
    }

    @Test
    public void delete1(){
        ProductCategory productCategory = new ProductCategory("pen", 22);
        Assert.assertEquals(1, productCategoryMapper.delete1(productCategory));
    }

    @Test
    public void delete2(){
        Assert.assertEquals(1, productCategoryMapper.delete2(29));
    }
}