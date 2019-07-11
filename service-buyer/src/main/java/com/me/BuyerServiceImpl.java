package com.me;

import com.me.core.dao.product.ColorDao;
import com.me.core.dao.product.ProductDao;
import com.me.core.dao.product.SkuDao;
import com.me.core.dao.user.BuyerDao;
import com.me.core.pojo.BuyerCart;
import com.me.core.pojo.BuyerItem;
import com.me.core.pojo.product.Sku;
import com.me.core.pojo.user.Buyer;
import com.me.core.pojo.user.BuyerQuery;
import com.me.core.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("buyerService")
public class BuyerServiceImpl implements BuyerService{
    @Autowired
    private BuyerDao buyerDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ColorDao colorDao;
    @Autowired
    private Jedis jedis;
    //通过用户名查询用户信息
    public Buyer selectBuyerByUsername(String username){
        BuyerQuery buyerQuery = new BuyerQuery();
        buyerQuery.createCriteria().andUsernameEqualTo(username);
        List<Buyer> buyers = buyerDao.selectByExample(buyerQuery);
        if(null != buyers && buyers.size() !=0 ){
            return buyers.get(0);
        }else {
            return null;
        }
    }
    //通过skuid查询sku对象
    public Sku selectSkuBuId(Long skuId){
        Sku sku = skuDao.selectByPrimaryKey(skuId);
        sku.setProduct(productDao.selectByPrimaryKey(sku.getProductId()));
        sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
        return sku;
    }
    //将购物车保存在redis中
    public void insertBuyerCartToRedis(BuyerCart buyerCart,String username){
        List<BuyerItem> items = buyerCart.getItems();
        for (BuyerItem item : items){
            jedis.hincrBy("buyerCart:"+username,String.valueOf(item.getSku().getId()),item.getAmount());
        }
    }
    //从redis中取出购物车
    public BuyerCart selectBuyerCartFromRedis(String username){
        BuyerCart buyerCart = null;
        Map<String,String> hgetAll = jedis.hgetAll("buyercart:"+username);
        if (null != hgetAll){
            Set<Map.Entry<String,String>> entrySet = hgetAll.entrySet();
            if(null != entrySet && entrySet.size() >0){
                buyerCart = new BuyerCart();
                for (Map.Entry<String,String> entry :entrySet){
                    buyerCart.addItem(Long.parseLong(entry.getKey()),Integer.parseInt(entry.getValue()));
                }
            }
        }
        return  buyerCart;
    }

}
