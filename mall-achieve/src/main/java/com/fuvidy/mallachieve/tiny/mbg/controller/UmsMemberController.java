package com.fuvidy.mallachieve.tiny.mbg.controller;

import com.fuvidy.mallachieve.tiny.mbg.common.api.CommonResult;
import com.fuvidy.mallachieve.tiny.mbg.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 会员登陆注册 接口
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/7/13
 */
@RestController
@Api(tags = "UmsMemberController",description = "会员登陆注册管理")
@RequestMapping("/sso")
public class UmsMemberController {
    @Autowired
    private UmsMemberService memberService;

    @ApiOperation("获取验证码")
    @GetMapping("/getAuthCode")
    public CommonResult getAuthCode(@RequestParam String telephone){
        return memberService.generateAuthCode(telephone);
    }
    @ApiOperation("判断验证码是否正确")
    @PostMapping("verifyAuthCode")
    public CommonResult updatePassword(@RequestParam String telephone,
                                       @RequestParam String authCode) {
        return memberService.verifyAuthCode(telephone,authCode);
    }
}
