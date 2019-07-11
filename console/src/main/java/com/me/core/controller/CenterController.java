package com.me.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台管理中心
 * */
@Controller
public class CenterController {

    /**
     *
     */
   /* @Autowired
    private TestTbService testTbService;


    @RequestMapping(value = "/test/index.do")
    public String index(){

        com.me.core.pojo.TestTb testTb = new com.me.core.pojo.TestTb();
        testTb.setName("hahha33");
        testTb.setBirthday(new Date());
        testTbService.insertTestTb(testTb);
        return "index";
    }*/
   //后台管理
    @RequestMapping(value = "/control/index.do")
    public String index(){
        return "index";
    }
    @RequestMapping(value = "/control/top.do")
    public String top(){
        return "top";
    }
    @RequestMapping(value = "/control/main.do")
    public String main(){
        return "main";
    }
    @RequestMapping(value = "/control/left.do")
    public String left(){
        return "left";
    }
    @RequestMapping(value = "/control/right.do")
    public String right(){
        return "right";
    }
    @RequestMapping(value = "/control/frame/product_main.do")
    public String product_main(){
        return "frame/product_main";
    }
    @RequestMapping(value = "/control/frame/product_left.do")
    public String product_left(){
        return "frame/product_left";
    }
    @RequestMapping(value = "/control/frame/ad_main.do")
    public String ad_main(){
        return "frame/ad_main";
    }
    @RequestMapping(value = "/control/frame/ad_left.do")
    public String ad_left(){
        return "frame/ad_left";
    }

}
