package com.me.core.service;

import com.me.core.pojo.ad.Position;

import java.util.List;

public interface PositionService {

    //通过父ID查询广告子位置
    public List<Position> selectPositionListByParentId(Long parentId);
    //通过id查询位置标题
    public Position selectPositionById(Long id);
}
