package com.imooc.sell.service.impl;

import com.imooc.sell.Data2Object.Order;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.enums.PayStatus;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.exception.WechatException;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.imooc.sell.util.JsonUtil;
import com.imooc.sell.util.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.exception.BestPayException;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.model.wxpay.response.WxPayAsyncResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.utils.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {
    @Autowired
    private BestPayService bestPayService;
    @Autowired
    private OrderService orderService;
    private final String WX_ORDER = "微信点餐";

    @Override
    public PayResponse create(String orderId) throws Exception {
        Order order = orderService.findOne(orderId);
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(order.getBuyerOpenid());
        payRequest.setOrderAmount(order.getOrderAmount().doubleValue());
        payRequest.setOrderId(order.getOrderId());
        payRequest.setOrderName(WX_ORDER);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        try{
            log.info("【微信支付】发起支付, request={}", JsonUtil.toJson(payRequest));
            PayResponse payResponse = bestPayService.pay(payRequest);
            log.info("【微信支付】发起支付, response={}", JsonUtil.toJson(payResponse));
            return payResponse;
        }catch (Exception e){
            log.error("【微信支付】获取预付单失败");
            throw new WechatException(ErrorCode.PRE_ORDER_FAIL.getCode(), e.getMessage());
        }
    }

    @Override
    public PayResponse asyncNotify(String notify_data) throws Exception {
        PayResponse payResponse = null;
        String openid = null;
        try{
            payResponse = bestPayService.asyncNotify(notify_data);
            log.info("【微信支付】异步通知, payResponse={}", JsonUtil.toJson(payResponse));
            openid = ((WxPayAsyncResponse) XmlUtil.fromXML(notify_data, WxPayAsyncResponse.class)).getOpenid();
        }catch (Exception e){
            log.error("【微信支付】获取预付单失败");
            throw new WechatException(ErrorCode.NOTIFY_FAIL.getCode(), e.getMessage());
        }
        Order order = orderService.findOne(payResponse.getOrderId());
        if (!MathUtil.isEqual(order.getOrderAmount().doubleValue(), payResponse.getOrderAmount())){
            log.error("【微信支付】异步通知, 订单金额不一致, orderId={}, 微信通知金额={}, 系统金额={}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    order.getOrderAmount());
            throw new WechatException((ErrorCode.ORDER_AMOUNT_ERROR));
        } else if (!order.getBuyerOpenid().equals(openid)){
            log.error("【微信支付】异步通知, 订单金额不一致, 通知下单人openid={}, 实际下单人openid={}",openid, order.getBuyerOpenid());
            throw new WechatException(ErrorCode.OPENID_ERROR);
        }
        orderService.pay(order);
        return payResponse;
    }

    @Override
    public RefundResponse refund(String orderId) throws Exception {
        Order order = orderService.findOne(orderId);
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(order.getOrderAmount().doubleValue());
        refundRequest.setOrderId(order.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        try{
            log.info("【微信退款】request={}", JsonUtil.toJson(refundRequest));
            RefundResponse refundResponse = bestPayService.refund(refundRequest);
            log.info("【微信退款】response={}", JsonUtil.toJson(refundResponse));
            return refundResponse;
        }catch (Exception e){
            log.error("【微信支付】获取预付单失败");
            throw new WechatException(ErrorCode.REFUND_FAIL.getCode(), e.getMessage());
        }
    }
}
