package com.imooc.sell.service;

import com.imooc.sell.Data2Object.Order;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

public interface PayService {
    PayResponse create(String orderId) throws Exception;
    PayResponse asyncNotify(String notify_data) throws Exception;
    RefundResponse refund(String orderId) throws Exception;
}
