package com.me.core.controller;

        import cn.itcast.common.page.Pagination;
        import com.me.core.pojo.product.Brand;
        import com.me.core.pojo.product.Color;
        import com.me.core.pojo.product.Product;
        import com.me.core.service.BrandService;
        import com.me.core.service.ColorService;
        import com.me.core.service.ProductService;
        import org.apache.solr.client.solrj.SolrServerException;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.RequestMapping;

        import java.io.IOException;
        import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ColorService colorService ;

    //商品管理列表查询
    @RequestMapping(value = "/product/list.do")
    public String list(Integer pageNo,String name,Long brandId,Boolean isShow,Model model){
        //查询结果集
        List<Brand> brands = brandService.selectBrandListByQuery(null, 1);
        model.addAttribute("brands",brands);
        Pagination pagination = productService.selectPaginationByQuery(pageNo,name,brandId,isShow);
        model.addAttribute("pagination",pagination);
        model.addAttribute("name",name);
        model.addAttribute("brandId",brandId);
        model.addAttribute("isShow",isShow);

        return "product/list";

    }
    @RequestMapping(value = "/product/toAdd.do")
    public String toAdd(Model model){
        //查询品牌结果集
        List<Brand> brands = brandService.selectBrandListByQuery(null, 1);
        model.addAttribute("brands",brands);
        List<Color> colors = colorService.selectColorList();
        model.addAttribute("colors",colors);
        return "product/add";



    }
    //商品保存
    @RequestMapping(value = "/product/add.do")
    public String add(Product product,Model model){
        productService.insertProduct(product);
        return "redirect:/product/list.do";

    }
    //上架
    @RequestMapping(value = "/product/isShow.do")
    public String isShow(Long[] ids,Model model) throws IOException, SolrServerException {
        productService.isShow(ids);
        return "forward:/product/list.do";
    }


}
