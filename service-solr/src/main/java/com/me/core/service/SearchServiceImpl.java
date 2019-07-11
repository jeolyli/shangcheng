package com.me.core.service;

import cn.itcast.common.page.Pagination;
import com.me.core.dao.product.ProductDao;
import com.me.core.dao.product.SkuDao;
import com.me.core.pojo.product.*;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 全文检索
 * 商品搜索
 * */
@Service("searchService")
public class SearchServiceImpl  implements SearchService {

    @Autowired
    private SolrServer solrServer;
    @Autowired
    private Jedis jedis;


    //根据关键词搜索
    public Pagination selectPaginationFromSolr(Integer pageNo,String keyword,Long brandId,String price) throws Exception {
        StringBuilder params = new StringBuilder();
        //创建索引库条件对象
        SolrQuery solrQuery = new SolrQuery();
        //查询 关键词
//		solrQuery.set("q", "name_ik:" + keyword);
        solrQuery.setQuery(keyword);
        //指定默认查询的域 default field
        solrQuery.set("df", "name_ik");
        params.append("keyword=").append(keyword);
        //查询过滤条件
        if (null != brandId){
            solrQuery.addFilterQuery("brandId:"+brandId);
            params.append("&brandId=").append(brandId);
        }
        //0-99  1600
        if (null != price){
            String[] p = price.split("-");
            if (p.length == 2){
                solrQuery.addFilterQuery("price:["+p[0]+" TO "+p[1] +"]");
            }else {
                solrQuery.add("price:["+p[0]+" TO *]");
            }
            params.append("&price=").append(price);

        }


        //高亮
        //1：打开高亮的开关
        solrQuery.setHighlight(true);
        //2:设置高亮的域
        solrQuery.addHighlightField("name_ik");
        //3:前缀 后缀
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        solrQuery.setHighlightSimplePost("</span>");


        //排序  默认按价格排序   综合排序
        solrQuery.setSort("price", SolrQuery.ORDER.asc);

        ProductQuery productQuery = new ProductQuery();
        //当前页
        productQuery.setPageNo(Pagination.cpn(pageNo));
        //每页数 8
        productQuery.setPageSize(8);
        //分页
        solrQuery.setStart(productQuery.getStartRow());
        solrQuery.setRows(productQuery.getPageSize());
        //查询指定的域  select id,name  from
        // field list
        solrQuery.set("fl", "id,name_ik,price,url");
        //执行查询
        QueryResponse response = solrServer.query(solrQuery);
        //取出高亮
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        //1: K  商品ID   V Map
        //2: K 商品域名 name_ik  V list
        //3: list 域支持多值  可以追加 Apache  但我们只一个值  list.get(0);
        //结果集
        SolrDocumentList docs = response.getResults();
        //总条数
        long numFound = docs.getNumFound();

        //创建List集群
        List<Product> products = new ArrayList<Product>();

        for (SolrDocument doc : docs) {
            Product product = new Product();

            String id = (String) doc.get("id");
            //商品IDeas
            product.setId(Long.parseLong(id));
            //商品名称
            Map<String, List<String>> map = highlighting.get(id);
            List<String> list = map.get("name_ik");
//			String name = (String) doc.get("name_ik");
            product.setName(list.get(0));
            //图片
            String imgUrl = (String) doc.get("url");
            product.setImgUrl(imgUrl);
            //价格
            product.setPrice((Float) doc.get("price"));

            products.add(product);
        }

        //创建分页对象
        Pagination pagination = new Pagination(
                productQuery.getPageNo(),
                productQuery.getPageSize(),
                (int) numFound,
                products
        );

        //分页在页面上展示
        String url = "/search";
        pagination.pageView(url, params.toString());
        return pagination;
    }
    //查询品牌结果集 从redis中查询
    public List<Brand> selectBrandList(){
        List<Brand> brands = new ArrayList<>();
        //取出
        Map<String, String> brand = jedis.hgetAll("brand");
        Set<Map.Entry<String, String>> entries = brand.entrySet();
        for (Map.Entry<String, String> entry: entries){
            Brand brand1 = new Brand();
            brand1.setId(Long.parseLong(entry.getKey()));
            brand1.setName(entry.getValue());
            brands.add(brand1);

        }
        return  brands;


    }
    @Autowired
    private ProductDao productDao;
    @Autowired
    private SkuDao skuDao;
    //保存商品信息到solr服务器  keyword == 商品名称
    public void insertProductToSolr(Long id) throws Exception {

        SolrInputDocument doc = new SolrInputDocument();
        //1.商品id
        doc.setField("id",id);
        //2.商品名称
        Product p = productDao.selectByPrimaryKey(id);
        doc.setField("name_ik",p.getName());
        //3.品牌id
        doc.setField("brandId",p.getBrandId());
        //4.图片URL
        doc.setField("url",p.getImgUrl());
        //5 价格 select price from bbs_sku where product_id = 4 order by price asc limit 1
        SkuQuery skuQuery = new SkuQuery();
        skuQuery.createCriteria().andProductIdEqualTo(id);
        skuQuery.setOrderByClause("price asc");
        skuQuery.setPageNo(1);
        skuQuery.setPageSize(1);
        //只查询price
        skuQuery.setFields("price");
        List<Sku> skus = skuDao.selectByExample(skuQuery);
        doc.setField("price",skus.get(0).getPrice());
        //时间
        solrServer.add(doc);
        solrServer.commit();
    }

}
