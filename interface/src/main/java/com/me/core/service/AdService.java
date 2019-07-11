package com.me.core.service;

import com.me.core.pojo.ad.Ad;

import java.util.List;

public interface AdService {
    //通过广告位置查询广告结果集
    public List<Ad> selectListByParentId(Long parentId);
    //添加
    public void insertAd(Ad ad);
    public String selectAdListByPositionId(Long positionId);
}
