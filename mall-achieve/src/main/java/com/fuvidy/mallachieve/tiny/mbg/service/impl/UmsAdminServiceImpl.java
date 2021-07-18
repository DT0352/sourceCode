package com.fuvidy.mallachieve.tiny.mbg.service.impl;

import com.fuvidy.mallachieve.tiny.mbg.model.UmsAdmin;
import com.fuvidy.mallachieve.tiny.mbg.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Permission;
import java.util.List;

/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/7/18
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    @Autowired
    private
    @Override
    public UmsAdmin register(UmsAdmin umsAdminParam) {

        return null;
    }

    @Override
    public String login(String username, String password) {
        return null;
    }

    @Override
    public List<Permission> getPermissionList(Long adminId) {
        return null;
    }
}
