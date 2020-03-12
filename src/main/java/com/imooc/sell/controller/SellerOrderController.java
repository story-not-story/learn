package com.imooc.sell.controller;

import com.imooc.sell.Data2Object.Order;
import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/order")
public class SellerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProjectUrlConfig urlConfig;
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int size) throws Exception {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Order> orderPage = orderService.findAll(pageRequest);
        Map<String, Object> map = new HashMap<>();
        map.put("url", urlConfig.getSell() + "/order");
        map.put("orderPage", orderPage);
        return new ModelAndView("order/list", map);
    }
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam String orderId) throws Exception {
        Order order = orderService.findOne(orderId);
        if (order == null){
            log.error("【取消订单】订单不存在");
            throw new SellException(ErrorCode.ORDER_NOT_EXISTS);
        }
        orderService.cancel(order);
        return new ModelAndView("redirect:" + urlConfig.getSell() + "/order/list");
    }
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam String orderId) throws Exception {
        Order order = orderService.findOne(orderId);
        if (order == null){
            log.error("【取消订单】订单不存在");
            throw new SellException(ErrorCode.ORDER_NOT_EXISTS);
        }
        orderService.finish(order);
        return new ModelAndView("redirect:" + urlConfig.getSell() + "/order/list");
    }
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam String orderId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int size) throws Exception {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Order order = orderService.findOne(orderId, pageRequest);
        if (order == null){
            log.error("【取消订单】订单不存在");
            throw new SellException(ErrorCode.ORDER_NOT_EXISTS);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", page);
        map.put("size", size);
        map.put("url", urlConfig.getSell() + "/order/detail");
        map.put("order", order);
        return new ModelAndView("order/detail", map);
    }
}
