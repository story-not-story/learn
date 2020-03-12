package com.imooc.sell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class SellerInfo {
    @Id
    private String sellerId;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String openid;
}
