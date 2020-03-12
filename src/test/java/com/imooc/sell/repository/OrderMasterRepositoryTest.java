package com.imooc.sell.repository;

import com.imooc.sell.entity.OrderMaster;
import com.imooc.sell.enums.OrderStatus;
import com.imooc.sell.enums.PayStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Test
    public void findByBuyerOpenid() throws Exception{
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid("111", pageRequest);
        Assert.assertNotEquals(0, orderMasterPage.getTotalElements());
        System.out.println(orderMasterPage.getTotalElements());
    }

    @Test
    public void save() throws Exception{
        OrderMaster orderMaster = new OrderMaster("1348", "hujun", "15294515333", "浙江省温州市", "111", BigDecimal.valueOf(669), OrderStatus.NEW.getCode(), PayStatus.WAIT.getCode());
        Assert.assertNotNull(orderMasterRepository.save(orderMaster));
    }
}