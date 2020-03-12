package com.imooc.sell.controller;

import com.imooc.sell.Data2Object.Order;
import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.enums.ProductStatus;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.ProductForm;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProjectUrlConfig urlConfig;
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        Map<String, Object> map = new HashMap<>();
        map.put("url", urlConfig.getSell() + "/product");
        map.put("productPage", productInfoPage);
        return new ModelAndView("product/list", map);
    }
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm){
        productService.save(productForm);
        return new ModelAndView("redirect:" + urlConfig.getSell() +  "/product/list");
    }
    @GetMapping("/offsale")
    public ModelAndView offsale(@RequestParam String productId){
        productService.offSale(productId);
        return new ModelAndView("redirect:" + urlConfig.getSell() +  "/product/list");
    }
    @GetMapping("/onsale")
    public ModelAndView onsale(@RequestParam String productId){
        productService.onSale(productId);
        return new ModelAndView("redirect:" + urlConfig.getSell() +  "/product/list");
    }
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(required = false) String productId){
        ProductInfo productInfo = new ProductInfo();
        if (productId != null){
            productInfo = productService.findOne(productId);
            if (productInfo == null){
                log.error("【查看商品】商品不存在");
                throw new SellException(ErrorCode.PRODUCT_NOT_EXISTS);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("url", urlConfig.getSell() + "/product/save");
        map.put("product", productInfo);
        return new ModelAndView("/product/index", map);
    }
}
