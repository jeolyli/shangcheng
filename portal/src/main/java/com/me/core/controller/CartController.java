package com.me.core.controller;
/**
 * 购物车管理
 * */
import com.me.common.JsonUtils;
import com.me.common.RequestUtils;
import com.me.common.web.Constants;
import com.me.core.pojo.BuyerCart;
import com.me.core.pojo.BuyerItem;
import com.me.core.pojo.product.Sku;
import com.me.core.service.BuyerService;
import com.me.core.service.SessionProvider;
import com.sun.tools.internal.jxc.ap.Const;
import com.sun.tools.javac.code.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private BuyerService buyerService;
    //加入购物车
    @RequestMapping(value = "/shopping/buyerCart")
    public String buyerCart(Long skuId, Integer amount
    , HttpServletRequest request, HttpServletResponse response){
        BuyerCart buyerCart = null;
        //1获取cookie
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0 ){
            for (Cookie cookie : cookies){
                //2.获取cookie中的购物车
                if (cookie.getName().equals(Constants.BUYER_CART)){
                    buyerCart = JsonUtils.jsonToPojo(cookie.getValue(), BuyerCart.class);
                }
            }
        }
        if (null == buyerCart){
            buyerCart = new BuyerCart();
        }
        //追加当前商品
        if (null != skuId && null != amount){
           buyerCart.addItem(skuId,amount);
        }
        //用户是否登录
        String username = sessionProvider.getAttributeForUserName(RequestUtils.getCESSION(request,response));
        if(null != username){
            //3登录了 将购物车保存在redis中清除cookie
            List<BuyerItem> items = buyerCart.getItems();
            if (items.size() > 0){
                buyerService.insertBuyerCartToRedis(buyerCart,username);
                //清空cookie中的购物车
                Cookie cookie = new Cookie(Constants.BUYER_CART,null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }else {
            //未登录
            if (null != skuId && null != amount){
                //创建cookie 将购物车保存到cookie
                Cookie cookie = new Cookie(Constants.BUYER_CART,JsonUtils.objectToString(buyerCart));
                cookie.setMaxAge(60*60*24);
                cookie.setPath("/");
                //写回浏览器
                response.addCookie(cookie);

            }
        }
        //重定向或内部转发
        return "redirect:/shopping/toCart";
    }
    //去购物车结算
    @RequestMapping(value = "/shopping/toCart")
    public String toCart(HttpServletRequest request, HttpServletResponse response, Model model){
        BuyerCart buyerCart = null;
        //1.获取cookie
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length >0){
            for (Cookie cookie : cookies){
                //2.获取cookie中购物车
                if (Constants.BUYER_CART.equals(cookie.getName())){
                    buyerCart = JsonUtils.jsonToPojo(cookie.getValue(),BuyerCart.class);
                    break;
                }

            }
        }
        //用户是否登录
        String username = sessionProvider.getAttributeForUserName(RequestUtils.getCESSION(request,response));
        if (null != username){
            //有 将购物车保存到redis 清空 cookie
            if (null != buyerCart){
                buyerService.insertBuyerCartToRedis(buyerCart,username);
                Cookie cookie = new Cookie(Constants.BUYER_CART,null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            buyerCart = buyerService.selectBuyerCartFromRedis(username);

        }
        //将购物车装满
        if (null != buyerCart){
            List<BuyerItem> itemList = buyerCart.getItems();
            if (itemList.size() >0){
                for (BuyerItem item : itemList){
                    item.setSku(buyerService.selectSkuBuId(item.getSku().getId()));
                }

            }
        }
        //跳转购物车页面进行回显
        model.addAttribute("buyerCart",buyerCart);
        return "cart";

    }
}
