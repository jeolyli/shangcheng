package com.me.core.controller;

import com.me.core.pojo.ad.Ad;
import com.me.core.pojo.ad.Position;
import com.me.core.service.AdService;
import com.me.core.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 内容管理
 * 广告位管理
 * 广告具体内容
 * */
@Controller
public class AdController {
    @Autowired
    private AdService adService;
    @Autowired
    private PositionService positionService;
    //大广告列表查询
    @RequestMapping(value = "/ad/list.do")
    public String list(Long root , Model model){
        List<Ad> ads = adService.selectListByParentId(root);
        model.addAttribute("ads",ads);
        return "ad/list";
    }
    //去添加广告
    @RequestMapping(value = "/ad/toAdd.do")
    public String toAdd(Long positionId,Model model){
        Position position =  positionService.selectPositionById(positionId);
        model.addAttribute("position",position);
        return "ad/add";
    }
    //添加广告
    @RequestMapping(value ="/ad/add.do")
    public String add(Ad ad){
        adService.insertAd(ad);
        return "redirect:/ad/list.do?root="+ad.getPositionId();
    }
}
