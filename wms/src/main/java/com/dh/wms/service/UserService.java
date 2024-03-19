package com.dh.wms.service;

public interface UserService {

    /**
     * 根据用户 id 查询用户权限，如果该用户不存在，则返回 null
     */
    Integer queryRoleById(Long id);

}
