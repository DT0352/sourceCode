package com.fuvidy.mallachieve.tiny.mbg.service;

import com.fuvidy.mallachieve.tiny.mbg.common.api.CommonResult;

public interface UmsMemberService {
    /**
     * 生成验证码
     * @param telephone
     * @return
     */
    CommonResult generateAuthCode(String telephone);

    /**
     * 判断验证码和手机号码是否匹配
     * @param telephone
     * @param authCode
     * @return
     */
    CommonResult verifyAuthCode(String telephone, String authCode);
}
