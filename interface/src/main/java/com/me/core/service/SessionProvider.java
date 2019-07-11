package com.me.core.service;

public interface SessionProvider {
    //保存用户名到redis中 key==》令牌
    public void setAttributeForUserName(String key,String value);
    //取出用户名从Redis
    public String getAttributeForUserName(String key);
}
