package com.dh.wms.mapper;

import com.dh.wms.entity.Store;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoreMapper {

    Store queryById(long id);
}
