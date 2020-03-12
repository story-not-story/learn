package com.imooc.sell.service.impl;

import com.imooc.sell.entity.SellerInfo;
import com.imooc.sell.repository.SellerInfoRepository;
import com.imooc.sell.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerInfoServiceImpl implements SellerInfoService{
    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }

    @Override
    public SellerInfo save(SellerInfo sellerInfo) {
        return sellerInfoRepository.save(sellerInfo);
    }
}
