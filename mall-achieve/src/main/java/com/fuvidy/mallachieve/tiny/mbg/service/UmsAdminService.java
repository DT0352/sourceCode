package com.fuvidy.mallachieve.tiny.mbg.service;

import com.fuvidy.mallachieve.tiny.mbg.model.UmsAdmin;

import java.security.Permission;
import java.util.List;

/**
 * @author fuzhidong
 */
public interface UmsAdminService {

    /**
     * 注册用户
     * @param umsAdminParam
     * @return
     */
    UmsAdmin register(UmsAdmin umsAdminParam);

    /**
     * 登陆用户并返回token
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * 获取所有权限
     * @param adminId
     * @return
     */
    List<Permission> getPermissionList(Long adminId);
}
