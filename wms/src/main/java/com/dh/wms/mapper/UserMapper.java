package com.dh.wms.mapper;

import com.dh.wms.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User queryById(Long id);

}
