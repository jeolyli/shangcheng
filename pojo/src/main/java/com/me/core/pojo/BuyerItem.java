package com.me.core.pojo;

import com.me.core.pojo.product.Sku;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;
import java.util.Objects;

public class BuyerItem  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Sku sku;
    private Boolean isHave = true;
    private Integer amount = 1;

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public Boolean getHave() {
        return isHave;
    }

    public void setHave(Boolean have) {
        isHave = have;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyerItem item = (BuyerItem) o;
        return Objects.equals(sku, item.sku) &&
                Objects.equals(isHave, item.isHave) &&
                Objects.equals(amount, item.amount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sku, isHave, amount);
    }
}
