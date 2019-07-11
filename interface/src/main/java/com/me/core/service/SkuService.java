package com.me.core.service;

import com.me.core.pojo.product.Sku;

import java.util.List;

public interface SkuService {
    public List<Sku> selectSkuListByProduct(Long productId);
    public void updateSkuById(Sku sku);
}
