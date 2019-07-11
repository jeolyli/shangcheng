package com.me.core.service;

import com.me.core.dao.product.ColorDao;
import com.me.core.dao.product.ProductDao;
import com.me.core.dao.product.SkuDao;
import com.me.core.pojo.product.Product;
import com.me.core.pojo.product.Sku;
import com.me.core.pojo.product.SkuQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cmsService")
public class CmsServiceImpl implements CmsService{
    @Autowired
    private ProductDao productDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private ColorDao colorDao;


    //通过id查询商品信息
    public Product selectProductById(Long id){
        return productDao.selectByPrimaryKey(id);

    }
    //通过id查询sku表只查有货的
    public List<Sku> selectSkuListByProductId(Long id){
        SkuQuery skuQuery = new SkuQuery();
        skuQuery.createCriteria().andProductIdEqualTo(id).andStockGreaterThan(0);
        List<Sku> skus = skuDao.selectByExample(skuQuery);
        for (Sku sku: skus) {
            //通过sku对象中的颜色id查询对应的颜色表
            sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));

        }
        return skus;

    }
}
