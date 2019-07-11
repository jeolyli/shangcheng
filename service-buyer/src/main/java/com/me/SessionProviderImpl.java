package com.me;

import com.me.common.web.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

public class SessionProviderImpl  {
    @Autowired
    private Jedis jedis;
    //可更改存活时间
    private Integer exp = 30;
    public void setExp(Integer exp){
        this.exp = exp;
    }
    //保存用户名到redis中 key==》令牌
    public void setAttributeForUserName(String key,String value){
        jedis.set(key+":"+ Constants.USER_NAME,value);
        //存活时间是30分钟 默认值
        jedis.expire(key+":"+Constants.USER_NAME,60*exp);

    }
    //取出用户名从Redis
    public String getAttributeForUserName(String key){
        String username = jedis.get(key+":"+Constants.USER_NAME);
        if(null != username){
            jedis.expire(key+":"+Constants.USER_NAME,60*exp);
            return  username;
        }else {
            return null;
        }
    }
}
