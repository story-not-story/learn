package com.imooc.sell.service.impl;


import com.imooc.sell.Data2Object.Order;
import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.entity.OrderMaster;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.enums.OrderStatus;
import com.imooc.sell.enums.PayStatus;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.OrderDetailRepository;
import com.imooc.sell.repository.OrderMasterRepository;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.util.KeyUtil;
import com.imooc.sell.util.converter.OrderMaster2Order;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public Order create(Order order) throws Exception{
        String orderId = KeyUtil.getUniqueKey();
        BigDecimal cost = new BigDecimal(0);
        List<ProductInfo> productInfoList = new ArrayList<>();
        for (OrderDetail orderDetail : order.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null){
                log.error("【创建订单】商品不存在");
                throw new SellException(ErrorCode.PRODUCT_NOT_EXISTS);
            }
            cost = productInfo.getPrice().multiply(BigDecimal.valueOf(orderDetail.getProductQuantity())).add(cost);
            orderDetail.setProductIcon(productInfo.getIcon());
            orderDetail.setProductName(productInfo.getName());
            orderDetail.setProductPrice(productInfo.getPrice());
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            OrderDetail result = orderDetailRepository.save(orderDetail);
            if (result == null){
                log.error("【创建订单】订单详情更新失败");
                throw new SellException(ErrorCode.ORDER_DETAIL_UPDATE_FAIL);
            }
            productInfoList.add(productInfo);
        }
        order.setOrderStatus(OrderStatus.NEW.getCode());
        order.setPayStatus(PayStatus.WAIT.getCode());
        order.setOrderAmount(cost);
        order.setOrderId(orderId);
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(order, orderMaster);//注意null值也会拷贝
        OrderMaster masterResult = orderMasterRepository.save(orderMaster);
        if (masterResult == null){
            log.error("【创建订单】主订单更新失败");
            throw new SellException(ErrorCode.ORDER_UPDATE_FAIL);
        }
        productService.decrease(productInfoList);
        return order;
    }

    @Override
    public Order findOne(String orderId, Pageable pageable) throws Exception{
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);
        if (!orderMasterOptional.isPresent()){
            log.error("【查找订单】主订单不存在");
            throw new SellException(ErrorCode.ORDER_NOT_EXISTS);
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderMasterOptional.get(), order);
        Page<OrderDetail> detailPage = orderDetailRepository.findByOrderId(orderId, pageable);
        if (!detailPage.hasContent()){
            log.error("【查找订单】订单详情不存在");
            throw new SellException(ErrorCode.ORDER_DETAIL_NOT_EXISTS);
        }
        order.setTotalPages(detailPage.getTotalPages());
        order.setOrderDetailList(detailPage.getContent());
        return order;
    }

    @Override
    public Order findOne(String orderId) throws Exception{
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);
        if (!orderMasterOptional.isPresent()){
            log.error("【查找订单】主订单不存在");
            throw new SellException(ErrorCode.ORDER_NOT_EXISTS);
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderMasterOptional.get(), order);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList.isEmpty()){
            log.error("【查找订单】订单详情不存在");
            throw new SellException(ErrorCode.ORDER_DETAIL_NOT_EXISTS);
        }
        order.setOrderDetailList(orderDetailList);
        return order;
    }

    @Override
    public Page<Order> findByBuyerOpenid(String buyerOpenid, Pageable pageable) throws Exception{
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<Order> orderList = OrderMaster2Order.convert(orderMasterPage.getContent());
        orderList.forEach(new Consumer<Order>() {
            @Override
            public void accept(Order order) {
                order.setOrderDetailList(orderDetailRepository.findByOrderId(order.getOrderId()));
                if (order.getOrderDetailList().isEmpty()){
                    log.error("【根据openId查找订单】订单详情不存在");
                    throw new SellException(ErrorCode.ORDER_DETAIL_NOT_EXISTS);
                }
            }
        });
        return new PageImpl<Order>(orderList, orderMasterPage.getPageable(), orderMasterPage.getTotalElements());
    }

    @Override
    public Page<Order> findAll(Pageable pageable) throws Exception{
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<Order> orderList = OrderMaster2Order.convert(orderMasterPage.getContent());
        orderList.forEach(new Consumer<Order>() {
            @Override
            public void accept(Order order) {
                order.setOrderDetailList(orderDetailRepository.findByOrderId(order.getOrderId()));
                if (order.getOrderDetailList().isEmpty()){
                    log.error("【查找订单】订单详情不存在");
                    throw new SellException(ErrorCode.ORDER_DETAIL_NOT_EXISTS);
                }
            }
        });
        return new PageImpl<Order>(orderList, orderMasterPage.getPageable(), orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public Order pay(Order order) throws Exception{
        if (!OrderStatus.NEW.getCode().equals(order.getOrderStatus())){
            log.error("【支付订单】订单状态错误");
            throw new SellException(ErrorCode.ORDER_STATUS_ERROR);
        }
        if (!PayStatus.WAIT.getCode().equals(order.getPayStatus())){
            log.error("【支付订单】支付状态错误");
            throw new SellException(ErrorCode.PAY_STATUS_ERROR);
        }
        order.setPayStatus(PayStatus.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(order, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result == null){
            log.error("【支付订单】更新支付状态失败");
            throw new SellException(ErrorCode.PAY_STATUS_UPDATE_FAIL);
        }
        return order;
    }

    @Override
    @Transactional
    public Order finish(Order order) throws Exception{
        if (!OrderStatus.NEW.getCode().equals(order.getOrderStatus())){
            log.error("【完结订单】订单状态错误");
            throw new SellException(ErrorCode.ORDER_STATUS_ERROR);
        }
        order.setOrderStatus(OrderStatus.FINISH.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(order, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result == null){
            log.error("【完结订单】更新订单状态失败");
            throw new SellException(ErrorCode.ORDER_STATUS_UPDATE_FAIL);
        }
        return order;
    }

    @Override
    @Transactional
    public Order cancel(Order order) throws Exception{
        if (!OrderStatus.NEW.getCode().equals(order.getOrderStatus())){
            log.error("【取消订单】订单状态错误");
            throw new SellException(ErrorCode.ORDER_STATUS_ERROR);
        }
        order.setOrderStatus(OrderStatus.CANCLE.getCode());
        if (PayStatus.SUCCESS.getCode().equals(order.getPayStatus())){
            order.setPayStatus(PayStatus.WAIT.getCode());
        }
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(order, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result == null){
            log.error("【取消订单】更新订单状态失败");
            throw new SellException(ErrorCode.ORDER_STATUS_UPDATE_FAIL);
        }
        List<ProductInfo> productInfoList = order.getOrderDetailList().stream().map(new Function<OrderDetail, ProductInfo>() {
            @Override
            public ProductInfo apply(OrderDetail orderDetail) {
                return productService.findOne(orderDetail.getProductId());
            }
        }).collect(Collectors.toList());
        productService.increase(productInfoList);
        return order;
    }
}
