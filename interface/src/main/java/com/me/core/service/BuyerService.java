package com.me.core.service;

import com.me.core.pojo.BuyerCart;
import com.me.core.pojo.product.Sku;
import com.me.core.pojo.user.Buyer;

public interface BuyerService {
    //通过用户名查询用户信息
    public Buyer selectBuyerByUsername(String username);
    //将购物车保存在redis中
    public void insertBuyerCartToRedis(BuyerCart buyerCart, String username);
    //从redis中取出购物车
    public BuyerCart selectBuyerCartFromRedis(String username);
    //通过skuid查询sku对象
    public Sku selectSkuBuId(Long skuId);

}
