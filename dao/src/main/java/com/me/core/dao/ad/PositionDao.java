package com.me.core.dao.ad;

import com.me.core.pojo.ad.Position;
import com.me.core.pojo.ad.PositionQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PositionDao {
    int countByExample(PositionQuery example);

    int deleteByExample(PositionQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(Position record);

    int insertSelective(Position record);

    List<Position> selectByExample(PositionQuery example);

    Position selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Position record, @Param("example") PositionQuery example);

    int updateByExample(@Param("record") Position record, @Param("example") PositionQuery example);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);
}