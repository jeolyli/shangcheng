package com.me.core.controller;

import com.me.core.pojo.ad.Position;
import com.me.core.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public class PositionController {
    @Autowired
    private PositionService positionServie;
    @RequestMapping(value = "/position/tree.do")
    public String tree(String root, Model model){
        if("source".equals(root)){
            //初始化加载
            List<Position> ads = positionServie.selectPositionListByParentId(0L);
            model.addAttribute("list",ads);
        }else{
            //广告子位置加载
            List<Position> ads = positionServie.selectPositionListByParentId(Long.parseLong(root));
            model.addAttribute("list",ads);
        }
        return "position/tree";
    }
    //父位置下子位置的列表
    @RequestMapping(value = "/position/list.do")
    public String list(String root,Model model){
        if (null == root){
            //初始化加载
            List<Position> adcs = positionServie.selectPositionListByParentId(0L);
            model.addAttribute("list",adcs);
        }else {
            //广告子位置加载
            List<Position> adcs = positionServie.selectPositionListByParentId(Long.parseLong(root));
            model.addAttribute("list",adcs);

        }
        return "position/list";
    }
}
