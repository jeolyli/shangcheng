package com.me.core.service;

import cn.itcast.common.page.Pagination;
import com.me.core.dao.product.ProductDao;
import com.me.core.dao.product.SkuDao;
import com.me.core.pojo.product.Product;
import com.me.core.pojo.product.ProductQuery;
import com.me.core.pojo.product.Sku;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;
import java.util.Date;

@Service("productService")
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductDao productDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private Jedis jedis;

    //返回分页对象
    public Pagination selectPaginationByQuery(Integer pageNo,String name,Long brandId,Boolean isShow){

        ProductQuery productQuery = new ProductQuery();
        //当前页
        productQuery.setPageNo(Pagination.cpn(pageNo));
        //每页数
        productQuery.setPageSize(5);
        ProductQuery.Criteria criteria = productQuery.createCriteria();
        StringBuilder params = new StringBuilder();

        if(null != name){
            criteria.andNameLike("%"+name+"%");
            params.append("name=").append(name);
        }
        if(null != brandId){
            criteria.andBrandIdEqualTo(brandId);
            params.append("&brandId=").append(brandId);
        }
        if(null != isShow){
            criteria.andIsShowEqualTo(isShow);
            params.append("&isShow=").append(isShow);
        }else{
            criteria.andIsShowEqualTo(false);
            params.append("&isShow=").append(false);
        }

        Pagination pagination = new Pagination(productQuery.getPageNo()
        ,productQuery.getPageSize(),productDao.countByExample(productQuery),productDao.selectByExample(productQuery));

        String url = "/product/list.do";
        pagination.pageView(url,params.toString());
        return pagination;

    }

    //保存商品
    public void insertProduct(Product product){
        //数据ID  自增长
        Long pno = jedis.incr("pno");
        product.setId(pno);

        //下架
        product.setIsShow(false);
        //不删除
        product.setIsDel(true);
        //时间
        product.setCreateTime(new Date());
        //保存商品时 放回商品id
        productDao.insertSelective(product);

        //保存sku 库存表数据
        for (String color : product.getColors().split(",")){
            for (String size : product.getSizes().split(",")) {
                Sku sku = new Sku();
                sku.setProductId(product.getId());
                //颜色ID
                sku.setColorId(Long.parseLong(color));
                //尺码
                sku.setSize(size);
                //市场价
                sku.setMarketPrice(0f);
                //售价
                sku.setPrice(10f);
                //运费
                sku.setDeliveFee(0f);
                //库存
                sku.setStock(0);
                //购买上限
                sku.setUpperLimit(200);
                //时间
                sku.setCreateTime(new Date());

                skuDao.insertSelective(sku);
            }

        }


    }
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private JmsTemplate jmsTemplate;

    //上架
    public void isShow(Long[] ids) throws IOException, SolrServerException {
        Product product = new Product();
        //上架状态
        product.setIsShow(true);
        for (final Long id : ids){
            product.setId(id);
            //更改商品状态
            productDao.updateByPrimaryKeySelective(product);

            //发送消息
            jmsTemplate.send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(String.valueOf(id));
                }
            });

        }

    }

}
