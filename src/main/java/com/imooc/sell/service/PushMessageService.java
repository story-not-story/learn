package com.imooc.sell.service;

import com.imooc.sell.Data2Object.Order;

public interface PushMessageService {
    void orderStatus(Order order);
}
