package com.imooc.sell.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.sell.Data2Object.Order;
import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderForm;
import com.imooc.sell.result.Result;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.util.ResultUtil;
import com.imooc.sell.util.converter.OrderForm2Order;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/buyer/order")
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @PostMapping("/create")
    public Result create(@Valid OrderForm orderForm, BindingResult bindingResult){
        try {
            if (bindingResult.hasErrors()){
                log.error("接口参数错误");
                throw new SellException(ErrorCode.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
            }
            Map<String, String> map = new HashMap<>();
            map.put("orderId", orderService.create(OrderForm2Order.convert(orderForm)).getOrderId());
            return ResultUtil.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof SellException){
                return ResultUtil.error(((SellException) e));
            } else {
                return ResultUtil.error(ErrorCode.UNKNOWN_ERROR.getCode(), e.getMessage());
            }
        }
    }

    @GetMapping("/detail")
    public Result find(@RequestParam String openid, @RequestParam(name = "orderId") String orderId){
        try {
            if (buyerService.checkOrderOwner(openid, orderId)){
                return ResultUtil.success(orderService.findOne(orderId));
            } else {
                return ResultUtil.error(ErrorCode.OWNER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof SellException){
                return ResultUtil.error(((SellException) e));
            } else {
                return ResultUtil.error(ErrorCode.UNKNOWN_ERROR.getCode(), e.getMessage());
            }
        }
    }

    @GetMapping("/list")
    public Result findList(@RequestParam String openid, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size){
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            return ResultUtil.success(orderService.findByBuyerOpenid(openid, pageable).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof SellException){
                return ResultUtil.error(((SellException) e));
            } else {
                return ResultUtil.error(ErrorCode.UNKNOWN_ERROR.getCode(), e.getMessage());
            }
        }
    }
}
