package com.imooc.sell.util.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.sell.Data2Object.Order;
import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Slf4j
public class OrderForm2Order {
    public static Order convert(OrderForm orderForm){
        Gson gson = new Gson();
        List<OrderDetail> orderDetailList = null;
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        } catch (Exception e){
            e.printStackTrace();
            log.error("接口参数错误");
            throw new SellException(ErrorCode.PARAM_ERROR);
        }
        Order order = new Order();
        order.setBuyerName(orderForm.getName());
        order.setBuyerAddress(orderForm.getAddress());
        order.setBuyerPhone(orderForm.getPhone());
        order.setBuyerOpenid(orderForm.getOpenid());
        order.setOrderDetailList(orderDetailList);
        return order;
    }
}
