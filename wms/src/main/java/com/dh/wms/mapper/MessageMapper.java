package com.dh.wms.mapper;

import com.dh.wms.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface MessageMapper {

    int save(Message message);

    Message getById(Long id);

    List<Message> queryByDateTime(Date start, Date end);

    int updateStatus(Long id);
}
