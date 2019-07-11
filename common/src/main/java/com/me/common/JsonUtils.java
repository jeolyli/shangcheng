package com.me.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonUtils {
    //定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        //转换 不包含null值
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    //将对象转为json字符串
    public static String objectToString(Object data){
        String str = null;
        try {
            str = MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return str;
    }
    //将JSON结果集转化为对象
    public static <T> T jsonToPojo(String jsonData,Class<T> beanType) {
        T t = null;
        try {
            t = MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
    //将JSON结果集转化为list
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType){
       JavaType javaType =  MAPPER.getTypeFactory().constructParametricType(List.class,beanType);
        List<T> t = null;
        try {
            t =  MAPPER.readValue(jsonData,javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
