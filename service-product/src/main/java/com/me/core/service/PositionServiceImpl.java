package com.me.core.service;

import com.me.core.dao.ad.PositionDao;
import com.me.core.pojo.ad.Position;
import com.me.core.pojo.ad.PositionQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PositionServiceImpl {

    @Autowired
    private PositionDao positionDao;

    //通过父ID查询广告子位置
    public List<Position> selectPositionListByParentId(Long parentId){
        PositionQuery positionQuery = new PositionQuery();
        positionQuery.createCriteria().andParentIdEqualTo(parentId);
        return positionDao.selectByExample(positionQuery);

    }
    //通过id查询位置标题
    public Position selectPositionById(Long id){
        return positionDao.selectByPrimaryKey(id);
    }

}
