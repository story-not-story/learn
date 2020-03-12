package com.imooc.sell.service;

import com.imooc.sell.entity.SellerInfo;

public interface SellerInfoService {
    SellerInfo findByOpenid(String openid);
    SellerInfo save(SellerInfo sellerInfo);
}
