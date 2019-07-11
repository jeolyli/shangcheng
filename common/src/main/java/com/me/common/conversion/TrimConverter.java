package com.me.common.conversion;

import org.springframework.core.convert.converter.Converter;

//去掉前后空串 如果是空串转成null
public class TrimConverter implements Converter<String,String> {


    public String convert(String source) {
        if (null != source){
            source = source.trim();
            if (!"".equals(source)){
                return source;
            }

        }
        return null;
    }
}
