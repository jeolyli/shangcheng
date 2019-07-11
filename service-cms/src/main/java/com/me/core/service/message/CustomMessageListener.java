package com.me.core.service.message;

import com.me.core.pojo.product.Color;
import com.me.core.pojo.product.Product;
import com.me.core.pojo.product.Sku;
import com.me.core.service.CmsService;
import com.me.core.service.SearchService;
import com.me.core.service.StaticPageService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.*;
//自定义消息处理类

public class CustomMessageListener implements MessageListener{
    @Autowired
    private StaticPageService staticPageService;
    @Autowired
    private CmsService cmsService;

    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage atm = (ActiveMQTextMessage) message;
        //商品id
        try {
            String id = atm.getText();
            System.out.println(id);
            Map<String,Object> root = new HashMap<>();
            //商品对象
            Product product = cmsService.selectProductById(Long.parseLong(id));
            root.put("product",product);
            //sku对象
            List<Sku> skus = cmsService.selectSkuListByProductId(Long.parseLong(id));
            root.put("skus",skus);
            //去掉重复
            Set<Color> colors = new HashSet<>();
            for (Sku sku: skus) {
                colors.add(sku.getColor());
            }
            root.put("colors",colors);
            staticPageService.index(root,id);



        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
