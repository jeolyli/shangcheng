package com.me.core.controller;

import cn.itcast.common.page.Pagination;
import com.me.core.pojo.product.Brand;
import com.me.core.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlandController {
    //品牌管理 入口
    @Autowired
    private BrandService brandService;

    @RequestMapping(value = "/brand/list.do")
    public String list(Integer pageNo ,String name,Integer isDisplay ,Model model){
        //查询列表  通过品牌名称  是否可见
        //List<Brand> brands = brandService.selectBrandListByQuery(name, isDisplay);
        Pagination pagination = brandService.selectPaginationByQuery(pageNo,name,isDisplay);
        model.addAttribute("pagination",pagination);
        model.addAttribute("name",name);
        model.addAttribute("isDisplay",isDisplay);


        return "brand/list";
    }
    //通过id查询品牌
    @RequestMapping(value = "/brand/toEdit.do")
    public String toEdit(Long id,Model model){
        Brand brand = brandService.selectBrandById(id);
        model.addAttribute("brand",brand);
        return "brand/edit";

    }
    //通过id修改
    @RequestMapping(value = "/brand/edit.do")
    public String updateBrandById(Brand brand){
        brandService.updateBrandById(brand);
        return "redirect:/brand/list.do";
    }
    //删除
    @RequestMapping(value = "/brand/deletes.do")
    public String deletes(Long[] ids){
        brandService.deletes(ids);
       /* model.addAttribute("name",name);
        model.addAttribute("isDisplay",isDisplay);
        model.addAttribute("pageNo",pageNo);*/
        //跳转视图 重定向
        return "forward:/brand/list.do";

    }


}
