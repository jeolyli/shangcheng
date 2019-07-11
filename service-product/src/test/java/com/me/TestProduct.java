package com.me;

import com.me.core.dao.product.ProductDao;
import com.me.core.pojo.product.Product;
import com.me.core.pojo.product.ProductQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestProduct {
    @Autowired
    private ProductDao productDao;
    //保存
    @Test
    public void testAdd() throws Exception {


		Product product = productDao.selectByPrimaryKey(3L);
		System.out.println(product);

    }

}
