package com.me.core.service;

import cn.itcast.common.page.Pagination;
import com.me.core.pojo.product.Brand;

import java.util.List;

public interface SearchService {
    public Pagination selectPaginationFromSolr(Integer pageNo,String keyword,Long brandId,String price) throws Exception;
    public List<Brand> selectBrandList();
    public void insertProductToSolr(Long id) throws Exception ;
}
