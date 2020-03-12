package com.imooc.sell.service;

import com.imooc.sell.Data2Object.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {
    Order create(Order order) throws Exception;
    Order findOne(String orderId, Pageable pageable) throws Exception;
    Order findOne(String orderId) throws Exception;
    Page<Order> findByBuyerOpenid(String buyerOpenid, Pageable pageable) throws Exception;
    Page<Order> findAll(Pageable pageable) throws Exception;
    Order pay(Order order) throws Exception;
    Order finish(Order order) throws Exception;
    Order cancel(Order order) throws Exception;
}
