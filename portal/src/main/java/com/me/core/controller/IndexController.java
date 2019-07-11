package com.me.core.controller;

import com.me.common.JsonUtils;
import com.me.core.pojo.ad.Ad;
import com.me.core.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController
{
    @Autowired
    private AdService adService;
    @RequestMapping(value = "/")
    public String index(Model model){
        //首页轮播图
        String ads =adService.selectAdListByPositionId(89L);
        model.addAttribute("ads", ads);
        return "index";

    }
}
