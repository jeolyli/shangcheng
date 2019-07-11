package com.me.core.controller.common;

import com.me.common.RequestUtils;
import com.me.core.service.SessionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginIntercepor implements HandlerInterceptor {
    @Autowired
    private SessionProvider sessionProvider;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //处理器之前
        //1必须登录 登录了放行，如果未登录，去登录页面
       String username =  sessionProvider.getAttributeForUserName(RequestUtils.getCESSION(request,response));
        if (null == username){
            response.sendRedirect("http://localhost:8085/login.aspx?returnUrl=http://localhost:8081/" );
            return false;
        }
        //放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
