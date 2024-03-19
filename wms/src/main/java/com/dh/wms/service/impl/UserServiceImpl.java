package com.dh.wms.service.impl;

import com.dh.wms.entity.User;
import com.dh.wms.mapper.UserMapper;
import com.dh.wms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer queryRoleById(Long id) {
        User user = userMapper.queryById(id);
        return user == null ? null : user.getRole();
    }

}
