package com.me.core.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Map;

/*
* 配置式开发静态化
* */
public class StaticPageServiceImpl implements StaticPageService,ServletContextAware{
    //注入
    private Configuration conf;
    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer){
        this.conf = freeMarkerConfigurer.getConfiguration();

    }
    //静态化程序
    public void index(Map<String,Object> root,String id){
        //路径
        String path = "/html/product/"+id+".html";
        String url = getAllPath(path);
        File f = new File(url);
        File parentFile = f.getParentFile();
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }

        //加载模板
        Writer out = null;
        try {
            //读
            Template template = conf.getTemplate("product.html");
            //输出流到指定文件
            out = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
            //处理
            template.process(root,out);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

                try {
                    if (null != out)
                     out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }




    }

    //获取全路径
    private String getAllPath(String path) {
        return servletContext.getRealPath(path);
    }
    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
