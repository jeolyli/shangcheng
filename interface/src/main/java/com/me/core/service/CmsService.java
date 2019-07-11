package com.me.core.service;

import com.me.core.pojo.product.Product;
import com.me.core.pojo.product.Sku;

import java.util.List;

public interface CmsService {
    public Product selectProductById(Long id);
    public List<Sku> selectSkuListByProductId(Long id);
}
