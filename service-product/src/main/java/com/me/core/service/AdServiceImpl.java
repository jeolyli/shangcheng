package com.me.core.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.me.common.JsonUtils;
import com.me.core.dao.ad.AdDao;
import com.me.core.dao.ad.PositionDao;
import com.me.core.pojo.ad.Ad;
import com.me.core.pojo.ad.AdQuery;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

public class AdServiceImpl implements AdService{
    @Autowired
    private AdDao adDao;
    @Autowired
    private PositionDao positionDao;
    @Autowired
    private Jedis jedis;
    //通过广告位置查询广告结果集
    public List<Ad> selectListByParentId(Long parentId){
        AdQuery adQuery = new AdQuery();
        adQuery.createCriteria().andPositionIdEqualTo(parentId);
        List<Ad> ads = adDao.selectByExample(adQuery);
        for (Ad ad : ads){
            ad.setPosition(positionDao.selectByPrimaryKey(ad.getPositionId()));
        }
        return ads;
    }
    //添加
    public void insertAd(Ad ad){
        adDao.insertSelective(ad);
    }
    //加载大广告位置的所有广告
    public String selectAdsList(Long positionId){
        AdQuery adQuery = new AdQuery();
        adQuery.createCriteria().andPositionIdEqualTo(positionId);
        return JsonUtils.objectToString(adDao.selectByExample(adQuery));

    }
    //根据位置id查询广告结果集
    public String selectAdListByPositionId(Long positionId){
        //根据广告位的id 从缓存中查询广告结果集的json字符串
       String adJson =  jedis.get("ads:"+positionId);
       if(null == adJson){
            AdQuery adQuery = new AdQuery();
            adQuery.createCriteria().andPositionIdEqualTo(positionId);
           List<Ad> ads =  adDao.selectByExample(adQuery);
           adJson = JsonUtils.objectToString(ads);
           jedis.set("ads:"+positionId,adJson);
       }
       return adJson;
    }
}
