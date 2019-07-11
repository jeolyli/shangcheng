package com.me.core.controller;

import cn.itcast.common.page.Pagination;
import com.me.core.pojo.product.Brand;
import com.me.core.pojo.product.Color;
import com.me.core.pojo.product.Product;
import com.me.core.pojo.product.Sku;
import com.me.core.service.CmsService;
import com.me.core.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class ProductController {
    @Autowired
    private SearchService searchService;
    //去首页
    @RequestMapping(value = "/")
    public String index(){

        return "index";
    }
    //搜索
    @RequestMapping(value = "/search")
    public String search(Integer pageNo,String keyword,Long brandId,String price,Model model) throws Exception {
        //加载品牌结果集
        List<Brand> brands = searchService.selectBrandList();
        model.addAttribute("brands",brands);
        //创建map  已知条件的结果集
        Map<String,String> map = new HashMap<>();
        if(null != brandId){
            for (Brand brand: brands) {
                if (brand.getId().equals(brandId)){
                    map.put("品牌",brand.getName());

                    break;
                }

            }
        }
        if(null != price){
          if (price.contains("-")){
              map.put("价格",price);
          }else {
              map.put("价格",price+"以上");
          }
        }
        model.addAttribute("map",map);


        //通过关键词 查询商品信息 从索引 库  返回分页对象
        Pagination pagination = searchService.selectPaginationFromSolr(pageNo,keyword,brandId,price);
        model.addAttribute("pagination", pagination);
        model.addAttribute("keyword", keyword);
        model.addAttribute("brandId", brandId);
        model.addAttribute("price", price);
        return "search";
    }
    @Autowired
    private CmsService cmsService;
    //去商品详情页面
    @RequestMapping(value = "/product/detail")
    public String detail(Long id,Model model)  {
        //商品对象
        Product product = cmsService.selectProductById(id);
        model.addAttribute("product",product);
        //sku对象
        List<Sku> skus = cmsService.selectSkuListByProductId(id);
        model.addAttribute("skus",skus);
        //去掉重复
        Set<Color> colors = new HashSet<>();
        for (Sku sku: skus) {
            colors.add(sku.getColor());
        }
        model.addAttribute("colors",colors);
        return "product";

    }

}
