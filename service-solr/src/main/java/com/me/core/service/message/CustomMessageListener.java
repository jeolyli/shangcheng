package com.me.core.service.message;

import com.me.core.service.SearchService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;

public class CustomMessageListener implements MessageListener{
    @Autowired
    private SearchService searchService;
    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage atm = (ActiveMQTextMessage) message;
        try {
            String id = atm.getText();
            System.out.println(id);
            //保存商品信息到solr服务器
            searchService.insertProductToSolr(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
