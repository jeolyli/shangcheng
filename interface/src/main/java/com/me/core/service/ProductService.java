package com.me.core.service;

import cn.itcast.common.page.Pagination;
import com.me.core.pojo.product.Product;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

public interface ProductService {
    public Pagination selectPaginationByQuery(Integer pageNo, String name, Long brandId, Boolean isShow);
    public void insertProduct(Product product);
    public void isShow(Long[] ids) throws IOException, SolrServerException;
}
