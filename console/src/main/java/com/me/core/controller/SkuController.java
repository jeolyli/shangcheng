package com.me.core.controller;

import com.me.core.pojo.product.Sku;
import com.me.core.service.SkuService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class SkuController {
    @Autowired
    private SkuService skuService;

    //去库存页面
    @RequestMapping(value = "/sku/list.do")
    public String list(Long productId , Model model){
        List<Sku> skus = skuService.selectSkuListByProduct(productId);
        model.addAttribute("skus",skus);
        return "sku/list";

    }

    //修改
    @RequestMapping(value = "/sku/addSku.do")
    public void update(Sku sku, HttpServletResponse response) throws IOException {
        //修改
        skuService.updateSkuById(sku);
        JSONObject jo = new JSONObject();
        jo.put("message","保存成功");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jo.toString());
    }

}
