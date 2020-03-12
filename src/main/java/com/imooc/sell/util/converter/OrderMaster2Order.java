package com.imooc.sell.util.converter;

import com.imooc.sell.Data2Object.Order;
import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.entity.OrderMaster;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.OrderDetailRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderMaster2Order {
    public static List<Order> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(new Function<OrderMaster, Order>() {
            @Override
            public Order apply(OrderMaster orderMaster) {
                Order order = new Order();
                BeanUtils.copyProperties(orderMaster, order);
                return order;
            }
        }).collect(Collectors.toList());
    }
}
