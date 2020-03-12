package com.imooc.sell.repository;

import com.imooc.sell.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId("1345");
        Assert.assertNotEquals(0, orderDetailList.size());
    }

    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail("002", "1345", "1355", "皮蛋瘦肉粥", BigDecimal.valueOf(8.5), 10, "http://1.jpg");
        Assert.assertNotNull(orderDetailRepository.save(orderDetail));
    }
}