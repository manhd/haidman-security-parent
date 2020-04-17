package com.haidm.web.mobile.impl;

import com.haidm.web.mobile.ISmsSend;
import lombok.extern.slf4j.Slf4j;

/** 发送短信验证码， 第三方短信服务接口
 * @author shs-xxaqbzymhd
 */
@Slf4j
public class SmsCodeSender implements ISmsSend {

    /**
     *
     * @param mobile 手机号
     * @param content 短信内容
     * @return
     */
    @Override
    public boolean sendSms(String mobile, String content) {
        String sendContent = String.format("Haidman权限统一管理系统，验证码:%s,有效期3分钟，请勿泄露",content);
        //调用第三方短信服务接口
        log.info("----------向手机发送验证码----------");
        log.info(sendContent);
        return true;
    }
}
