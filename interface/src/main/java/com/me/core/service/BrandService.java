package com.me.core.service;

import cn.itcast.common.page.Pagination;
import com.me.core.pojo.product.Brand;

import java.util.List;

public interface BrandService {
    public List<Brand> selectBrandListByQuery(String name, Integer isDisplay);
    public Pagination selectPaginationByQuery(Integer pageNo, String name, Integer isDisplay);
    public Brand selectBrandById(Long id);
    public void updateBrandById(Brand brand);
    public void deletes(Long[] ids);
}
