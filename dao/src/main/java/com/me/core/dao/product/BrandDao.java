package com.me.core.dao.product;

import java.util.List;

import com.me.core.pojo.product.Brand;
import com.me.core.pojo.product.BrandQuery;

public interface BrandDao {

	//查询结果集 符合条件的   所有      并不是所有  而是 limit 开始行 , 每页数
	public List<Brand> selectBrandListByQuery(BrandQuery brandQuery);
	
	//总条数  符合条件的
	public Integer countBrandByQuery(BrandQuery brandQuery);
	
	//通过ID查询一个品牌
	public Brand selectBrandById(Long id);
	
	
	//修改品牌 通过ID
	public void updateBrandById(Brand brand);
	
	//批量删除 
	public void deletes(Long[] ids);

	
	

}
