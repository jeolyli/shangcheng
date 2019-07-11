/*
package com.me;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestJedis {
    @Autowired
    private  Jedis jedis;
    //保存
    @Test
    public void testAdd() throws Exception {
      //  Jedis jedis = new Jedis("192.168.200.133",6379);
        jedis.set("he","hehe");
        jedis.close();
    }

    }
*/
