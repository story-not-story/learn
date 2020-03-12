package com.imooc.sell.service.impl;

import com.imooc.sell.Data2Object.Order;
import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;
    @Test
    public void create() throws Exception {
        List<String> list = new ArrayList<String>() {{
            add("2");
            add("3");
            add("3");
            add("3");
            add("4");
        }};
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("3")) {
                list.remove(i);
                i--;
            }
        }
        log.info("List={}",list);
        Object[] element1 = {};
        Object[] element2 = {};
        log.info("booleanValue={}", element1 == element2);
        Order order = new Order();
        order.setBuyerName("qianqian");
        order.setBuyerAddress("北京");
        order.setBuyerOpenid("112");
        order.setBuyerPhone("15398723453");
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1320");
        orderDetail.setProductQuantity(708);
        orderDetailList.add(orderDetail);
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("1355");
        orderDetail1.setProductQuantity(2353);
        orderDetailList.add(orderDetail1);
        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductId("1357");
        orderDetail2.setProductQuantity(52);
        orderDetailList.add(orderDetail2);
        order.setOrderDetailList(orderDetailList);
        Order result = orderService.create(order);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getOrderId());
    }

    @Test
    public void findOne() throws Exception{
        Order result = orderService.findOne("2453");
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getOrderDetailList());
        Assert.assertNotEquals(0, result.getOrderDetailList().size());
    }

    @Test
    public void findByBuyerOpenid() throws Exception{
    }

    @Test
    public void findAll() throws Exception{
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<Order> orderPage = orderService.findAll(pageRequest);
        System.out.println(orderPage.getTotalElements());
        System.out.println(orderPage.getTotalPages());
        orderPage.getContent();
    }

    @Test
    public void pay() throws Exception{
    }

    @Test
    public void finish() throws Exception{
    }

    @Test
    public void cancel() throws Exception{
    }
}