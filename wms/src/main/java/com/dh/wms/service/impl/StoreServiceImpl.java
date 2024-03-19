package com.dh.wms.service.impl;

import com.dh.wms.entity.Store;
import com.dh.wms.mapper.StoreMapper;
import com.dh.wms.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public Store queryById(long id) {
        return storeMapper.queryById(id);
    }
}
