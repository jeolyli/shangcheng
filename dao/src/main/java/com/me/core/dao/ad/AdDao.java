package com.me.core.dao.ad;

import com.me.core.pojo.ad.Ad;
import com.me.core.pojo.ad.AdQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdDao {
    int countByExample(AdQuery example);

    int deleteByExample(AdQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(Ad record);

    int insertSelective(Ad record);

    List<Ad> selectByExample(AdQuery example);

    Ad selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Ad record, @Param("example") AdQuery example);

    int updateByExample(@Param("record") Ad record, @Param("example") AdQuery example);

    int updateByPrimaryKeySelective(Ad record);

    int updateByPrimaryKey(Ad record);
}