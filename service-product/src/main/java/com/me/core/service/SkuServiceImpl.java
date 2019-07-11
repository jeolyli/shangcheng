package com.me.core.service;

import com.me.core.dao.product.ColorDao;
import com.me.core.dao.product.SkuDao;
import com.me.core.pojo.product.Sku;
import com.me.core.pojo.product.SkuQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("skuService")
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuDao skuDao;
    @Autowired
    private ColorDao colorDao;

    //通过商品id  查询SKU结果集
    public List<Sku> selectSkuListByProduct(Long productId){
        SkuQuery skuQuery = new SkuQuery();
        skuQuery.createCriteria().andProductIdEqualTo(productId);
        List<Sku> skus = skuDao.selectByExample(skuQuery);
        for (Sku sku: skus) {
            //一个productID只发送3条，实际需要15  MyBatis一级缓存
            sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));



        }
        return skus;
    }

    //修改sku
    public void updateSkuById(Sku sku){
        skuDao.updateByPrimaryKeySelective(sku);

    }




}
