package com.me.core.service;

import com.me.core.dao.product.ColorDao;
import com.me.core.pojo.product.Color;
import com.me.core.pojo.product.ColorQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("colorService")
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorDao colorDao;

    //颜色结果集
    public List<Color> selectColorList(){
        ColorQuery colorQuery = new ColorQuery();
        colorQuery.createCriteria().andParentIdNotEqualTo(0L);
        return colorDao.selectByExample(colorQuery);

    }
}
