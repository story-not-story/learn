package com.imooc.sell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNKNOWN_ERROR(0, "未知错误"),
    NO_UP_PRODUCT(1, "无上架商品"),
    PRODUCT_NOT_EXISTS(2, "商品不存在"),
    STOCK_ERROR(3, "库存错误"),
    ORDER_NOT_EXISTS(4, "主订单不存在"),
    ORDER_DETAIL_NOT_EXISTS(5, "订单详情不存在"),
    ORDER_STATUS_ERROR(6, "订单状态错误"),
    ORDER_CANCEL_FAIL(7, "订单取消失败"),
    PARAM_ERROR(8, "参数错误"),
    ORDER_UPDATE_FAIL(9, "主订单更新失败"),
    ORDER_DETAIL_UPDATE_FAIL (10, "订单详情更新失败"),
    PAY_STATUS_ERROR(11, "订单支付状态错误"),
    PAY_STATUS_UPDATE_FAIL(12, "支付状态更新失败"),
    ORDER_STATUS_UPDATE_FAIL(13, "订单状态更新失败"),
    OWNER_ERROR(14, "该用户不是当前用户"),
    USER_NOT_EXISTS(15, "用户未注册"),
    USER_ALREADY_EXISTS(16, "用户已存在"),
    USER_NOT_LOGIN(17, "用户未登录"),
    USER_ALREADY_LOGIN(18, "用户已登录"),
    CATEGORY_NOT_EXISTS(19, "类目不存在"),
    PRODUCT_ALREDY_DOWN(20, "商品已下架"),
    PRODUCT_ALREDY_UP(21, "商品已上架"),
    OPENID_FAIL(22, "获取openid失败"),
    CODE_FAIL(23, "获取code失败"),
    REFRESH_FAIL(24, "刷新access_token失败"),
    USER_INFO_FAIL(25, "用户信息获取失败"),
    PRE_ORDER_FAIL(26, "获取微信预付订单失败"),
    REFUND_FAIL(27,"微信退款失败"),
    NOTIFY_FAIL(28, "微信异步通知支付结果失败"),
    ORDER_AMOUNT_ERROR(29, "微信异步通知金额校验不通过"),
    OPENID_ERROR(30, "微信异步通知openid校验不通过");
    private Integer code;
    private String msg;

}
