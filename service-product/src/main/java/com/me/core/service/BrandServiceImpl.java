package com.me.core.service;

import cn.itcast.common.page.Pagination;

import com.me.core.dao.product.BrandDao;
import com.me.core.pojo.product.Brand;
import com.me.core.pojo.product.BrandQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

//品牌管理
@Service("brandService")
public class BrandServiceImpl implements BrandService {
    @Autowired
    private Jedis jedis;

    @Autowired
    private BrandDao brandDao;

    //查询列表  通过品牌名称  是否可见
    public List<Brand> selectBrandListByQuery(String name, Integer isDisplay){
        BrandQuery brandQuery = new BrandQuery();
        if(null != name){
            brandQuery.setName(name);
        }
        if(null != isDisplay){
            brandQuery.setIsDisplay(isDisplay);
        }else {
            brandQuery.setIsDisplay(1);
        }
        return brandDao.selectBrandListByQuery(brandQuery);

    }
    //返回分页对象
    public Pagination selectPaginationByQuery(Integer pageNo,String name,Integer isDisplay){
        BrandQuery brandQuery = new BrandQuery();
        //设置当前页  cpn方法: 如果pageNumber 是null或者 小于1, 那么就将pageNumber设置为1, 如果不是则使用传递进来的pageNumber
        brandQuery.setPageNo(Pagination.cpn(pageNo));
        //每页数
        brandQuery.setPageSize(5);
        //拼接参数
        StringBuilder params = new StringBuilder();
        if (null != name){
            brandQuery.setName(name);
            params.append("name=").append(name);
        }
        if (null != isDisplay){
            brandQuery.setIsDisplay(isDisplay);
            params.append("&isDisplay=").append(isDisplay);
        }else{
            brandQuery.setIsDisplay(1);
            params.append("&isDisplay=").append(1);
        }
        //构建分页对象
        Pagination pagination = new Pagination(brandQuery.getPageNo(),
                brandQuery.getPageSize(),brandDao.countBrandByQuery(brandQuery));
        //设置结果集
        pagination.setList(brandDao.selectBrandListByQuery(brandQuery));
        //设置URL 参数&参数
        String url = "/brand/list.do";
        pagination.pageView(url,params.toString());
        return pagination;

    }

    public Brand selectBrandById(Long id) {
        return brandDao.selectBrandById(id);
    }

    //修改品牌 通过id
    public void updateBrandById(Brand brand){
        brandDao.updateBrandById(brand);
        //保存redis
        jedis.hset("brand",String.valueOf(brand.getId()),brand.getName());

    }

    //删除
    public void deletes(Long[] ids){
        brandDao.deletes(ids);

    }


}
