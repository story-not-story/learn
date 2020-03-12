package com.imooc.sell.Data2Object;

import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.enums.OrderStatus;
import com.imooc.sell.enums.PayStatus;
import com.imooc.sell.util.converter.Code2Enum;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Order {
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    private Integer payStatus;

    private List<OrderDetail> orderDetailList;

    @Nullable
    private Integer totalPages;

    private Date createTime;

    private Date updateTime;
    public OrderStatus getOrderStatusEnum(){
        return Code2Enum.convert(orderStatus, OrderStatus.class);
    }
    public PayStatus getPayStatusEnum(){
        return Code2Enum.convert(payStatus, PayStatus.class);
    }
}
