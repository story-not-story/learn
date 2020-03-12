package com.imooc.sell.service.impl;

import com.imooc.sell.entity.OrderMaster;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.OrderMasterRepository;
import com.imooc.sell.service.BuyerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Override
    public boolean checkOrderOwner(String openId, String orderId) {
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);
        if (!orderMasterOptional.isPresent()){
            log.error("【用户校验】主订单不存在");
            throw new SellException(ErrorCode.ORDER_NOT_EXISTS);
        }
        if (openId.equals(orderMasterOptional.get().getBuyerOpenid())){
            return true;
        }
        return false;
    }
}
