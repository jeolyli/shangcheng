package com.me.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 获取或生成令牌
 * */
public class RequestUtils
{
    public static String getCESSION(HttpServletRequest request, HttpServletResponse response){
        //1.cookie中是否已经有令牌 去cookie中令牌
        Cookie[] cookies = request.getCookies();
        if(null != cookies && cookies.length >0){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("CESSIONID")){
                    //2有直接使用
                    return cookie.getValue();

                }
            }
        }
        //3.没有生成一个新的令牌
        String cessionid = UUID.randomUUID().toString().replaceAll("-","");
        //4.创建cookie 保存新令牌
        Cookie cookie = new Cookie("CESSIONID",cessionid);
        //设置存活时间 -1表示关闭浏览器销毁
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        //5.写回浏览器
        response.addCookie(cookie);
        //6.返回新生成的令牌
        return cessionid;
    }

}
