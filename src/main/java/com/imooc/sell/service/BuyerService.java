package com.imooc.sell.service;

public interface BuyerService {
    boolean checkOrderOwner(String openId, String ownerId);
}
