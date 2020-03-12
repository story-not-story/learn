package com.imooc.sell.controller;


import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.entity.ProductCategory;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/category")
public class SellerCategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProjectUrlConfig urlConfig;
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<ProductCategory> categoryPage = categoryService.findAll(pageRequest);
        Map<String, Object> map = new HashMap<>();
        map.put("categoryPage", categoryPage);
        map.put("url", urlConfig.getSell() + "/category");
        return new ModelAndView("category/list", map);
    }
    @PostMapping("/save")
    public ModelAndView save(ProductCategory category){
        categoryService.save(category);
        return new ModelAndView("redirect:" + urlConfig.getSell() + "/category/list");
    }
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(required = false) Integer categoryId){
        ProductCategory category = new ProductCategory();
        if (categoryId != null){
            category = categoryService.findOne(categoryId);
            if (category == null){
                log.error("【类目展示】类目不存在");
                throw new SellException(ErrorCode.CATEGORY_NOT_EXISTS);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("url", urlConfig.getSell() + "/category/save");
        map.put("category", category);
        return new ModelAndView("category/index", map);
    }
}
