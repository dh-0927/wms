package com.dh.wms.mapper;

import com.dh.wms.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {

    int insert(Goods goods);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Goods goods);

    List<Goods> QueryByName(String name);

    Goods queryById(long id);
}
