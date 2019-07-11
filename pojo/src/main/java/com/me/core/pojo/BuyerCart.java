package com.me.core.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.me.core.pojo.product.Sku;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BuyerCart implements Serializable{
    private static final long serialVersionUID = 1L;
    //购物项结果集
    private List<BuyerItem> items = new ArrayList<BuyerItem>();

    public List<BuyerItem> getItems() {
        return items;
    }

    public void setItems(List<BuyerItem> items) {
        this.items = items;
    }
    //添加购物项
    public void addItem(Long skuId,Integer amount){
        Sku sku = new Sku();
        sku.setId(skuId);
        BuyerItem buyerItem = new BuyerItem();
        buyerItem.setSku(sku);
        buyerItem.setAmount(amount);
        if(items.contains(buyerItem)){
            //数量追加
            for (BuyerItem it : items){
                if (it.equals(buyerItem)){
                    it.setAmount(it.getAmount());
                    break;
                }
            }

        }else {
            items.add(buyerItem);
        }
    }
    //商品数量
    @JsonIgnore
    public Integer getProductAmount(){
        Integer result = 0;
        for (BuyerItem item : items){
            result += item.getAmount();
        }
        return result;
    }
    //商品金额
    @JsonIgnore
    public Float getProductPrice(){
        Float result = 0f;
        for (BuyerItem item : items){
            result += item.getAmount()* item.getSku().getPrice();
        }
        return result;
    }
    //运费
    @JsonIgnore
    public Float getFee(){
        Float result = 0f;
        if (getProductPrice() <79){
            result = 5f;
        }
        return result;
    }
    //总金额
    @JsonIgnore
    public Float getTotalPrice(){

        return getProductPrice() + getFee();
    }
}
