package com.imooc.sell.controller;

import com.imooc.sell.entity.ProductCategory;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.result.CategoryResult;
import com.imooc.sell.result.ProductInfoResult;
import com.imooc.sell.result.Result;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.util.ResultUtil;
import org.hibernate.annotations.Cache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    @Cacheable(cacheNames = {"product"}, key = "123", unless = "#result.getCode() != 0")
    public Result list(){
        List<ProductInfo> productInfoList = productService.findUpAll();
        List<Integer> categoryTypeList = productInfoList.stream().map(o -> o.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByTypeIn(categoryTypeList);
        List<CategoryResult> categoryResultList = new ArrayList<>();
        productCategoryList.stream().forEach(new Consumer<ProductCategory>() {
            @Override
            public void accept(ProductCategory productCategory) {
                CategoryResult categoryResult = new CategoryResult();
                List<ProductInfoResult> productInfoResultList = productInfoList.stream().filter(new Predicate<ProductInfo>() {
                    @Override
                    public boolean test(ProductInfo productInfo) {
                        return productInfo.getCategoryType().equals(productCategory.getType());
                    }
                }).map(new Function<ProductInfo, ProductInfoResult>() {
                    @Override
                    public ProductInfoResult apply(ProductInfo productInfo) {
                        ProductInfoResult productInfoResult = new ProductInfoResult();
                        BeanUtils.copyProperties(productInfo, productInfoResult);
                        return productInfoResult;
                    }
                }).collect(Collectors.toList());
                categoryResult.setProductInfoResultList(productInfoResultList);
                categoryResult.setName(productCategory.getName());
                categoryResult.setType(productCategory.getType());
                categoryResultList.add(categoryResult);
            }
        });
        return ResultUtil.success(categoryResultList);
    }
}
