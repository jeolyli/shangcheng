package com.me;


import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestSolrJ {
    @Autowired
    private SolrServer solrServer;

    //保存
    @Test
    public void testAdd() throws Exception {
       /* String url = "http://192.168.200.133:8080/solr/";
        SolrServer solrServer = new HttpSolrServer(url);*/
        //保存
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id",2);
        doc.setField("name","hehe");
        solrServer.add(doc);
        solrServer.commit();


    }

    }

