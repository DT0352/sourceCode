package com.fuvidy.mallachieve.tiny.mbg.controller;

import com.fuvidy.mallachieve.tiny.mbg.common.api.CommonResult;
import com.fuvidy.mallachieve.tiny.mbg.dto.UmsAdminLoginParam;
import com.fuvidy.mallachieve.tiny.mbg.model.UmsAdmin;
import com.fuvidy.mallachieve.tiny.mbg.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Permission;
import java.util.HashMap;
import java.util.List;

/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/7/18
 */
@RestController("/admin")
@Api(value = "UserAdminController", description = "用户后台管理")
public class UserAdminController {
    @Autowired
    private UmsAdminService adminService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam, BindingResult result) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            CommonResult.failed("注册失败");
        }
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation("用户登陆返回token")
    @PostMapping("/login")
    public CommonResult login(@RequestBody UmsAdminLoginParam umsLoginParam, BindingResult result) {
        String token = adminService.login(umsLoginParam.getUsername(), umsLoginParam.getPassword());
        if (token == null) {
            return CommonResult.failed("用户名或密码错误");
        }
        HashMap<Object, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("获取用户所有权限")
    @GetMapping("/permission/{adminId}")
    public CommonResult<List<Permission>> getPermissionList(@PathVariable Long adminId) {
        List<Permission> permissionList = adminService.getPermissionList(adminId);
        return CommonResult.success(permissionList);
    }
}
